package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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

public class ManagerHandler  {
	
	private EntityManagerDao dao;
	
	public ManagerHandler() {
		dao = new EntityManagerDao();
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
			String confirmPassword, String street, String city, String zip, String state) throws AddressException  {
		
			
		
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
		if(!zip.matches("^[0-9]{5}$")) {
			throw new IllegalArgumentException("Zipcode must be 5 digits");
		}
		if(!phoneNumber.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$")) {
			throw new IllegalArgumentException("Phone number must be 10 digits in format ###-###-####");
		}
		
		// validate email
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException e) {
			throw e;
		}
		
		
		// Establish a connection to the database
		dao = new EntityManagerDao();
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
	
	/**
	 * This method searches the customer table and returns a list of customers matching the keyword. It looks for matches in 
	 * the firstName, last name, email, phone, city, state, zip, street, health insurance and membership. 
	 * @param keywords
	 * @return
	 */
	
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
