package edu.colostate.cs.cs414.enigma.handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;
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
	public void testDeleteCustomer() throws AddressException {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		new CustomerHandler().deleteCustomer(Integer.toString(c1.getId()));
		HashMap<String, Object> params = new HashMap<>();
		params.put("id", c1.getId());
		Customer c = (Customer) dao.querySingle("Customer.findById", params);
		assertNull(c);
		
	}
}


