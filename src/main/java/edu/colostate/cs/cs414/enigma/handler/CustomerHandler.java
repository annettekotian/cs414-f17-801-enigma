package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Workout;

public class CustomerHandler extends GymSystemHandler {
	
	/**
	 * this method returns all the customers present in the db. 
	 * @return List
	 */
	public List<Customer> getCustomers() {
		List rawCustomers = getDao().query("Customer.findAll", null);
		List<Customer> customers = new ArrayList<Customer>();
		for(int i=0; i<rawCustomers.size(); i++) {
			customers.add((Customer) rawCustomers.get(i));
		}
		return customers;
	}
	
	/**
	 * This method searches the customer table and returns a list of customers matching the keyword. It looks for matches in 
	 * the firstName, last name, email, phone, city, state, zip, street, healthinsurance and membership.
	 * @param keywords: String
	 * @return
	 */
	public List<Customer> getCustomerByKeyword(String keywords) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("keyword", "%" + keywords + "%");
		List<?> results = getDao().query("Customer.findByKeywords", parameters);
		List<Customer> customers= new ArrayList<Customer>();
		
			for(int i=0; i<results.size(); i++) {
				customers.add((Customer) results.get(i));
			}
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
		return (Customer) getDao().querySingle("Customer.findById", params);
	}
	
	public Customer getCustomerByUserId(int userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", userId);
		return (Customer) getDao().querySingle("Customer.findByUserId", params);
	}
	
	public Workout getWorkoutById(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return (Workout) getDao().querySingle("Workout.findId", params);
	}

	
	public void deleteCustomer(String id) {
		int cId = Integer.parseInt(id);
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("id", cId);
		Customer c = (Customer) getDao().querySingle("Customer.findById", params);
		if(c==null) {
			return;
		}
		getDao().remove(c);
	}
	
	public void addFeedback(int customerId, int workoutId, String feedback) {
		Customer customer = this.getCustomerById(customerId);
		Workout workout = this.getWorkoutById(workoutId);
		workout.addFeedback(customer, feedback);
		this.getDao().update(workout);
	}
}
