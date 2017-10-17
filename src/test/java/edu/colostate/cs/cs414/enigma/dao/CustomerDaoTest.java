package edu.colostate.cs.cs414.enigma.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class CustomerDaoTest {
	
	private static EntityManagerFactoryListener emfl;
	private List<Customer> addedCustomer;
	private List<HealthInsurance> addedHealthInsurance;
	private HealthInsuranceDao healthInsuranceDao;
	private CustomerDao customerDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emfl = new EntityManagerFactoryListener();
		emfl.contextInitialized(null);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		emfl.contextDestroyed(null);
		emfl = null;
	}

	@Before
	public void setUp() throws Exception {
		addedCustomer = new ArrayList<Customer>();
		addedHealthInsurance = new ArrayList<HealthInsurance>();
		
		healthInsuranceDao = new HealthInsuranceDao();
		customerDao = new CustomerDao();
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<addedCustomer.size(); i++) {
			customerDao.remove(addedCustomer.get(i));
		}
		customerDao.close();
		addedCustomer = null;
		
		for(int i=0; i<addedHealthInsurance.size(); i++) {
			healthInsuranceDao.remove(addedHealthInsurance.get(i));
		}
		healthInsuranceDao.close();
		addedHealthInsurance = null;
	}

	@Test
	public void addNewCustomer() {
		MembershipDao membershipDao = new MembershipDao();
		Membership membership = membershipDao.findMembershipByType("ACTIVE");
		
		HealthInsurance healthInsurance = new HealthInsurance("Java Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", healthInsurance);
		Customer customer = new Customer(personalInformation, membership);
		
		customerDao.persist(customer);
		customerDao.commit();
		assertEquals("Failed to add new cusotmer to database", customerDao.findCustomerById(customer.getId()), customer);
		
		addedCustomer.add(customer);
		addedHealthInsurance.add(healthInsurance);
	}

}
