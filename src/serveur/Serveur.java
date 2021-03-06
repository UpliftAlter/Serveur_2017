package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import classes.DB;
import classes.DataBaseException;
import classes.FilDeDiscussion;
import classes.Groupe;
import classes.Message;
import classes.Pair;
import classes.TypeMessage;
import classes.Utilisateur;
import ihm.FrameServeur;

public class Serveur {
	private int port = 7777;
	private ServerSocket server;
	private ArrayList<Groupe> allGroups = new ArrayList<>();
	private List<FilDeDiscussion> allFdd = new ArrayList<>();
	private ArrayList<Utilisateur> allUsers = new ArrayList<>();
	private List<Message> allMessages = new ArrayList<>();
	private DB database = new DB();

	private Map<Integer, List<Message>> pendingMessages = new HashMap<Integer, List<Message>>();

	private Map<Integer, List<Pair<Utilisateur, TypeMessage>>> etatRecepetionMessages = new HashMap<Integer, List<Pair<Utilisateur, TypeMessage>>>();

	private Map<Integer, List<Utilisateur>> mapAllMessagesRead = new HashMap<>();
	// Network part
	// private ArrayList<Socket> allSockets = new ArrayList<>();
	// private ArrayList<Utilisateur> onlineUsers = new ArrayList<>();

	private Map<Integer, Socket> onlineUsers = new HashMap<Integer, Socket>();

	// CONSTRUCTEUR
	public Serveur() throws IOException {
		initAllGroups();
		initAllUsers();
		initAllFdd();
		initAllMessages();
		initMapAllMessage();
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
			// allSockets.add(enteringClient);
			// System.out.println(allSockets);
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

	public ServerSocket getSocket() {
		return server;
	}

	// ----------------------------------------------------------------------------------------
	public Map<Integer, Socket> getOnlineUsers() {
		return onlineUsers;
	}

	public Map<Integer, List<Pair<Utilisateur, TypeMessage>>> getEtatRecepetionMessages() {
		return etatRecepetionMessages;
	}

	public boolean isMessageReceived(int idMessage) {
		boolean res = false;
		List<Pair<Utilisateur, TypeMessage>> newList = etatRecepetionMessages.get(idMessage);
		for (Pair<Utilisateur, TypeMessage> pair : newList) {
			res = res || pair.getRight().equals(TypeMessage.RECEIVED);
		}

		return res;
	}

	public boolean isMessageRead(int idMessage) {
		boolean res = false;
		List<Pair<Utilisateur, TypeMessage>> newList = etatRecepetionMessages.get(idMessage);
		for (Pair<Utilisateur, TypeMessage> pair : newList) {
			res = res || pair.getRight().equals(TypeMessage.READ);
		}

		return res;
	}

	public boolean isMessageReadByAll(int idMessage) {
		boolean res = false;
		List<Pair<Utilisateur, TypeMessage>> newList = etatRecepetionMessages.get(idMessage);
		for (Pair<Utilisateur, TypeMessage> pair : newList) {
			res = res && (pair.getRight().equals(TypeMessage.READ) || pair.getRight().equals(TypeMessage.READ_BY_ALL));
		}

		return res;
	}

	public Map<Integer, List<Message>> getPendingMessages() {
		return pendingMessages;
	}

	public void addUserSocket(Utilisateur user, Socket sock) {
		onlineUsers.put(user.getIdUser(), sock);
		System.out.println(onlineUsers);
	}

	public void removeUserSocket(int idUser) {
		onlineUsers.remove(idUser);
		System.out.println(onlineUsers);
	}

	public void addPendingMessage(int idUser, Message message) {
		pendingMessages.put(idUser, new ArrayList<>());
		pendingMessages.get(idUser).add(message);
		System.out.println("pending message :" + pendingMessages.get(idUser));
	}

	public void removePendingMessage(int idUser) {
		pendingMessages.remove(idUser);
	}

	public List<FilDeDiscussion> getAllFdd() {
		return allFdd;
	}

	public void setAllFdd(List<FilDeDiscussion> allFdd) {
		this.allFdd = allFdd;
	}

	public List<Message> getAllMessages() {
		return allMessages;
	}

	public void setAllMessages(List<Message> allMessages) {
		this.allMessages = allMessages;
	}

	// -----------------------------------------------------------------------------------------
	public void disconnect() throws IOException {
		server.close();
	}

	public void addGroup(Groupe g) {
		if (!allGroups.contains(g)) {
			allGroups.add(g);
			try {
				database.addGroupBD(g);
			} catch (DataBaseException e) {
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
				e.printStackTrace();
			}
		}
	}

	public void removeUser(Utilisateur u) {
		allUsers.remove(u);
		try {
			database.removeUserBD(u);
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
	}

	public void removeUserFromGroup(Groupe g, Utilisateur u) {
		g.deleteMember(u);
		try {
			database.removeUserInGroup(u.getIdUser(), g.getIdGroupe());
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
	}

	public void removeGroup(Groupe g) {
		allGroups.remove(g);
		try {
			database.removeGroupBD(g.getIdGroupe());
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
	}

	private void initAllGroups() {
		try {
			allGroups = (ArrayList<Groupe>) database.getAllGroupsBD();
		} catch (DataBaseException e) {
			e.printStackTrace();
		}

		for (Groupe groupe : allGroups) {
			try {
				groupe.initMembers(database.getUsersFromGroup(groupe.getIdGroupe()));
			} catch (DataBaseException e) {
				e.printStackTrace();
			}
		}
	}

	private void initAllUsers() {
		try {
			allUsers = (ArrayList<Utilisateur>) database.getAllUsers();
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
	}

	private void initAllFdd() {
		allFdd = database.getAllFilDeDiscussion();
		System.out.println("All fdd:" + allFdd);
	}

	private void initAllMessages() {
		allMessages = database.getAllMessages();
		System.out.println("All Messages:" + allMessages);
	}

	public void initMapAllMessage() {
		for (Message m : allMessages) {
			mapAllMessagesRead.put(m.getIdMsg(), new ArrayList<Utilisateur>());
			Groupe gRef = null;
			FilDeDiscussion fddRef = null;
			int idFilRef = m.getIdFil();
			for (FilDeDiscussion fdd : allFdd) {
				if (fdd.getIdFil() == idFilRef) {
					gRef = fdd.getGroupe();
					fddRef = fdd;
				}
			}
			if (!gRef.getListeUtilisateur().contains(m.getAuteur()))
				updateMessage(m);
			mapAllMessagesRead.get(m.getIdMsg()).addAll(gRef.getListeUtilisateur());
		}

	}

	// --------------------------------------------------------------------------------------------------------------------

	public void updateMessage(Message m) {
		List<?> temp = mapAllMessagesRead.get(m.getIdMsg());
		System.out.println("Je m'en fou de ta cond: " + temp.size());
		System.out.println(temp);
		if (!temp.contains(m.getAuteur()))
			mapAllMessagesRead.get(m.getIdMsg()).add(m.getAuteur());
			
	}

	public void addNewMessage(Message m) {
		mapAllMessagesRead.put(m.getIdMsg(), new ArrayList<Utilisateur>());
		System.out.println("Ajout d'un message " + m.getIdMsg());
	}

	public Map<Integer, List<Utilisateur>> getMapAllMessages() {
		return mapAllMessagesRead;
	}

	public void setMapAllMessages(Map<Integer, List<Utilisateur>> allMessages) {
		this.mapAllMessagesRead = mapAllMessagesRead;
	}

}
