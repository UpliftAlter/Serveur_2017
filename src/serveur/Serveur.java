package serveur;

import ihm.FrameServeur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import utilisateur.Groupe;
import utilisateur.Utilisateur;

public class Serveur {

	private String adress;
	private int port = 7777;
	private ServerSocket server;
	private ArrayList<Groupe> allGroups = new ArrayList<>();
	private ArrayList<Utilisateur> allUsers = new ArrayList<>();
	
	//Network part
	private ArrayList<Socket> allSockets = new ArrayList<>();
	private ArrayList<Utilisateur> onlineUsers = new ArrayList<>();
	

	//CONSTRUCTEUR
	public Serveur() throws IOException{
		server = new ServerSocket(port);
		System.out.println("Server is running...");
		FrameServeur fs = new FrameServeur(this);
		fs.setVisible(true);
		listeningConnections();
	}
	
	//METHODE
	public void listeningConnections () throws IOException
	{
		while(true){
			Socket enteringClient = server.accept();
			System.out.println("Someone conenct");
			allSockets.add(enteringClient);
			
			//add user in list
			
			Thread t = new Thread(new Tube(this, enteringClient));
			t.start();
		}
	}
	
	//GETTERS AND SETTERS
	public String getAdress() {
		return adress;
	}

	public int getPort() {
		return port;
	}

	public ArrayList<Groupe> getAllGroups() {
		return allGroups;
	}

	public ArrayList<Utilisateur> getAllUsers() {
		return allUsers;
	}

	public ArrayList<Socket> getAllSockets() {
		return allSockets;
	}

	public ArrayList<Utilisateur> getOnlineUsers() {
		return onlineUsers;
	}
	
	public ServerSocket getSocket(){
		return server;
	}
	
}
