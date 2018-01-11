package serveur;

import java.io.IOException;
import java.util.Scanner;

import utilisateur.DB;
import utilisateur.DataBaseException;
import utilisateur.FilDeDiscussion;
import utilisateur.Message;
import utilisateur.Utilisateur;

public class GestionMessage {
	
	//ATTRIBUTS
	DB database = new DB();
	Tube tube;
	Serveur server;
	
	
	//CONSTRUCTEUR

	//METHODES
	
	public GestionMessage(Tube tube) {
		this.tube = tube;
	}

	public void filDeDiscussion(FilDeDiscussion fdd) {
		
		
	}

	public void message(Message message) {
		
		
	}
	
	
	
	
	
	
	
}
