package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;

public class TrainerHandler {
	
	public static List<Customer> getAllCustomers() {
		
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
}
