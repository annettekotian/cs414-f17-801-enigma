package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.Qualification;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;

public class TrainerHandler {
	
	private EntityManagerDao dao;
	
	public TrainerHandler() {
		this.dao = new EntityManagerDao();
	}
	
	public void close() {
		this.dao.close();
	}
	
	public List<Customer> getAllCustomers() {
		
		// Establish a connection to the database
		EntityManagerDao dao = new EntityManagerDao();
		
		// Issue a query to get all the customers
		List<Customer> customers = new ArrayList<Customer>();
		List<?> results = dao.query("Customer.findAll", null);
		for(int i=0; i<results.size(); i++) {
			customers.add((Customer) results.get(i));
		}

		// Shutdown connection to database
		dao.close();
		
		return customers;
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
}
