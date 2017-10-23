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

public class ManagerHandler {
	
	public static List<Trainer> getAllTrainers() {
		
		// Open up a connection to the db
		EntityManagerDao dao = new EntityManagerDao();
		
		// Issue a query to get all the customers
		List<Trainer> trainers = new ArrayList<Trainer>();
		List<?> results = dao.query("Trainer.findAll", null);
		for(int i=0; i<results.size(); i++) {
			trainers.add((Trainer) results.get(i));
		}

		// Shutdown connection to database
		dao.close();
		
		return trainers;
	}
	
	public Trainer getTrainerById(int trainerId) {
		// Open up a connection to the db
		EntityManagerDao dao = new EntityManagerDao();
		
		// Get a state entity/object
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", trainerId);
		
		// Get the trainer
		Trainer trainer = (Trainer) dao.querySingle("Trainer.findById", trainerParams);
		
		// Shutdown connection to database
		dao.close();
				
		return trainer;		
	}
	
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
	
	public void modifyTrainer(Trainer trainer) {
		EntityManagerDao dao = new EntityManagerDao();
		dao.update(trainer);
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
	public Manager createManager(String email, String firstName, String lastName, String phoneNumber, String hiId, String userName, String userPass,
			String street, String city, String zip, String state)  {
		
				
		// validations
		if(email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || hiId.isEmpty() || userName.isEmpty()
				|| userPass.isEmpty() || street.isEmpty() || city.isEmpty() || zip.isEmpty() || state.isEmpty()) {
			
			return null;
		}
		
		// Establish a connection to the database
		EntityManagerDao dao = new EntityManagerDao();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", Integer.parseInt(hiId));
		HealthInsurance hiDB = (HealthInsurance) dao.querySingle("HealthInsurance.findId", parameters);
		
		
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
		
		// Persist the customer with the database
		
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
	
	
	/** This method creates a new customer in the db
	 * 
	 * @param email: String
	 * @param firstName: String
	 * @param lastName: String
	 * @param phoneNumber: String
	 * @param insurance: String
	 * @param userName: String
	 * @param userPass: String
	 * @param street: String
	 * @param city: String
	 * @param zip: String
	 * @param state: String
	 * @return Customer: String
	 */
	public Customer createNewCustomer(String email, String firstName, String lastName, String phoneNumber,
			String insurance, String street, String city, String zip, String state, String membershipStatus) {

		
		if(email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || insurance.isEmpty() || 
				street.isEmpty() || city.isEmpty() || zip.isEmpty() || state.isEmpty() || membershipStatus.isEmpty()) {
			
			return null;
		}
		
		// Establish a connection to the database
		EntityManagerDao dao = new EntityManagerDao();

		// Get/generate the health insurance object
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("description", insurance);
		HealthInsurance healthInsurance = (HealthInsurance) dao.querySingle("HealthInsurance.findDescription",
				parameters);
		
		// Get the membership object based on type
		parameters = new HashMap<String, Object>();
		parameters.put("type", membershipStatus);
		Membership membership = (Membership) dao.querySingle("Membership.findType", parameters);
		
		// get the state based on the state name
		Map<String, Object> stateParams = new HashMap<String, Object>();
		stateParams.put("state", state);
		State stateDB = (State) dao.querySingle("State.findState", stateParams);
		Address address = new Address(street, city, zip, stateDB);
		

		// Create a new personal information for the customer
		PersonalInformation personalInformation = new PersonalInformation(email, firstName, lastName,  phoneNumber, healthInsurance,
				address);
		Customer customer = new Customer(personalInformation, membership);

		// Persist the customer with the database
		dao.persist(customer);

		// Shutdown connection to database
		dao.close();
		
		return customer;
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
