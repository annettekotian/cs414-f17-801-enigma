package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.State;

public class CustomerHandler {

	private EntityManagerDao dao;
	
	public CustomerHandler() {
		dao = new EntityManagerDao();
	}
	
	/**
	 * this method returns all the customers present in the db. 
	 * @return List
	 */
	public List<Customer> getCustomers() {
		List rawCustomers = dao.query("Customer.findAll", null);
		List<Customer> customers = new ArrayList<Customer>();
		for(int i=0; i<rawCustomers.size(); i++) {
			customers.add((Customer) rawCustomers.get(i));
		}
		close();
		return customers;
	}
	
	/**
	 * this method closes the db connection
	 */
	public void close() {
		dao.close();
	}
	
	/**
	 * This method searches the customer table and returns a list of customers matching the keyword. It looks for matches in 
	 * the firstName, last name, email, phone, city, state, zip, street, healthinsurance and membership.
	 * @param keywords: String
	 * @return
	 */
	public List<Customer> getCustomerByKeyword(String keywords) {
		
		EntityManagerDao dao = new EntityManagerDao();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("keyword", "%" + keywords + "%");
		List<?> results = dao.query("Customer.findByKeywords", parameters);
		List<Customer> customers= new ArrayList<Customer>();
		
			for(int i=0; i<results.size(); i++) {
				customers.add((Customer) results.get(i));
			}
		dao.close();
		return customers;
	}
	
	/**
	 * searches and returns the customer by the id.
	 * @param id
	 * @return Customer
	 */
	public Customer getCustomerById(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return (Customer) dao.querySingle("Customer.findById", params);
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
			
			throw new IllegalArgumentException("Missing input");
		}
		
		// Establish a connection to the database
		EntityManagerDao dao = new EntityManagerDao();

		// Get/generate the health insurance object
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("description", insurance);
		HealthInsurance healthInsurance = (HealthInsurance) dao.querySingle("HealthInsurance.findDescription",
				parameters);
		if(healthInsurance == null) {
			healthInsurance = new HealthInsurance(insurance);
		}
		
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
	
	/** This method edits a customer already in the db
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
	 * @param membershipStatus: String
	 * @return Customer: String
	 */
	
	public Customer updateCustomer(int id, String email, String firstName, String lastName, String phoneNumber,
			String insurance, String street, String city, String zip, String state, String membershipStatus) {

		
		if(email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || insurance.isEmpty() || 
				street.isEmpty() || city.isEmpty() || zip.isEmpty() || state.isEmpty() || membershipStatus.isEmpty()) {
			
			throw new IllegalArgumentException("Missing input");
		}
		
		// Establish a connection to the database
		EntityManagerDao dao = new EntityManagerDao();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		Customer c = (Customer) dao.querySingle("Customer.findById", parameters);
		// update personal information
		PersonalInformation p = c.getPersonalInformation();
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setEmail(email);
		p.setPhoneNumber(phoneNumber);
		
		// update membership
		parameters = new HashMap<String, Object>();
		parameters.put("type", membershipStatus);
		Membership m = (Membership) dao.querySingle("Membership.findType", parameters);
		c.setMembership(m);
		// update address
		Address a = p.getAddress();
		a.setCity(city);
		a.setStreet(street);
		a.setZipcode(zip);
		if(!p.getHealthInsurance().getName().equals(insurance)) {
			Map<String, Object> healthInsuranceParams = new HashMap<String, Object>();
			healthInsuranceParams.put("name", insurance);
			HealthInsurance healthInsuranceEntity = (HealthInsurance) dao.querySingle("HealthInsurance.findByName", healthInsuranceParams);
			if(healthInsuranceEntity == null) {
				healthInsuranceEntity = new HealthInsurance(insurance);
			}
			p.setHealthInsurance(healthInsuranceEntity);
		}
		State s = a.getState();
		if(!s.getState().equals(state)) {
			Map<String, Object> stateParams = new HashMap<String, Object>();
			stateParams.put("state", state);
			State stateEntity = (State) dao.querySingle("State.findState", stateParams);
			a.setState(stateEntity);
		}
		
		

		// Persist the customer with the database
		dao.update(c);

		// Shutdown connection to database
		dao.close();
		
		return c;
	}
}
