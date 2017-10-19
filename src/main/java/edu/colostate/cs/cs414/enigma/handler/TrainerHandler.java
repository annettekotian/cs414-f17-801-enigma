package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class TrainerHandler {
	
	public static void createNewCustomer(String first, String last, String phone, String email, String insurance, String status) {
		
		// Attempt to find the health insurance in the db
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createNamedQuery("HealthInsurance.findDescription");
		query.setParameter("description", insurance);
		HealthInsurance healthInsurance = (HealthInsurance) query.getSingleResult();
		if(healthInsurance == null) {
			healthInsurance = new HealthInsurance(insurance);
		}
		
		// Get the membership status object from the db
		query = em.createNamedQuery("Membership.findType");
		query.setParameter("type", status);
		Membership membership = (Membership) query.getSingleResult();
		
		// Create a new personal information for the customer
		PersonalInformation personalInformation = new PersonalInformation(first, last, phone, email, healthInsurance);
		Customer customer = new Customer(personalInformation, membership);
		
		// Persist the new customer
		em.persist(customer);
		em.getTransaction().commit();
		em.close();
	}

	public static List<Customer> getAllCustomers() {
		
		// Issued a named query to the db for all customers
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createNamedQuery("Customer.findAll");
		List<Customer> customers = new ArrayList<Customer>();
		List<?> results = query.getResultList();
		for(int i=0; i<results.size(); i++) {
			customers.add((Customer) results.get(i));
		}
		em.close();
		em.getTransaction().commit();
		return customers;
	}
}
