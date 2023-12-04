package ChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
	private final Socket clientSocket;
	private final String username;
	private final String roomID;
	private BufferedReader reader;
	private PrintWriter writer;

	public ClientHandler(Socket clientSocket, String username, String roomID) {
		this.clientSocket = clientSocket;
		this.username = username;
		this.roomID = roomID;

		try {
			this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ChatRoom.getInstance().registerObserver(new User(username), roomID);
		ChatRoom.getInstance().addUser(username, roomID);
	}

	@Override
	public void run() {
		try {
			String clientMessage;
			while ((clientMessage = reader.readLine()) != null) {
				if (clientMessage.equalsIgnoreCase("exit")) {
					ChatRoom.getInstance().removeUser(username, roomID);
					break;
				} else if (clientMessage.equals("LIST_USERS")) {
					listActiveUsers();
				} else if (clientMessage.equals("LIST_MESSAGES")) {
					listChatMessages();
				} else {
					ChatRoom.getInstance().sendMessage(username, roomID, clientMessage);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void listActiveUsers() {
		writer.println("Active Users:");
		for (String user : ChatRoom.getInstance().listActiveUsers(roomID)) {
			writer.println(user);
		}
	}

	private void listChatMessages() {
		writer.println("Chat Messages:");
		for (String message : ChatRoom.getInstance().getMessages(roomID)) {
			writer.println(message);
		}
	}
}