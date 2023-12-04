package ChatApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatRoom implements Subject {
	private static ChatRoom instance;
	private final Map<String, List<Observer>> observersMap;
	private final Map<String, List<String>> messagesMap;
	private final Map<String, List<String>> activeUsersMap;

	private ChatRoom() {
		this.observersMap = new HashMap<>();
		this.messagesMap = new HashMap<>();
		this.activeUsersMap = new HashMap<>();
	}

	public static ChatRoom getInstance() {
		if (instance == null) {
			instance = new ChatRoom();
		}
		return instance;
	}

	@Override
	public void registerObserver(Observer observer, String roomID) {
		observersMap.computeIfAbsent(roomID, k -> new CopyOnWriteArrayList<>()).add(observer);
	}

	@Override
	public void removeObserver(Observer observer, String roomID) {
		observersMap.getOrDefault(roomID, new CopyOnWriteArrayList<>()).remove(observer);
	}

	@Override
	public void notifyObservers(String username, String roomID, String message) {
		List<Observer> observers = observersMap.getOrDefault(roomID, new CopyOnWriteArrayList<>());
		for (Observer observer : observers) {
			observer.update(username, roomID, message);
		}
	}

	@Override
	public List<String> listActiveUsers(String roomID) {
		return new CopyOnWriteArrayList<>(activeUsersMap.getOrDefault(roomID, new CopyOnWriteArrayList<>()));
	}

	public void addUser(String username, String roomID) {
		activeUsersMap.computeIfAbsent(roomID, k -> new CopyOnWriteArrayList<>()).add(username);
		notifyObservers(username, roomID, username + " joined the chat room.");
	}

	public void removeUser(String username, String roomID) {
		activeUsersMap.getOrDefault(roomID, new CopyOnWriteArrayList<>()).remove(username);
		notifyObservers(username, roomID, username + " left the chat room.");
	}

	public void sendMessage(String username, String roomID, String message) {
		String formattedMessage = "[" + username + "]: " + message;
		messagesMap.computeIfAbsent(roomID, k -> new CopyOnWriteArrayList<>()).add(formattedMessage);
		notifyObservers(username, roomID, formattedMessage);
	}

	public List<String> getMessages(String roomID) {
		return new CopyOnWriteArrayList<>(messagesMap.getOrDefault(roomID, new CopyOnWriteArrayList<>()));
	}
}