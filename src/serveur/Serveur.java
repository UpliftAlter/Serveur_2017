package serveur;

public class Serveur {

	private String adresse;
	private int port;
	private int socket;
	private Message tobroad;
	
	//CONSTRUCTEUR
	public Serveur(String adresse , int port, int socket, Message tobroad){
		this.setAdresse(adresse);
		this.setPort(port);
		this.setSocket(socket);
		this.setTobroad(tobroad);
	}
	
	//METHODES
	
	//Methode qui va Broadcast un message 
	public void broadcast(Message tobroad){
		
	}
	
	//Methode pour lancer l'attente active
	public void ecoute ()
	{
		
	}
	
	//Methode pour envoyer un message
	public void envoyerMessage ()
	{
		
	}

	
	//GETTERS AND SETTERS
	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getSocket() {
		return socket;
	}

	public void setSocket(int socket) {
		this.socket = socket;
	}

	public Message getTobroad() {
		return tobroad;
	}

	public void setTobroad(Message tobroad) {
		this.tobroad = tobroad;
	}
}
