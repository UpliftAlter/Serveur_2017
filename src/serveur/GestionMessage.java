package serveur;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import classes.DB;
import classes.DataBaseException;
import classes.FilDeDiscussion;
import classes.Groupe;
import classes.Message;
import classes.Pair;
import classes.TypeMessage;
import classes.Utilisateur;

public class GestionMessage {

	// ATTRIBUTS
	DB database = new DB();
	Tube tube;
	Authentification a;
	Serveur server;

	// CONSTRUCTEUR

	// METHODES

	public GestionMessage(Serveur server, Tube tube) {
		this.tube = tube;
		this.server = server;
	}

	public void filDeDiscussion(FilDeDiscussion fdd) {

		try {
			Message message = fdd.getConversation().get(0);
			int idMessage = message.getIdMsg();
			database.addFilDeDiscussion(fdd);
			database.addMessageToFil(fdd.getIdFil(), message);
			server.getAllFdd().add(fdd);

		} catch (DataBaseException e) {
			System.out.println("Erreur ajout fdd dans gerer message cote serveur");
		}

		List<Socket> listeSocket = createListSocketFromMessage(fdd);

		tube.broadcast(listeSocket, fdd);
		tube.broadcast(listeSocket, fdd.getConversation().get(0));
		Message temp = fdd.getConversation().get(0);
		System.out.println("Id du message: " + temp.getIdMsg());
		temp.setType(TypeMessage.RECEIVED);
		temp.setIdFil(fdd.getIdFil());
		System.out.println("temp id: " + temp.getIdMsg());
		server.addNewMessage(temp);
		Utilisateur user = temp.getAuteur();
		Socket socketTemp = server.getOnlineUsers().get(user.getIdUser());
		tube.send(socketTemp, temp);

	}

	private List<Socket> createListSocketFromMessage(FilDeDiscussion fdd) {
		List<Utilisateur> listUsersInGroup = null;
		List<Socket> listeSocket = new ArrayList<>();

		try {
			listUsersInGroup = database.getUsersFromGroup(fdd.getGroupe().getIdGroupe());
		} catch (Exception e1) {
			System.out.println("Pb retrieve list user from id group");
		}
		if (!listUsersInGroup.isEmpty() && listUsersInGroup != null) {
			for (Utilisateur user : listUsersInGroup) {
				Socket socketTemp = server.getOnlineUsers().get(user.getIdUser());
				if (socketTemp != null)
					listeSocket.add(socketTemp);

			}
		}

		Utilisateur user = fdd.getCreateur();
		Socket socketTemp = server.getOnlineUsers().get(user.getIdUser());
		if (socketTemp != null && !listeSocket.contains(socketTemp))
			listeSocket.add(socketTemp);
		return listeSocket;
	}

	public void message(Message message) {

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (message.getType()) {
		case REQUETE_INIT_FDD:
			gererInitFDD(message);
			break;
		case READ:
			gererMessageRead(message);
			break;
		case REQUETE_INIT_GROUP:
			gererInitGroupes();
			break;
		case REQUETE_STATUS_MESSAGE:
			gererStatusMessage(message);
			break;
		case MESSAGE:
			gererMessage(message);
			break;
		default:
			break;
		}
	}

	private void gererStatusMessage(Message message) {
		System.out.println(server.getMapAllMessages().get(message.getIdMsg()));
		tube.send(server.getMapAllMessages().get(message.getIdMsg()));

	}

	private void gererMessageRead(Message message) {
		server.updateMessage(message);
		Groupe gRef = null;
		FilDeDiscussion fddRef = null;
		int idFilRef = message.getIdFil();
		System.out.println(message.getIdFil());
		for (FilDeDiscussion fdd : server.getAllFdd()) {
			System.out.println(idFilRef + " et " + fdd.getIdFil());
			if (fdd.getIdFil() == idFilRef) {
				gRef = fdd.getGroupe();
				fddRef = fdd;
			}
		}

		if (!gRef.getListeUtilisateur().contains(message.getAuteur())) {
			if (server.getMapAllMessages().get(message.getIdMsg()).size() == gRef.getListeUtilisateur().size() + 1) {
				message.setType(TypeMessage.READ_BY_ALL);
				tube.broadcast(createListSocketFromMessage(message), message);
			} 
		} else {
			if (server.getMapAllMessages().get(message.getIdMsg()).size() == gRef.getListeUtilisateur().size()) {
				message.setType(TypeMessage.READ_BY_ALL);
				tube.broadcast(createListSocketFromMessage(message), message);
			} 
		}

	}

	private void gererMessage(Message message) {

		List<Socket> listeSocket = createListSocketFromMessage(message);

		try {
			database.loadFil(message.getIdFil()).addMessage(message);
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
		tube.broadcast(listeSocket, message);
		message.setType(TypeMessage.RECEIVED);

		server.addNewMessage(message);
		Utilisateur user = message.getAuteur();
		Socket socketTemp = server.getOnlineUsers().get(user.getIdUser());
		tube.send(socketTemp, message);
	}

	private List<Socket> createListSocketFromMessage(Message message) {
		FilDeDiscussion fdd = null;
		List<Utilisateur> listUsersInGroup = null;
		List<Socket> listeSocket = new ArrayList<>();
		try {
			fdd = database.loadFil(message.getIdFil());
		} catch (Exception e) {
			System.out.println("là cette fois ci");
		}
		try {

			listUsersInGroup = database.getUsersFromGroup(fdd.getGroupe().getIdGroupe());
		} catch (Exception e1) {
			System.out.println("Pb retrieve list user from id group");
		}
		if (!listUsersInGroup.isEmpty() && listUsersInGroup != null) {
			for (Utilisateur user : listUsersInGroup) {
				Socket socketTemp = server.getOnlineUsers().get(user.getIdUser());
				if (socketTemp != null) {
					listeSocket.add(socketTemp);
				} else {
					server.addPendingMessage(user.getIdUser(), message);
				}
			}
		}

		Utilisateur user = fdd.getCreateur();
		Socket socketTemp = server.getOnlineUsers().get(user.getIdUser());
		if (socketTemp != null && !listeSocket.contains(socketTemp))
			listeSocket.add(socketTemp);
		return listeSocket;
	}

	private void gererInitGroupes() {
		tube.send(server.getAllGroups());
	}

	private void gererInitFDD(Message message) {
		int idUser = getIdUserFromMessage(message);
		if (idUser >= 0) {
			List<FilDeDiscussion> lfddTemp = null;
			try {
				lfddTemp = database.filsFromIdUser(idUser);
			} catch (DataBaseException e) {
				System.out.println("erreur fdd data");
			}
			tube.send(lfddTemp);
		} else {
			System.out.println("Erreur init fil de discussions");
		}
	}

	private int getIdUserFromMessage(Message message) {
		int toReturn = Integer.parseInt(message.getMsg());
		return toReturn;
	}

	private String[] getLogin(String s) {
		String[] toReturn = s.split("#");
		return toReturn;
	}

}
