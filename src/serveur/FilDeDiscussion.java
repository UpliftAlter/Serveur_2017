package serveur;

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
