package edu.colostate.cs.cs414.enigma.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Trainer;

public class CustomerDao extends EntityManagerDaoOld<Customer> {
	
	/**
	 * Get a customer by the unique primary ID key from the GymSystem database
	 * customer table. Note that this function will attach the Cmerusto entity with
	 * the underlying entity manager.
	 * @param id Primary ID key.
	 * @return Customer object on success, else null.
	 * @see Customer
	 */
	public Customer findCustomerById(int id) {
		return this.getEntityManager().find(Customer.class, id);
	}
	
	/**
	 * Get all the customers in the GymSystem database customer table.
	 * Note that this function will attach the Customer entity with the underlying entity
	 * manager.
	 * @return List of Customer entities.
	 * @see Customer
	 */
	public List<Customer> getCustomers() {
		
		// Issue a NamedQuery found in Trainer.class
		Query query = this.getEntityManager().createNamedQuery("Customer.findAll");
		List<Customer> customers = new ArrayList<Customer>();
		List<?> results = query.getResultList();
		for(int i=0; i<results.size(); i++) {
			customers.add((Customer) results.get(i));
		}
		return customers;
	}
}
