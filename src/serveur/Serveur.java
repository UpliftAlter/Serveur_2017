package serveur;

import ihm.FrameServeur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import utilisateur.DB;
import utilisateur.Etudiant;
import utilisateur.Groupe;
import utilisateur.Utilisateur;

public class Serveur {
	private int port = 7777;
	private ServerSocket server;
	private ArrayList<Groupe> allGroups = new ArrayList<>();
	private ArrayList<Utilisateur> allUsers = new ArrayList<>();
	private DB database = new DB();

	// Network part
	private ArrayList<Socket> allSockets = new ArrayList<>();
	private ArrayList<Utilisateur> onlineUsers = new ArrayList<>();

	// CONSTRUCTEUR
	public Serveur() throws IOException {
		initAllGroups();
		initAllUsers();
		server = new ServerSocket(port);
		System.out.println("Server is running...");
		FrameServeur fs = new FrameServeur(this);
		fs.setVisible(true);
		listeningConnections();
	}

	// METHODE
	public void listeningConnections() throws IOException {
		while (true) {
			Socket enteringClient = server.accept();
			System.out.println("Someone has connected");
			allSockets.add(enteringClient);

			// add user in list

			Thread t = new Thread(new Tube(this, enteringClient));
			t.start();
		}
	}

	// GETTERS AND SETTERS

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

	public ServerSocket getSocket() {
		return server;
	}

	public void disconnect() throws IOException {
		server.close();
	}

	public void addGroup(Groupe g) {
		if (!allGroups.contains(g)) {
			allGroups.add(g);
			database.addGroupBD(g);
		}
	}

	public void addUser(Utilisateur u) {
		if (!allUsers.contains(u)) {
			allUsers.add(u);
			database.addUserBD(u);
		}
	}

	private void initAllGroups() {
		allGroups = (ArrayList<Groupe>) database.getAllGroupsBD();
	}

	private void initAllUsers() {
		database.getAllGroupsBD();
	}

}
