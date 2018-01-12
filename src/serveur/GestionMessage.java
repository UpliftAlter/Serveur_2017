package serveur;

import java.util.ArrayList;
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

	public GestionMessage(Tube tube) {
		this.tube = tube;
	}

	public GestionMessage(Authentification a) {
		this.a = a;
	}

	public void filDeDiscussion(FilDeDiscussion fdd) {
		try {
			database.addFilDeDiscussion(fdd);
		} catch (DataBaseException e) {
			System.out.println("Erreur ajout fdd dans gerer message coté serveur");
			e.printStackTrace();
		}
		tube.broadcast(null, fdd.getConversation().get(0));
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
		tube.broadcast(null, message);

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
