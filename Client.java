package ChatApp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 8888);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

			System.out.print("Enter your username: ");
			String username = reader.readLine();

			System.out.print("Enter the room ID: ");
			String roomID = reader.readLine();

			writer.println(username);
			writer.println(roomID);

			new Thread(() -> {
				try {
					String serverMessage;
					while ((serverMessage = serverReader.readLine()) != null) {
						System.out.println(serverMessage);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();

			String userOption;
			while (true) {
				printOptions();
				System.out.print("Choose an option: ");
				userOption = reader.readLine();

				switch (userOption) {
				case "1":
					sendMessage(reader, writer);
					break;
				case "2":
					listActiveUsers(writer);
					break;
				case "3":
					listChatMessages(writer);
					break;
				case "4":
					exitChat(username, roomID, writer);
					break;
				default:
					System.out.println("Invalid option. Please choose again.");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printOptions() {
		System.out.println("Options:");
		System.out.println("1. Send Message");
		System.out.println("2. List Active Users");
		System.out.println("3. List Chat Messages");
		System.out.println("4. Exit");
	}

	private static void sendMessage(BufferedReader reader, PrintWriter writer) throws IOException {
		System.out.print("Enter your message: ");
		String userMessage = reader.readLine();
		writer.println(userMessage);
	}

	private static void listActiveUsers(PrintWriter writer) {
		writer.println("LIST_USERS");
	}

	private static void listChatMessages(PrintWriter writer) {
		writer.println("LIST_MESSAGES");
	}

	private static void exitChat(String username, String roomID, PrintWriter writer) {
		writer.println("exit");
		System.out.println(username + " left the chat room.");
		System.exit(0);
	}
}