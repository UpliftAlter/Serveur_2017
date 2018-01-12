package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import classes.DB;
import classes.DataBaseException;
import classes.Groupe;
import classes.Utilisateur;
import ihm.FrameServeur;

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

			Thread t = new Thread(new Authentification(this, enteringClient));
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
			try {
				database.addGroupBD(g);
			} catch (DataBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void addUser(Utilisateur u) {
		if (!allUsers.contains(u)) {
			allUsers.add(u);
			try {
				database.addUserBD(u);
			} catch (DataBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void removeUser(Utilisateur u) {
		allUsers.remove(u);
		try {
			database.removeUserBD(u);
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeUserFromGroup(Groupe g, Utilisateur u) {
		g.deleteMember(u);
		database.removeUserInGroup(u.getIdUser(), g.getIdGroupe());
	}

	public void removeGroup(Groupe g) {
		allGroups.remove(g);
		try {
			database.removeGroupBD(g.getIdGroupe());
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initAllGroups() {
		allGroups = (ArrayList<Groupe>) database.getAllGroupsBD();

		for (Groupe groupe : allGroups) {
			groupe.initMembers(database.getUsersFromGroup(groupe.getIdGroupe()));
		}
	}

	private void initAllUsers() {
		allUsers = (ArrayList<Utilisateur>) database.getAllUsers();
	}

}
