package serveur;

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
	
	//Methode pour ajouter un user � un groupe
	public void addMember(Etudiant user)
	{
		this.groupeUser.add(user);
	}
	
	//Methode pour supprimer un user � un groupe
	public void deleteMember(Utilisateur user)
	{
		this.groupeUser.remove(user);
	}
	
	//Methode pour envoyer les donn�es d'un groupe � la BDD
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
		return idGroupe;
	}

	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}
	
	public List<Utilisateur> getGroupeUser(){
		return this.groupeUser;
	}
	


}
