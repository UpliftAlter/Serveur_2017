package serveur;

public class Enseignant extends Utilisateur {

	
	//CONSTRUCTEUR
	public Enseignant(String nom, String prenom, int Id, String login,
			String mdp) {
		super(nom, prenom, Id, login, mdp,Service.ENSEIGNANT);

	}
	
	

}
