package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Set;

import classes.DB;
import classes.Message;
import classes.Utilisateur;

public class Authentification implements Runnable {
	private Socket socket;
	private Serveur serveur;
	private ObjectInputStream inputFromServer;
	private ObjectOutputStream outputToServer;
	private DB database = new DB();
	private boolean notlogged = true;

	public Authentification(Serveur serveur, Socket socket) {
		this.serveur = serveur;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			while (notlogged)
				receive();
		} catch (IOException e) {
			System.out.println("Someone has disconnected wrong authen");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found authentification");
		}
	}

	public void send(Object object) {
		try {
			outputToServer = new ObjectOutputStream(socket.getOutputStream());
			outputToServer.writeObject(object);
			outputToServer.flush();
		} catch (IOException e) {
			System.out.println("Error sending message from server to client");
		}
	}

	public void receive() throws ClassNotFoundException, IOException {

		inputFromServer = new ObjectInputStream(socket.getInputStream());
		Object temp = null;
		temp = inputFromServer.readObject();

		if (temp != null) {
			if (temp instanceof Message) {
				Message message = (Message) temp;
				gererLogin(message);
			}
		}

	}

	private void gererLogin(Message message) {
		String[] login = getLogin(message.getMsg());
		Utilisateur uTemp = database.login(login[0], login[1]);
		if (uTemp == null)
			System.out.println("hey");
		send(uTemp);
		if (uTemp != null) {
			notlogged = false;
			Thread t = new Thread(new Tube(serveur, socket, uTemp.getIdUser()));
			serveur.addUserSocket(uTemp, socket);
			t.start();
		}
	}



	private String[] getLogin(String s) {
		String[] toReturn = s.split("#");
		return toReturn;
	}

	public Socket getSocket() {
		return socket;
	}

	public Serveur getServeur() {
		return serveur;
	}

}
