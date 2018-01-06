package serveur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class FilDeDiscussion {
	
	
	//ATTRIBUTS
	private String titre;
	private int IdFil;
	private Groupe groupe;
	private Utilisateur createur;
	private List<Message> listeMessage;
	
	//CONSTRUCTEUR
	public FilDeDiscussion(String titre, int IdFil, Groupe groupe, Utilisateur createur, List<Message> listeMessage){
		this.setCreateur(createur);
		this.setIdFil(IdFil);
		this.setGroupe(groupe);
		this.setTitre(titre);
		this.setListeMessage(listeMessage);
	}
	
	
	//METHODES
	
	//Methode qui va envoyer à la Base de données le Fil de discussion
	public void stockageBDD( FilDeDiscussion fil)
	{
		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:3306/base_de_donnees_neocampus?autoReconnect=true&useSSL=false";
		String username = "root";
		String mdp = "root";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection(url,username,mdp);

		    /* Ici, nous placerons nos requêtes vers la BDD */
			Statement statement = connexion.createStatement();
			
			int statut = statement.executeUpdate("INSERT INTO FilDeDiscussion (ID_FilDeDiscussion,Titre) VALUES ('"+this.IdFil+"','"+this.titre+"';");

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
	

	
	
	// GETTERS AND SETTERS
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getIdFil() {
		return IdFil;
	}

	public void setIdFil(int idFil) {
		IdFil = idFil;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public Utilisateur getCreateur() {
		return createur;
	}

	public void setCreateur(Utilisateur createur) {
		this.createur = createur;
	}


	public List<Message> getListeMessage() {
		return listeMessage;
	}


	public void setListeMessage(List<Message> listeMessage) {
		this.listeMessage = listeMessage;
	}
	
	
	

}
