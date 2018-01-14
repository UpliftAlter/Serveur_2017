package serveur;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import classes.DB;
import classes.DataBaseException;
import classes.FilDeDiscussion;
import classes.Groupe;
import classes.Message;
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
			database.addFilDeDiscussion(fdd);
			database.addMessageToFil(fdd.getIdFil(), fdd.getConversation().get(0));
		} catch (DataBaseException e) {
			System.out.println("Erreur ajout fdd dans gerer message cote serveur");
		}
		List<Socket> listeSocket = createListSocketFromMessage(fdd.getConversation().get(0));

		tube.broadcast(listeSocket, fdd);
		tube.broadcast(listeSocket, fdd.getConversation().get(0));
	}

	public void message(Message message) {
		
		try {
			System.out.println(message.getIdFil());
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
		case MESSAGE:
			gererMessage(message);
			break;
		default:
			break;
		}
	}

	private void gererMessageRead(Message message) {
		tube.broadcast(createListSocketFromMessage(message), message);

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

		} catch (DataBaseException e) {
			e.printStackTrace();
		}
		if (fdd != null) {
			listUsersInGroup = fdd.getGroupe().getListeUtilisateur();
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
		ArrayList<Groupe> lgTemp = null;
		try {
			lgTemp = (ArrayList<Groupe>) database.getAllGroupsBD();
		} catch (DataBaseException e) {
			System.out.println("Catch groups data");
		}
		tube.send(lgTemp);
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
