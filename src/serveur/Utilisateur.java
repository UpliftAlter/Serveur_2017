package serveur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Utilisateur {
	
	// ATTRIBUTS 
	private String nom ;
	private String prenom;
	private int IdUser;
	private String login;
	private String mdp;
	private Service service;
	
	
	//CONSTRUCTEUR
	public Utilisateur(String nom, String prenom, int Id, String login, String mdp,Service service){
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setIdUser(Id);
		this.setLogin(login);
		this.setMdp(mdp);
		this.service=service;
	}
	
	public Utilisateur(String nom, String prenom){
		this.setNom(nom);
		this.setPrenom(prenom);
	}
	
	
	//METHODES
	
	//Methode qui va envoyer les informations vers la base de donnï¿½es.
	public void stockageUserBDD(Utilisateur user){
		
		/* Connexion à la base de données */
		String url = "A COMPLETER";
		String username = " A COMPLETER";
		String mdp = "A COMPLETER";
				
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url,username,mdp);

		    /* Ici, nous placerons nos requêtes vers la BDD */
			Statement statement = connexion.createStatement();
			
			int statut = statement.executeUpdate("INSERT INTO Utilisateur (ID_Utilisateur,Identifiant,MotDePasse,NomUtilisateur,PrenomUtilisateur,TypeUtilisateur) VALUES ("+this.IdUser+","+this.login+","+this.mdp+","+this.nom+","+this.prenom+","+this.service+";");

		} catch ( SQLException e ) {
		    /* Gérer les éventuelles erreurs ici */
		} finally {
		    if ( connexion != null )
		        try {
		            /* Fermeture de la connexion */
		            connexion.close();
		        } catch ( SQLException ignore ) {
		            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
		        }
		}
	
	}
	
	
	@Override
	public String toString() {
		return nom + " " + prenom;
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
