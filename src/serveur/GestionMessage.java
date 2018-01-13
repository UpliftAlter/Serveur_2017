package serveur;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import classes.DB;
import classes.DataBaseException;
import classes.FilDeDiscussion;
import classes.Groupe;
import classes.Message;
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
		List<Utilisateur> listUsersInGroup = null;
		List<Socket> listeSocket = new ArrayList<>();
		try {
			database.addFilDeDiscussion(fdd);
			database.addMessageToFil(fdd.getIdFil(), fdd.getConversation().get(0));
			
		} catch (DataBaseException e) {
			System.out.println("Erreur ajout fdd dans gerer message cote serveur");
		}
		listUsersInGroup = fdd.getGroupe().getListeUtilisateur();
		
		if (!listUsersInGroup.isEmpty() && listUsersInGroup != null) {
			for (Utilisateur user : listUsersInGroup) {
				Socket socketTemp = server.getOnlineUsers().get(user.getIdUser());
				if (socketTemp != null) {
					listeSocket.add(socketTemp);
				} 
			}
		}
		Utilisateur user = database.UtilisateurFromID(fdd.getCreateur().getIdUser());
		Socket socketTemp = server.getOnlineUsers().get(user.getIdUser());
		if (socketTemp != null && !listeSocket.contains(socketTemp)) 
			listeSocket.add(socketTemp);
		tube.broadcast(listeSocket, fdd.getConversation().get(0));
	}

	public void message(Message message) {
		switch (message.getType()) {
		case REQUETE_INIT_FDD:
			gererInitFDD(message);
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

	private void gererMessage(Message message) {
		FilDeDiscussion fdd;
		List<Utilisateur> listUsersInGroup = null;
		List<Socket> listeSocket = new ArrayList<>();
		try {
			fdd = database.loadFil(message.getIdFil());
			listUsersInGroup = fdd.getGroupe().getListeUtilisateur();
		} catch (DataBaseException e) {
			e.printStackTrace();
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
		Utilisateur user = database.UtilisateurFromID(message.getAuteur().getIdUser());
		Socket socketTemp = server.getOnlineUsers().get(user.getIdUser());
		if (socketTemp != null && !listeSocket.contains(socketTemp)) 
			listeSocket.add(socketTemp);

		tube.broadcast(listeSocket, message);
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
