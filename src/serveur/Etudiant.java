package serveur;

public class Etudiant extends Utilisateur {

	
	
	//CONSTRUCTEUR
	public Etudiant(String nom, String prenom, int id, String login, String mdp) {
		super(nom, prenom, id, login, mdp,Service.ETUDIANT);
	}
	
	public Etudiant(String nom, String prenom){
		super(nom,prenom);
	}

}
