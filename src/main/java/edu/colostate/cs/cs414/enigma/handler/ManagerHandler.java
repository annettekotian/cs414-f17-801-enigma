package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.GymSystemUser;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
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
	
	/**
	 * 
	 * @param email: String
	 * @param firstName: String:
	 * @param lastName: String
	 * @param phoneNumber: String
	 * @param hiId: health insruance id
	 * @param userName: username of the manager using which he will log in
	 * @param userPass: password for the username
	 * @return
	 */
	public boolean createManager(String email, String firstName, String lastName, String phoneNumber, String hiId, String userName, String userPass) {
		
		// Establish a connection to the database
		EntityManagerDao dao = new EntityManagerDao();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", Integer.parseInt(hiId));
		HealthInsurance hiDB = (HealthInsurance) dao.querySingle("HealthInsurance.findId", parameters);
		PersonalInformation p = new PersonalInformation(email, firstName, lastName, phoneNumber, hiDB);
		Map<String, Object> userLevelParams = new HashMap<String, Object>(); 
		userLevelParams.put("level", "MANAGER");
		UserLevel ul = (UserLevel) dao.querySingle("UserLevel.findLevel", userLevelParams);
		User user = new User( userName, userPass, ul);
		Manager manager= new Manager(p, user);
		
		// Persist the customer with the database
		dao.persist(manager);
		
		// Shutdown connection to database
		dao.close();
		
		
		return true;		
		
	}

}
