package edu.colostate.cs.cs414.enigma.handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Exercise;
import edu.colostate.cs.cs414.enigma.entity.Workout;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseDurationException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseSetException;
import edu.colostate.cs.cs414.enigma.handler.builder.CustomerBuilder;

public class CustomerHandlerTest {

	private EntityManagerDao dao;
	private List<Object> persistedObjects = new ArrayList<Object>();	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		new EntityManagerDao().close();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		new EntityManagerDao().shutdown();
	}

	@Before
	public void setUp() throws Exception {
		dao = new EntityManagerDao();
		
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<persistedObjects.size(); i++) {
			dao.remove(persistedObjects.get(i));
		}
		dao.close();
	}
	
	@Test
	public void testSearchCustomerByKeyword() throws AddressException {
		String fName = "Annetteqweqwepoqweqwpfjjjsdfoqased";
		
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName(fName).setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("999-999-9999").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		cb.setUsername("annkot123739").setPassword("password").setConfirmPassword("password");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);

		List<Customer> list = new CustomerHandler().getCustomerByKeyword(fName);
		assertTrue(list.size() == 1 && list.get(0).getPersonalInformation().getFirstName().equals(fName));
	}
	
	private Customer createCustomer() throws AddressException {
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("999-999-9999").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		cb.setUsername("annkot123739").setPassword("password").setConfirmPassword("password");
		Customer c1 = cb.createCustomer();
		cb.close();
		return c1;
	}
	
	
	@Test
	public void testSearchCustomerEmptyKeyword() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("999-999-9999").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		cb.setUsername("annkot123739").setPassword("password").setConfirmPassword("password");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
		
		cb.setUsername("test123423");
		cb.setFirstName("asdfasf");
		Customer c2 = cb.createCustomer();
		persistedObjects.add(c2);
		cb.close();
		
		List<Customer> list = new CustomerHandler().getCustomerByKeyword("");
		assertTrue(list.size() >=2);
	}
	
	@Test
	public void testGetCustomerById() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("999-999-9999").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		cb.setUsername("annkot123739").setPassword("password").setConfirmPassword("password");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
		cb.close();
		
		Customer c2 = new CustomerHandler().getCustomerById(c1.getId());
		assertTrue(c1.getId() == c2.getId());
	}
	
	@Test 
	public void testDeleteCustomer() throws AddressException {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		new CustomerHandler().deleteCustomer(Integer.toString(c1.getId()));
		HashMap<String, Object> params = new HashMap<>();
		params.put("id", c1.getId());
		Customer c = (Customer) dao.querySingle("Customer.findById", params);
		assertNull(c);
		
	}
	
	@Test
	public void addWorkoutFeedback() throws AddressException, PersistenceException, ExerciseDurationException, ExerciseSetException, ExerciseException
	{
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		TrainerHandler th = new TrainerHandler();
		String name = "testWorkout123456";
		Exercise ex1 = th.createExercise("ex1122346", 0, 0, 30, 30, new ArrayList<Integer>());
		Exercise ex2 = th.createExercise("ex112234567", 0, 0, 30, 30, new ArrayList<Integer>());
		String[] exList = {ex2.getName(), ex1.getName()}; 
		persistedObjects.add(ex1);
		persistedObjects.add(ex2);
		Workout w = th.createWorkout(name, exList);
		persistedObjects.add(w);
		c1.addWorkout(w);
		dao.update(c1);
		
		CustomerHandler ch = new CustomerHandler();
		ch.addFeedback(c1.getId(), w.getId(), "Greatest workout ever");
		w = ch.getWorkoutById(w.getId());
		assertEquals("Failed to added new feedback", w.getFeedback().size(), 1);
		
		w.removeAllFeedback();
		dao.update(w);
		
		th.close();
		ch.close();
	}
}


