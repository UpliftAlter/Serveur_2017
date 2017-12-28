package serveur;

public class Utilisateur {
	
	// ATTRIBUTS 
	private String nom ;
	private String prenom;
	private int IdUser;
	private String login;
	private String mdp;
	
	
	//CONSTRUCTEUR
	public Utilisateur(String nom, String prenom, int Id, String login, String mdp){
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setIdUser(Id);
		this.setLogin(login);
		this.setMdp(mdp);
	}
	
	
	//METHODES
	
	//Methode qui va envoyer les informations vers la base de données.
	public void stockageUserBDD(Utilisateur user){
	
	}
	
	
	//Methode Qui permet de modifier les informations de l'utilisateur
	public void modifierInfos(Utilisateur user , String nom, String prenom, int Id, String login, String mdp) {
		
	}
	
	//Methode qui verifie les credentials
	public void login(Utilisateur user){
		
	}

    // GETTERS AND SETTERS
	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public int getIdUser() {
		return IdUser;
	}


	public void setIdUser(int idUser) {
		IdUser = idUser;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getMdp() {
		return mdp;
	}


	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	
	
	
}
