package serveur;

public class Agent extends Utilisateur {

	//ATTRIBUTS
	private Service service;
	
	
	
	//CONSTRUCTEUR
	public Agent(String nom, String prenom, int Id, String login, String mdp, Service service) {
		super(nom, prenom, Id, login, mdp,service);
	}


//GETTERS AND SETTERS
	public Service getService() {
		return service;
	}



	public void setService(Service service) {
		this.service = service;
	}

}
