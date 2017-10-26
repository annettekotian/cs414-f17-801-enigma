package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;

public class ManagerHandler extends GymSystemEmployeeHandler {
	

	/**
	 * Return a list of trainers matching a specific search value.
	 * @param value Value each trainer must have.
	 * @return List of trianers.
	 */
	public List<Trainer> searchTrainers(String value) {
		
		// Check to see if the toString() of each trainer contains the desired value
		List<Trainer> trainers = getAllTrainers();
		List<Trainer> matchedTrainers = new ArrayList<Trainer>();
		for(int i=0; i<trainers.size(); i++) {
			Trainer trainer = trainers.get(i);
			if(trainer.toString().contains(value)) {
				matchedTrainers.add(trainer);
			}
		}
		return matchedTrainers;
	}
	
	/**
	 * Manager specific function to create a new trainer.
	 * @param firstName First name of trainer.
	 * @param lastName Last name of the trainer.
	 * @param phoneNumber Phone number of trainer.
	 * @param email Email of trainer.
	 * @param street Street of the trainer.
	 * @param city City of the trainer.
	 * @param state State of the trainer.
	 * @param zipcode Zipcode of the trainer.
	 * @param healthInsurance Health insurance of the trainer.
	 * @param userName Unique user name of the trainer.
	 * @param password Password of the trainer.
	 * @return Trainer
	 * @throws PersistenceException
	 */
	public Trainer createNewTrainer(String firstName, String lastName, String phoneNumber, String email, String street,
			String city, String state, String zipcode, String healthInsurance, String userName, String password) throws PersistenceException {
		
		// Open up a connection to the db
		EntityManagerDao dao = new EntityManagerDao();
		
		// Get a state entity/object
		Map<String, Object> stateParams = new HashMap<String, Object>();
		stateParams.put("state", state);
		State stateEntity = (State) dao.querySingle("State.findState", stateParams);
		
		// Create an address for the new trainer
		Address addressEntity = new Address(street, city, zipcode, stateEntity);
		
		// Attempt to get a healthInsurance object
		Map<String, Object> healthInsuranceParams = new HashMap<String, Object>();
		healthInsuranceParams.put("name", healthInsurance);
		HealthInsurance healthInsuranceEntity = (HealthInsurance) dao.querySingle("HealthInsurance.findByName", healthInsuranceParams);
		if(healthInsuranceEntity == null) {
			healthInsuranceEntity = new HealthInsurance(healthInsurance);
		}
		
		// Create a personalInformation for the new trainer
		PersonalInformation personalInformationEntity = new PersonalInformation(email, firstName, lastName, phoneNumber,
				healthInsuranceEntity, addressEntity);
		
		// Get the trainer userLevel object for the new trainer
		Map<String, Object> userLevelParams = new HashMap<String, Object>();
		userLevelParams.put("level", "TRAINER");
		UserLevel userLevelEntity = (UserLevel) dao.querySingle("UserLevel.findLevel", userLevelParams);
		
		// Create a user for the new trainer
		User userEntity = new User(userName, password, userLevelEntity);
		
		// Create the new trainer
		Trainer trainer = new Trainer(personalInformationEntity, userEntity);
		
		// Persist the new trainers information with the db
		dao.persist(trainer);
		
		// Shutdown connection to database
		dao.close();
		
		return trainer;
	}
	
	/**
	 * Modify a trainer based of a specific trainer ID.
	 * @param id ID of trainer to be modified.
	 * @param firstName Modified first name of trainer.
	 * @param lastName Modified last name of the trainer.
	 * @param phoneNumber Modified phone number of trainer.
	 * @param email Modified email of trainer.
	 * @param street Modified street of the trainer.
	 * @param city Modified city of the trainer.
	 * @param state Modified state of the trainer.
	 * @param zipcode Modified zipcode of the trainer.
	 * @param healthInsurance Modified health insurance of the trainer.
	 * @param userName Modified unique user name of the trainer.
	 * @param password Modified password of the trainer.
	 * @return Trainer
	 * @throws PersistenceException
	 */
	public Trainer modifyTrainer(int id, String firstName, String lastName, String phoneNumber, String email, String street,
			String city, String state, String zipcode, String healthInsurance, String userName, String password) throws PersistenceException {
	
		// Open up a connection to the db
		EntityManagerDao dao = new EntityManagerDao();
		
		// Get the trainer entity to be updated
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", id);
		Trainer trainer = (Trainer) dao.querySingle("Trainer.findById", trainerParams);
		
		// Update the trainers information that does not require a new object
		PersonalInformation personalInformation = trainer.getPersonalInformation();
		personalInformation.setFirstName(firstName);
		personalInformation.setLastName(lastName);
		personalInformation.setEmail(email);
		personalInformation.setPhoneNumber(phoneNumber);
		if(!personalInformation.getHealthInsurance().getName().equals(healthInsurance)) {
			Map<String, Object> healthInsuranceParams = new HashMap<String, Object>();
			healthInsuranceParams.put("name", healthInsurance);
			HealthInsurance healthInsuranceEntity = (HealthInsurance) dao.querySingle("HealthInsurance.findByName", healthInsuranceParams);
			if(healthInsuranceEntity == null) {
				healthInsuranceEntity = new HealthInsurance(healthInsurance);
				dao.persist(healthInsuranceEntity);
			}
			personalInformation.setHealthInsurance(healthInsuranceEntity);
		}
		
		Address address = personalInformation.getAddress();
		address.setCity(city);
		address.setStreet(street);
		address.setZipcode(zipcode);
		if(!address.getState().getState().equals(state)) {
			Map<String, Object> stateParams = new HashMap<String, Object>();
			stateParams.put("state", state);
			State stateEntity = (State) dao.querySingle("State.findState", stateParams);
			address.setState(stateEntity);
		}
		
		User user = trainer.getUser();
		user.setUsername(userName);
		user.setPassword(password);
		
		
		// Modify/update the trainers information with the db
		dao.update(trainer);
				
		// Shutdown connection to database
		dao.close();
		
		return trainer;
	}
	
	/**
	 * Delete a trainer based on a specific trainer ID.
	 * @param id ID of trainer to be deleted.
	 */
	public void deleteTrainer(int id) {
		// Open up a connection to the db
		EntityManagerDao dao = new EntityManagerDao();
		
		// Get the trainer entity to be updated
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", id);
		Trainer trainer = (Trainer) dao.querySingle("Trainer.findById", trainerParams);
		
		// Delete the trainer
		dao.remove(trainer);
		
		// Shutdown connection to database
		dao.close();
	}
	
	/**
	 * 
	 * @param email: String
	 * @param firstName: String:
	 * @param lastName: String
	 * @param phoneNumber: String
	 * @param hiId: String health insruance id
	 * @param userName: String username of the manager using which he will log in
	 * @param userPass: String password for the username
	 * @param street: String password for the username
	 * @param city: String city
	 * @param zip: String zipcode
	 * @param state: String state
	 * @return
	 */
	public Manager createManager(String email, String firstName, String lastName, String phoneNumber, String insurance, String userName, String userPass,
			String confirmPassword, String street, String city, String zip, String state)  {
		
			
		
		// validations
		if(email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || insurance.isEmpty() || userName.isEmpty()
				|| userPass.isEmpty() || street.isEmpty() || city.isEmpty() || zip.isEmpty() || state.isEmpty()) {
			
			throw new IllegalArgumentException("Missing Input");
		}
		if(userPass.length()<8) {
			throw new IllegalArgumentException("Password short");
		}
		
		// password validations	
		if(!userPass.equals(confirmPassword) || userPass.length() < 8) {
			throw new IllegalArgumentException("Password error");
		}
		
		// Establish a connection to the database
		EntityManagerDao dao = new EntityManagerDao();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", insurance);
		HealthInsurance hiDB = (HealthInsurance) dao.querySingle("HealthInsurance.findByName", parameters);
		if(hiDB == null) {
			hiDB = new HealthInsurance(insurance);
		}
		
		Map<String, Object> stateParams = new HashMap<String, Object>();
		stateParams.put("state", state);
		State stateDB = (State) dao.querySingle("State.findState", stateParams);
		Address address = new Address(street, city, zip, stateDB);
		
		PersonalInformation p = new PersonalInformation(email, firstName, lastName, phoneNumber, hiDB, address);
		Map<String, Object> userLevelParams = new HashMap<String, Object>(); 
		userLevelParams.put("level", "MANAGER");
		UserLevel ul = (UserLevel) dao.querySingle("UserLevel.findLevel", userLevelParams);
		User user = new User( userName, userPass, ul);
		Manager manager= new Manager(p, user);
		
		// Persist the manager with the database
		
		dao.persist(manager);
		
		// Shutdown connection to database
		dao.close();
		
		
		return manager;		
		
	}
	
	public List<Manager> getAllManagers() {
		// Open up a connection to the db
			EntityManagerDao dao = new EntityManagerDao();
			
			// Issue a query to get all the customers
			List<Manager> managers = new ArrayList<Manager>();
			List<?> results = dao.query("Manager.findAll", null);
			for(int i=0; i<results.size(); i++) {
				managers.add((Manager) results.get(i));
			}

			// Shutdown connection to database
			dao.close();
			
			return managers;
	}
	
	

	/**
	 * this method returns the manager object by its id
	 * @param id: String the db id of the Manager
	 * @return Manager
	 */
	public Manager getMangerById(String id) {
		
		// Establish a connection to the database
		
		EntityManagerDao dao = new EntityManagerDao();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", Integer.parseInt(id));
		Manager m = (Manager) dao.querySingle("Manager.findById", parameters);
		
		dao.close();
		return m;
	}
	
	
	public List<Manager> searchManager(String keywords) {
		EntityManagerDao dao = new EntityManagerDao();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("keyword", "%" + keywords + "%");
		List<?> results = dao.query("Manager.findByKeywords", parameters);
		List<Manager> managers= new ArrayList<Manager>();
		
			for(int i=0; i<results.size(); i++) {
				managers.add((Manager) results.get(i));
			}
		dao.close();
		return managers;
	}
	

}
