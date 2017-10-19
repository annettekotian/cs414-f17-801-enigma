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
	
	public static void createNewCustomer(String first, String last, String phone, String email, String insurance, String status) {
		
		// Establish a connection to the database
		EntityManagerDao dao = new EntityManagerDao();
		
		// Get/generate the health insurance object
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("description", insurance);
		HealthInsurance healthInsurance = (HealthInsurance) dao.querySingle("HealthInsurance.findDescription", parameters);
		if(healthInsurance == null) {
			healthInsurance = new HealthInsurance(insurance);
		}
		
		// Get the membership object based on type
		parameters = new HashMap<String, Object>();
		parameters.put("type", status);
		Membership membership = (Membership) dao.querySingle("Membership.findType", parameters);
	
		// Create a new personal information for the customer
		PersonalInformation personalInformation = new PersonalInformation(first, last, phone, email, healthInsurance);
		Customer customer = new Customer(personalInformation, membership);
		
		// Persist the customer with the database
		dao.persist(customer);
		
		// Shutdown connection to database
		dao.close();
	}

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
