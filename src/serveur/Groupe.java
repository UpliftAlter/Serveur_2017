package serveur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
		String url = "jdbc:mysql://localhost:3306/base_de_donnees_neocampus?autoReconnect=true&useSSL=false";
		String username = "root";
		String mdp = "root";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url,username,mdp);

		    /* Ici, nous placerons nos requêtes vers la BDD */
			Statement statement = connexion.createStatement();
			
			int statut = statement.executeUpdate("INSERT INTO APPARTENIR (ID_Utilisateur,ID_Groupe) VALUES ('"+user.getIdUser()+"','"+this.idGroupe+"');");

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
		
		String url = "jdbc:mysql://localhost:3306/base_de_donnees_neocampus?autoReconnect=true&useSSL=false";
		String username = "root";
		String mdp = "root";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url,username,mdp);

		    /* Ici, nous placerons nos requêtes vers la BDD */
			Statement statement = connexion.createStatement();
			
			int statut = statement.executeUpdate("DELETE FROM Appartenir (ID_Utilisateur,ID_Groupe) WHERE (ID_Utilisateur ='"+user.getIdUser()+"'and'"+ this.idGroupe+"';");

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
	
	
	//Methode pour envoyer les donnï¿½es d'un groupe ï¿½ la BDD ECRITURE
	public void stockageGrpBDD()
	{
		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:3306/base_de_donnees_neocampus?autoReconnect=true&useSSL=false";
		String username = "root";
		String mdp = "root";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url,username,mdp);

		    /* Ici, nous placerons nos requêtes vers la BDD */
			Statement statement = connexion.createStatement();
			
			int statut = statement.executeUpdate("INSERT INTO Groupe (ID_Groupe,NomGroupe) VALUES ('"+this.idGroupe+"',"+this.idGroupe+"';");

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
	
	//Requete pour obtenir la liste de tout les GROUPES
	public List <Groupe> retrieveAllGroups () {
		
		List <Groupe> listeGroupe = new ArrayList<Groupe>();
		String nomgroupe;
		int idgroupe;
		
		
		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:3306/base_de_donnees_neocampus?autoReconnect=true&useSSL=false";
		String username = "root";
		String mdp = "root";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url,username,mdp);

		    /* Ici, nous placerons nos requêtes vers la BDD */
			Statement statement = connexion.createStatement();
			
			ResultSet resultat = statement.executeQuery("SELECT (ID_GROUPE) , (Nom_Groupe) FROM Groupe ;");
			while(resultat.next())
			{
				idgroupe = resultat.getInt("ID_GROUPE");
				nomgroupe=resultat.getString("Nom_Groupe");
				Groupe groupetest = new Groupe(nomgroupe,idgroupe);
				listeGroupe.add(groupetest);
			}

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
		return listeGroupe;
	}
	
	//RENVOIE LA LISTE DES PERSONNES  FESANT PARTIE DUN GROUPE
	public List <Utilisateur> retriveUsersFromGroupe() {
		
		List <Utilisateur> listeUser = new ArrayList();
		 String nom ;
		 String prenom;
		 int IdUser;
		 String login;
		 String mdp;
		 Service service;
		
		
		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:3306/base_de_donnees_neocampus?autoReconnect=true&useSSL=false";
		String username = "root";
		String mdp1 = "root";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url,username,mdp1);

		    /* Ici, nous placerons nos requêtes vers la BDD */
			Statement statement = connexion.createStatement();
			
			ResultSet resultat = statement.executeQuery("SELECT * FROM utilisateur as U WHERE U.ID_UTILISATEUR IN (SELECT ID_UTILISATEUR FROM appartenir WHERE ID_GROUPE ='"+this.idGroupe+"';");
			
			while(resultat.next())
			{
				nom = resultat.getString("Nom_Utilisateur");
				prenom = resultat.getString("Prenom_Utilisateur");
				IdUser = resultat.getInt("ID_Utilisateur");
				login = resultat.getString("Identifiant");
				mdp = resultat.getString("Mot_De_Passe");
				service = (Service) resultat.getObject("TypeUtilisateur");
				
				if(service == Service.ETUDIANT)
				{
					Etudiant etudiant = new Etudiant(nom, prenom, IdUser, login, mdp);
					listeUser.add(etudiant);
				}
				else if (service == Service.ENSEIGNANT)
				{
					Enseignant enseignant = new Enseignant(nom, prenom, IdUser, login, mdp);
					listeUser.add(enseignant);
				}
				else
				{
					Agent agent = new Agent(nom, prenom, IdUser, login, mdp, service);
					listeUser.add(agent);
				}
		
			}
		

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
		
		return listeUser;
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
