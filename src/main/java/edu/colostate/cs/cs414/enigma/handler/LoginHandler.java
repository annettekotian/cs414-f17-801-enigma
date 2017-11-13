package edu.colostate.cs.cs414.enigma.handler;

import java.util.HashMap;
import java.util.Map;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.User;

public class LoginHandler extends GymSystemHandler {
	private User getUserByUsername(String username) {

		// Issued a named query for a specific username
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", username);
		User user = (User) getDao().querySingle("User.findUser", parameters);		
		return user;
	}
	
	public boolean authenticate(String username, String password) {
		User user = getUserByUsername(username);
		if(user == null) {
			return false;
		}
		return user.getPassword().equals(password);
	}
	
	public String getUserLevel(String username) throws IllegalArgumentException {
		User user = getUserByUsername(username);
		if(user == null) {
			throw new IllegalArgumentException("Invalid username");
		}
		return user.getUserLevel().getDescription();
	}

	public int getUserId(String username) throws IllegalArgumentException {
		User user = getUserByUsername(username);
		if(user == null) {
			throw new IllegalArgumentException("Invalid username");
		}
		return user.getId();
	}
}
