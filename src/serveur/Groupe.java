package serveur;

import java.util.TreeSet;

public class Groupe {
	
	//ATTRIBUTS
	private TreeSet<Utilisateur> GroupeUser;
	private String nomGroupe;
	private int IdGroupe;
	
	
	
	//CONSTRUCTEUR
	public Groupe(TreeSet <Utilisateur> groupe, String nomGroupe , int Idgroupe)
	{
		this.GroupeUser=groupe;
		this.nomGroupe=nomGroupe;
		this.IdGroupe=Idgroupe;
	}
	
	
	//METHODES
	
	//Methode pour ajouter un user à un groupe
	public void addMember(Groupe groupe, Utilisateur user){
		
		this.GroupeUser.add(user);
	}
	
	//Methode pour supprimer un user à un groupe
	public void deleteMember(Groupe groupe, Utilisateur user)
	{
		this.GroupeUser.remove(user);
	}
	
	//Methode pour envoyer les données d'un groupe à la BDD
	public void stockageGrpBDD(Groupe groupe)
	{
		
	}
	
	
	//GETTERS AND SETTERS 
	public String getNomGroupe() {
		return nomGroupe;
	}

	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	public int getIdGroupe() {
		return IdGroupe;
	}

	public void setIdGroupe(int idGroupe) {
		IdGroupe = idGroupe;
	}
	
	


}
