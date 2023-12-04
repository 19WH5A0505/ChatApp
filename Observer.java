package ChatApp;

import java.util.List;

public interface Observer {
	void update(String username, String roomID, String message);

	List<String> listActiveUsers(String roomID);
}
