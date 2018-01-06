package serveur;

import java.util.Date;

public class Message {

	//ATTRIBUTS

	private Date date;
	private String msg;
	private int idEmetteur;
	private int idfil;
	private Utilisateur createur;
	
	
	//CONSTRUCTEUR
	public Message(String msg, int idEmetteur, int idfil, Utilisateur createur )
	{
		this.date = new Date();
		this.setMsg(msg);
		this.setIdEmetteur(idEmetteur);
		this.setIdfil(idfil);
		this.setCreateur(createur);
	}
	
	public Message (String msg){
		this.date = new Date();
		this.msg = msg;
	}
	
	//METHODES
	
	//Methode qui va ACK 
	public void envoiACK(){
		
	}
	
	
	
	//SETTERS AND GETTERS

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getIdEmetteur() {
		return idEmetteur;
	}

	public void setIdEmetteur(int idEmetteur) {
		this.idEmetteur = idEmetteur;
	}

	public int getIdfil() {
		return idfil;
	}

	public void setIdfil(int idfil) {
		this.idfil = idfil;
	}

	public Utilisateur getCreateur() {
		return createur;
	}

	public void setCreateur(Utilisateur createur) {
		this.createur = createur;
	}
	
}
