package serveur;

import java.util.Date;

public class Message {

	//ATTRIBUTS

	private Date date;
	private String msg;
	private int IdEmetteur;
	private int Idfil;
	private Utilisateur createur;
	
	
	//CONSTRUCTEUR
	public Message(Date date,String msg, int IdEmetteur, int Idfil, Utilisateur createur )
	{
		this.setDate(date);
		this.setMsg(msg);
		this.setIdEmetteur(IdEmetteur);
		this.setIdfil(Idfil);
		this.setCreateur(createur);
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
		return IdEmetteur;
	}

	public void setIdEmetteur(int idEmetteur) {
		IdEmetteur = idEmetteur;
	}

	public int getIdfil() {
		return Idfil;
	}

	public void setIdfil(int idfil) {
		Idfil = idfil;
	}

	public Utilisateur getCreateur() {
		return createur;
	}

	public void setCreateur(Utilisateur createur) {
		this.createur = createur;
	}
	
}
