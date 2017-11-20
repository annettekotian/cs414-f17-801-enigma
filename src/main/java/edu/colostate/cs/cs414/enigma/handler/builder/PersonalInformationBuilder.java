package edu.colostate.cs.cs414.enigma.handler.builder;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;

import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.handler.GymSystemHandler;

public abstract class PersonalInformationBuilder extends GymSystemHandler {

	protected String firstName;
	protected String lastName;
	protected String phoneNumber;
	protected String email;
	protected String street;
	protected String city;
	protected String state;
	protected String zipcode;
	protected String healthInsurance;
	
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
	
	protected void modifyPersonalInformation(PersonalInformation info) throws AddressException {
		// Need to get state object
		Map<String, Object> stateParams = new HashMap<String, Object>();
		stateParams.put("state", this.state);
		State stateEntity = (State) getDao().querySingle("State.findState", stateParams);
		
		// Update the trainer entity
		info.setFirstName(this.firstName);
		info.setLastName(this.lastName);
		info.setPhoneNumber(this.phoneNumber);
		info.setEmail(this.email);
		info.getAddress().setStreet(this.street);
		info.getAddress().setCity(this.city);
		info.getAddress().setZipcode(this.zipcode);
		info.getAddress().setState(stateEntity);
		info.setHealthInsurance(getHealthInsurance());
	}
}
