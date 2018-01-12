package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import classes.DB;
import classes.Message;

public class Authentification implements Runnable {
	private Socket socket;
	private Serveur serveur;
	private ObjectInputStream inputFromServer;
	private ObjectOutputStream outputToServer;
	private GestionMessage gm;

	public Authentification(Serveur serveur, Socket socket) {
		this.serveur = serveur;
		this.socket = socket;
		gm = new GestionMessage(this);
		
	}

	@Override
	public void run() {
		receive();
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

	@SuppressWarnings("unused")
	public void receive() {
		try {
			inputFromServer = new ObjectInputStream(socket.getInputStream());
			Object temp = null;
			temp = inputFromServer.readObject();

			if (temp != null) {
				if (temp instanceof Message) {
					Message message = (Message) temp;
					gm.message(message);
				}
			}
		} catch (IOException e) {
			System.out.println("Erreur reception login");
		} catch (ClassNotFoundException e) {
			System.err.println("Erreur de classe dans authentification");
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public Serveur getServeur() {
		return serveur;
	}
	
	

}
