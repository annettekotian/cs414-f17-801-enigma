package edu.colostate.cs.cs414.enigma.handler;

import java.util.HashMap;
import java.util.Map;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.User;

public class LoginHandler {
	private static User getUserByUsername(String username) {
		// Connect to the database
		EntityManagerDao dao = new EntityManagerDao();
		
		// Issued a named query for a specific username
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", username);
		User user = (User) dao.querySingle("User.findUser", parameters);

		// Close connection to the database
		dao.close();
		
		return user;
	}
	
	public static boolean authenticate(String username, String password) {
		User user = getUserByUsername(username);
		if(user == null) {
			return false;
		}
		return user.getPassword().equals(password);
	}
	
	public static String getUserLevel(String username) throws IllegalArgumentException {
		User user = getUserByUsername(username);
		if(user == null) {
			throw new IllegalArgumentException("Invalid username");
		}
		return user.getUserLevel().getDescription();
	}

	public static int getUserId(String username) throws IllegalArgumentException {
		User user = getUserByUsername(username);
		if(user == null) {
			throw new IllegalArgumentException("Invalid username");
		}
		return user.getId();
	}
}
