package serveur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Groupe {
	


	//ATTRIBUTS
	private List<Utilisateur> groupeUser = new ArrayList<>();
	private String nomGroupe;
	private int idGroupe;
	
	
	
	//CONSTRUCTEUR
	public Groupe(String nomGroupe , int idgroupe)
	{
		this.nomGroupe=nomGroupe;
		this.idGroupe=idgroupe;
	}
	
	public Groupe(String nomGroupe){
		this.nomGroupe = nomGroupe;
	}
	
	
	//METHODES
	
	@Override
	public String toString() {
		return nomGroupe;
	}
	
	//Methode pour ajouter un user à un groupe
	public void addMember(Etudiant user)
	{
		
		/* Connexion à la base de données */
		String url = "A COMPLETER";
		String username = "A COMPLETER";
		String mdp = " A COMPLETER";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url,username,mdp);

		    /* Ici, nous placerons nos requêtes vers la BDD */
			Statement statement = connexion.createStatement();
			
			int statut = statement.executeUpdate("INSERT INTO APPARTENIR (ID_Utilisateur,ID_Groupe) VALUES ("+user.getIdUser()+","+this.idGroupe+";");

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

	
		this.groupeUser.add(user);
	}
	
	//Methode pour supprimer un user d'un groupe
	public void deleteMember(Utilisateur user)
	{
		
		/* Connexion à la base de données */
		String url = "A COMPLETER";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url);

		    /* Ici, nous placerons nos requêtes vers la BDD */
			Statement statement = connexion.createStatement();
			
			int statut = statement.executeUpdate("DELETE FROM APPARTENIR (ID_Utilisateur,ID_Groupe) WHERE (ID_Utilisateur = ? and ID_Groupe = ?)");

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

		this.groupeUser.remove(user);
	}
	
	
	//Methode pour envoyer les donnï¿½es d'un groupe ï¿½ la BDD
	public void stockageGrpBDD(Groupe groupe)
	{
		/* Connexion à la base de données */
		String url = "A COMPLETER";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url);

		    /* Ici, nous placerons nos requêtes vers la BDD */
			Statement statement = connexion.createStatement();
			
			int statut = statement.executeUpdate("INSERT INTO Groupe (ID_Groupe,NomGroupe) VALUES (?,?)");

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
	
	
	//GETTERS AND SETTERS 
	public String getNomGroupe() {
		return nomGroupe;
	}

	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	public int getIdGroupe() {
		return idGroupe;
	}

	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}
	
	public List<Utilisateur> getGroupeUser(){
		return this.groupeUser;
	}
	


}
