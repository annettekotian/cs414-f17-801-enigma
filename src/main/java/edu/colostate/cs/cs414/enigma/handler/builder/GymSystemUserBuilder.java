package edu.colostate.cs.cs414.enigma.handler.builder;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;

import edu.colostate.cs.cs414.enigma.entity.GymSystemUser;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;

public abstract class GymSystemUserBuilder extends PersonalInformationBuilder {

	protected String username;
	protected String password;
	protected String confirmPassword;
	protected int id;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	protected User createUser() {
		
		if(this.confirmPassword != null) {
			if(!this.confirmPassword.equals(this.password)) {
				throw new IllegalArgumentException("Passwords do not match");
			}
		}
		
		// Get the trainer userLevel object for the new trainer
		Map<String, Object> userLevelParams = new HashMap<String, Object>();
		userLevelParams.put("level", "TRAINER");
		UserLevel userLevelEntity = (UserLevel) getDao().querySingle("UserLevel.findLevel", userLevelParams);
		
		// Create a user for the new trainer
		User userEntity = new User(this.username, this.password, userLevelEntity);
		
		return userEntity;
	}
	
	protected GymSystemUser modifyGymSystemUser(GymSystemUser user) throws AddressException {
		this.modifyPersonalInformation(user.getPersonalInformation());
		
		user.getUser().setUsername(this.username);
		user.getUser().setPassword(this.password);
		
		// Modify/update the trainers information with the db
		getDao().update(user);
		
		return user;
	}
}
