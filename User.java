package ChatApp;

import java.util.List;

public class User implements Observer {
	private final String username;

	public User(String username) {
		this.username = username;
	}

	@Override
	public void update(String username, String roomID, String message) {
		System.out.println("[" + roomID + "] " + username + ": " + message);
	}

	@Override
	public List<String> listActiveUsers(String roomID) {
		return List.of();
	}
}

