package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeSet;

import utilisateur.Groupe;
import utilisateur.Utilisateur;

public class Serveur {

	private String adress;
	private int port = 7777;
	private ServerSocket server;
	private TreeSet<Groupe> allGroups = new TreeSet<>();
	private TreeSet<Utilisateur> allUsers = new TreeSet<>();
	
	//Network part
	private ArrayList<Socket> allSockets = new ArrayList<>();
	private ArrayList<Utilisateur> onlineUsers = new ArrayList<>();
	

	//CONSTRUCTEUR
	public Serveur() throws IOException{
		server = new ServerSocket(port);
		System.out.println("Server is running...");
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

	public TreeSet<Groupe> getAllGroups() {
		return allGroups;
	}

	public TreeSet<Utilisateur> getAllUsers() {
		return allUsers;
	}

	public ArrayList<Socket> getAllSockets() {
		return allSockets;
	}

	public ArrayList<Utilisateur> getOnlineUsers() {
		return onlineUsers;
	}
	
	
}
