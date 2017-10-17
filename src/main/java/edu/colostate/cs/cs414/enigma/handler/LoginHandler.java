package edu.colostate.cs.cs414.enigma.handler;

import edu.colostate.cs.cs414.enigma.dao.UserDao;
import edu.colostate.cs.cs414.enigma.entity.User;

public class LoginHandler {
	
	private UserDao userDao;
	
	public LoginHandler() {
		userDao = new UserDao();
	}
	
	public void close() {
		userDao.close();
	}

	public boolean authenticate(String username, String password) {
		User user = userDao.findUserByUserName(username);
		if(user == null) {
			return false;
		}
		return user.getPassword().equals(password);
	}
	
	public String getUserLevel(String username) {
		return userDao.findUserByUserName(username).getUserLevel().getDescription();
	}

	public int getUserId(String username) {
		return userDao.findUserByUserName(username).getId();
	}
}
