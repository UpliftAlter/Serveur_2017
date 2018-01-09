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
		String url = "jdbc:mysql://localhost:3306/base_de_donnees_neocampus?autoReconnect=true&useSSL=false";
		String username = "root";
		String mdp = "root";
		Connection connexion = null;

		try {
			connexion = DriverManager.getConnection(url, username, mdp);

			Statement statement = connexion.createStatement();
			ResultSet resultat1 = statement
					.executeQuery("SELECT * FROM GROUPE");

			while (resultat1.next()) {
				int numGroupe = resultat1.getInt("ID_GROUPE");
				String nomGroupe = resultat1.getString("NOM_GROUPE");
				Groupe g = new Groupe(nomGroupe, numGroupe);
				this.allGroups.add(g);
			}

			for (Groupe groupe : allGroups) {

				int numGroupe = groupe.getIdGroupe();

				String req = "SELECT * FROM UTILISATEUR WHERE ID_UTILISATEUR IN (SELECT ID_UTILISATEUR FROM APPARTENIR WHERE ID_GROUPE = "
						+ numGroupe + ")";
				System.out.println(req);
				ResultSet resultat2 = statement.executeQuery(req);
				while (resultat2.next()) {
					int id_utilisateur = resultat2.getInt("ID_UTILISATEUR");
					String identifiant = resultat2.getString("IDENTIFIANT");

					String mdp2 = resultat2.getString("MOT_DE_PASSE");

					String nom = resultat2.getString("NOM_UTILISATEUR");
					String prenom = resultat2.getString("PRENOM_UTILISATEUR");
					String type = resultat2.getString("TYPE_UTILISATEUR");

					Utilisateur u = null;
					switch (type) {
					case "ETUDIANT":
						u = new Etudiant(nom, prenom, mdp2, identifiant,
								id_utilisateur);
						break;

					default:
						break;
					}

					groupe.addMember(u);

					System.out.println(u);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connexion != null)
				try {
					/* Fermeture de la connexion */
					connexion.close();
				} catch (SQLException ignore) {
					ignore.printStackTrace();
				}
		}
	}

	private void initAllUsers() {
		String url = "jdbc:mysql://localhost:3306/base_de_donnees_neocampus?autoReconnect=true&useSSL=false";
		String username = "root";
		String mdp = "root";
		Connection connexion = null;

		try {
			connexion = DriverManager.getConnection(url, username, mdp);

			Statement statement = connexion.createStatement();
			ResultSet resultat1 = statement
					.executeQuery("SELECT * FROM Utilisateur");

			while (resultat1.next()) {
				int numGroupe = resultat1.getInt("ID_UTILISATEUR");
				String type = resultat1.getString("TYPE_UTILISATEUR");
				String nom = resultat1.getString("NOM_UTILISATEUR");
				String prenom = resultat1.getString("PRENOM_UTILISATEUR");

				Utilisateur u = null;
				switch (type) {
				case "ETUDIANT":
					u = new Etudiant(nom, prenom);
					break;
				// ALED TOTO FAUT PAS OUBLIE DE FAIRE LE RESTE
				default:
					break;
				}
				this.allUsers.add(u);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connexion != null)
				try {
					/* Fermeture de la connexion */
					connexion.close();
				} catch (SQLException ignore) {
					ignore.printStackTrace();
				}
		}
	}

}
