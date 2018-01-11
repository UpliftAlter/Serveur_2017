package serveur;

import java.util.List;

import utilisateur.DB;
import utilisateur.DataBaseException;
import utilisateur.FilDeDiscussion;
import utilisateur.Groupe;
import utilisateur.Message;
import utilisateur.Utilisateur;

public class GestionMessage {

	// ATTRIBUTS
	DB database = new DB();
	Tube tube;
	Serveur server;

	// CONSTRUCTEUR

	// METHODES

	public GestionMessage(Tube tube) {
		this.tube = tube;
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
		case REQUETE_LOGIN:
			gererLogin(message);
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

	private void gererLogin(Message message) {
		String[] login = getLogin(message.getMsg());
		Utilisateur uTemp = database.login(login[0], login[1]);
		tube.send(uTemp);
	}

	private void gererInitGroupes() {
		List<Groupe> lgTemp = database.getAllGroupsBD();
		tube.send(lgTemp);
	}

	private void gererInitFDD(Message message) {
		int idUser = getIdUserFromMessage(message);
		if (idUser >= 0) {
			List<FilDeDiscussion> lfddTemp = database.filsFromIdUser(idUser);
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
