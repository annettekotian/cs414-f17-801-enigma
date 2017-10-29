package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.Qualification;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.entity.WorkHours;
import edu.colostate.cs.cs414.enigma.entity.WorkHoursException;
import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.Customer;

public class TrainerHandler {
	
	private EntityManagerDao dao;
	
	public TrainerHandler() {
		this.dao = new EntityManagerDao();
	}
	
	public void close() {
		this.dao.close();
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
	
	
	public void addQualification(int trainerId, String qualification) {
		
		// Attempt to get the Qualification entity from the DB
		Map<String, Object> qualificationParams = new HashMap<String, Object>();
		qualificationParams.put("name", qualification);
		Qualification qualificationEntity = (Qualification) dao.querySingle("Qualification.findByName", qualificationParams);
		if(qualificationEntity == null) {
			qualificationEntity = new Qualification(qualification);
			dao.persist(qualificationEntity);
		}
		
		// Get the Trainer entity from the DB
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", trainerId);
		Trainer trainerEntity = (Trainer) dao.querySingle("Trainer.findById", trainerParams);
		
		// Add the qualification to the trainer
		trainerEntity.addQualification(qualificationEntity);
		dao.update(trainerEntity);
	}
	
	public void addWorkHours(int trainerId, Date startDateTime, Date endDateTime) throws WorkHoursException {
		
		// Verify the start date time is before the end date time
		if(startDateTime.compareTo(endDateTime) > 0) {
			throw new WorkHoursException("Start date time cannot be after end data time");
		}
		
		// Verify the start date time is not before the current date time
		Date currentDateTime = new Date();
		if(startDateTime.compareTo(currentDateTime) < 0) {
			throw new WorkHoursException("Start date time cannot occur in the past");
		}
		
		// Create new work hours
		WorkHours workHours = new WorkHours(startDateTime, endDateTime);
		
		// Get the Trainer entity from the DB
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", trainerId);
		Trainer trainerEntity = (Trainer) dao.querySingle("Trainer.findById", trainerParams);

		trainerEntity.addWorkHours(workHours);
		dao.update(trainerEntity);
	}
	
	/**
	 * Get a specific trainer by the trainer ID. Note that if the trainer does not exist, a null
	 * trainer will be returned.
	 * @param trainerId Trainer ID.
	 * @return Trainer.
	 */
	public Trainer getTrainerById(int trainerId) {		
		// Get a state entity/object
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", trainerId);
		
		// Get the trainer
		Trainer trainer = (Trainer) dao.querySingle("Trainer.findById", trainerParams);
				
		return trainer;		
	}
	
	/**Given the user id, this method returns the trainer object
	 * 
	 * @param userId
	 * @return: Trainer
	 */
	public Trainer getTrainerByUserId(int userId) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", userId);		
		// Get the trainer
		Trainer trainer = (Trainer) dao.querySingle("Trainer.findByUserId", params);
				
		return trainer;	
	}
	
	/**
	 * Get all the trainer employees of a gym system. Note that if no trainers exists, a empty
	 * list is returned.
	 * @return List of trainers.
	 */
	public List<Trainer> getAllTrainers() {
		
		// Issue a query to get all the customers
		List<Trainer> trainers = new ArrayList<Trainer>();
		List<?> results = dao.query("Trainer.findAll", null);
		for(int i=0; i<results.size(); i++) {
			trainers.add((Trainer) results.get(i));
		}
		
		return trainers;
	}

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
			if(trainer.searchString().contains(value)) {
				matchedTrainers.add(trainer);
			}
		}
		return matchedTrainers;
	}
	
	/**
	 * Delete a trainer based on a specific trainer ID.
	 * @param id ID of trainer to be deleted.
	 */
	public void deleteTrainer(int id) {
		// Open up a connection to the db
		dao = new EntityManagerDao();
		
		// Get the trainer entity to be updated
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", id);
		Trainer trainer = (Trainer) dao.querySingle("Trainer.findById", trainerParams);
		trainer.deleteWorkHours();
		
		// Delete the trainer
		dao.remove(trainer);
		
		// Shutdown connection to database
		dao.close();
	}
	
}
