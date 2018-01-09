package serveur;

import java.io.IOException;

import utilisateur.DB;
import utilisateur.DataBaseException;
import utilisateur.FilDeDiscussion;
import utilisateur.Groupe;
import utilisateur.Message;
import utilisateur.Utilisateur;

public class GestionMessage {
	
	//ATTRIBUTS
	DB database;
	Tube tube;
	Serveur server;
	
	
	//CONSTRUCTEUR
	public GestionMessage(Tube tube)
	{
		this.tube=tube;
	}
	
	//METHODES
	
	//TRAITEMENT MESSAGE CLASSIQUE
	public void traitementMessage(Message message,FilDeDiscussion fil )
	{
		
		try {
			this.database.addMessageToFil(fil.getIdFil(),message);
		} catch (DataBaseException e) {
			// ENVOYER MESSAGE UTILISATEUR ?????
			e.printStackTrace();
		}
		try {
			this.tube.broadcast(this.server.getAllSockets(), message);
		} catch (IOException e) {
			// ENVOYER MESSAGE UTILISATEUR ?????
			e.printStackTrace();
		}


	}
	
	//TRAITEMENT CREATION DE TICKET
	public void creationTicket(Message message, FilDeDiscussion fil)
	{
		try{
			this.database.addFilDeDiscussion(fil);
		} catch (DataBaseException e){
			//ENVOYER MESSAGE UTILISATEUR?????
			e.printStackTrace();
	}
		
		try {
			this.tube.broadcast(this.server.getAllSockets(), message);
		} catch (IOException e) {
			//ENVOYER MESSAGE UTILISATEUR ????
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}
