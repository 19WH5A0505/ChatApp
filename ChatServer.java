package ChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	private static final int PORT = 8888;

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("Server is running on port " + PORT);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("New connection: " + clientSocket);

				String[] userData = getClientData(clientSocket);

				String username = userData[0];
				String roomID = userData[1];

				System.out.println(username + " joined the chat room with Room ID: " + roomID);

				if (isValidRoomID(roomID)) {
					ClientHandler clientHandler = new ClientHandler(clientSocket, username, roomID);
					new Thread(clientHandler).start();
				} else {
					System.out.println("Invalid Room ID. Connection refused for " + username);
					try {
						clientSocket.getOutputStream().write("Invalid Room ID. Connection refused.".getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
					clientSocket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String[] getClientData(Socket clientSocket) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String username = reader.readLine();
			String roomID = reader.readLine();
			return new String[]{username, roomID};
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String[]{};
	}

	private static boolean isValidRoomID(String roomID) {
		return !roomID.isEmpty();
	}
}