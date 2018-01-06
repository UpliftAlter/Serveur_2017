package serveur;

public class Etudiant extends Utilisateur {

	
	
	//CONSTRUCTEUR
	public Etudiant(String nom, String prenom, int id, String login, String mdp,Service service) {
		super(nom, prenom, id, login, mdp, service);
	}
	
	public Etudiant(String nom, String prenom){
		super(nom,prenom);
	}

}
