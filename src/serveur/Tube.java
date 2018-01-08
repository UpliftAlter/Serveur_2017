package serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import utilisateur.Message;

public class Tube implements Runnable {
	private Serveur server;
	private Socket socket;
	private ObjectInputStream inputFromClient;
	private ObjectOutputStream outputToClient;

	public Tube(Serveur server, Socket s) {
		this.server = server;
		this.socket = s;
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println(server.getAllSockets());
				if (socket.isConnected()) {
					inputFromClient = new ObjectInputStream(
							socket.getInputStream());
					Object temp = inputFromClient.readObject();
					if (temp != null) {
						if (temp instanceof Message) {
							Message message = (Message) temp;
							broadcast(server.getAllSockets(), message);
						}
					}
				}
			}

		} catch (IOException e) {
			System.out.println("Someone has disconnected");
			server.getAllSockets().remove(socket);
			System.out.println(server.getAllSockets());
			
		} catch (ClassNotFoundException e) {
			System.out.println("Class non connu");
		}
		
	}

	public void broadcast(List<Socket> list, Message tobroad)
			throws IOException {
		
		System.out.println(list);
		for (Socket stemp : list) {
			outputToClient = new ObjectOutputStream(stemp.getOutputStream());
			outputToClient.writeObject(tobroad);
			outputToClient.flush();
		}
	}
}
