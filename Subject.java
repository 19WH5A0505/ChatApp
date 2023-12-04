package ChatApp;

import java.util.List;

public interface Subject {
	void registerObserver(Observer observer, String roomID);

	void removeObserver(Observer observer, String roomID);

	void notifyObservers(String username, String roomID, String message);

	List<String> listActiveUsers(String roomID);
}
