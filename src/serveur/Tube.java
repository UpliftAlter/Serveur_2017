package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import classes.FilDeDiscussion;
import classes.Message;

public class Tube implements Runnable {
	private Serveur server;
	private Socket socket;
	private ObjectInputStream inputFromClient;
	private ObjectOutputStream outputToClient;
	private GestionMessage gestionMessage = new GestionMessage(this);

	public Tube(Serveur server, Socket s) {
		this.server = server;
		this.socket = s;
	}

	@Override
	public void run() {
		try {
			while (true) {
				receive();
			}
		} catch (IOException e) {
			System.out.println("Someone has disconnected");
			server.getAllSockets().remove(socket);
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found !");
		}

	}

	public void receive() throws IOException, ClassNotFoundException {
		if (socket.isConnected()) {
			inputFromClient = new ObjectInputStream(socket.getInputStream());
			Object temp = inputFromClient.readObject();
			if (temp != null) {
				if (temp instanceof Message) {
					Message message = (Message) temp;
					gestionMessage.message(message);
				} else if (temp instanceof FilDeDiscussion) {
					FilDeDiscussion fdd = (FilDeDiscussion) temp;
					gestionMessage.filDeDiscussion(fdd);
				}
			}
		}
	}

	public void send(Object object) {
		try {
			outputToClient = new ObjectOutputStream(socket.getOutputStream());
			outputToClient.writeObject(object);
			outputToClient.flush();
		} catch (IOException e) {
			System.out.println("Error sending message from server to client");
		}

	}
	
	public void send(Socket client, Object object) {
		try {
			outputToClient = new ObjectOutputStream(client.getOutputStream());
			outputToClient.writeObject(object);
			outputToClient.flush();
		} catch (IOException e) {
			System.out.println("Error sending message from server to client");
		}

	}

	public void broadcast(List<Socket> list, Message msgtobroad) {
		for (Socket stemp : list)
			send(stemp, msgtobroad);
	}
}
