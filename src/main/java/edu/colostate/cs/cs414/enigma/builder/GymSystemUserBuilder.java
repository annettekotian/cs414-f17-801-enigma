package edu.colostate.cs.cs414.enigma.builder;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;

import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.GymSystemUser;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.handler.GymSystemHandler;

public abstract class GymSystemUserBuilder extends GymSystemHandler {

	protected String firstName;
	protected String lastName;
	protected String phoneNumber;
	protected String email;
	protected String street;
	protected String city;
	protected String state;
	protected String zipcode;
	protected String healthInsurance;
	protected String username;
	protected String password;
	protected String confirmPassword;
	protected int id;
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setHealthInsurance(String healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

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
	
	protected HealthInsurance getHealthInsurance() {
		Map<String, Object> healthInsuranceParams = new HashMap<String, Object>();
		healthInsuranceParams.put("name", this.healthInsurance);
		HealthInsurance healthInsuranceEntity = (HealthInsurance) getDao().querySingle("HealthInsurance.findByName", healthInsuranceParams);
		if(healthInsuranceEntity == null) {
			healthInsuranceEntity = new HealthInsurance(this.healthInsurance);
		}
		return healthInsuranceEntity;
	}
	
	protected PersonalInformation createPersonalInformation() throws AddressException {
		// Get a state entity/object
		Map<String, Object> stateParams = new HashMap<String, Object>();
		stateParams.put("state", this.state);
		State stateEntity = (State) getDao().querySingle("State.findState", stateParams);
		
		// Create an address for the new trainer
		Address addressEntity = new Address(this.street, this.city, this.zipcode, stateEntity);
		
		// Create a personalInformation for the new trainer
		HealthInsurance healthInsurance = this.getHealthInsurance();
		PersonalInformation personalInformationEntity = new PersonalInformation(this.email, this.firstName, this.lastName, this.phoneNumber, healthInsurance, addressEntity);
		
		return personalInformationEntity;
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
		// Need to get state object
		Map<String, Object> stateParams = new HashMap<String, Object>();
		stateParams.put("state", this.state);
		State stateEntity = (State) getDao().querySingle("State.findState", stateParams);
		
		// Update the trainer entity
		user.getPersonalInformation().setFirstName(this.firstName);
		user.getPersonalInformation().setLastName(this.lastName);
		user.getPersonalInformation().setPhoneNumber(this.phoneNumber);
		user.getPersonalInformation().setEmail(this.email);
		user.getPersonalInformation().getAddress().setStreet(this.street);
		user.getPersonalInformation().getAddress().setCity(this.city);
		user.getPersonalInformation().getAddress().setZipcode(this.zipcode);
		user.getPersonalInformation().getAddress().setState(stateEntity);
		user.getPersonalInformation().setHealthInsurance(getHealthInsurance());
		user.getUser().setUsername(this.username);
		user.getUser().setPassword(this.password);
		
		// Modify/update the trainers information with the db
		getDao().update(user);
		
		return user;
	}
}
