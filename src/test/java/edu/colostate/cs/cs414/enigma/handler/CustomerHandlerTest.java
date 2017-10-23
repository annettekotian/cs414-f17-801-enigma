package edu.colostate.cs.cs414.enigma.handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class CustomerHandlerTest {

	private EntityManagerDao dao;
	private static EntityManagerFactoryListener emfl;
	private List<Object> persistedObjects = new ArrayList<Object>();	
	
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
	public void testSearchManagerByKeyword() {

		String fName = "Annetteqweqwepoqweqwpfsdfoqased";
		String lName = "Kotian";
		String email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";

		ManagerHandler mh = new ManagerHandler();
		Customer c1 = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state,
				membershipStatus);
		persistedObjects.add(c1);

		List<Customer> list = new CustomerHandler().getCustomerByKeyword(fName);
		assertTrue(list.size() == 1 && list.get(0).getPersonalInformation().getFirstName().equals(fName));
	}
	
	@Test
	public void testSearchManagerEmptyKeyword() {
		
		String fName = "Annetteqweqwepoqweqwpfsdfoqased";
		String lName = "Kotian";
		String email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";

		ManagerHandler mh = new ManagerHandler();
		Customer c1 = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state,
				membershipStatus);
		persistedObjects.add(c1);
		Customer c2 = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state,
				membershipStatus);
		persistedObjects.add(c2);
		
		List<Customer> list = new CustomerHandler().getCustomerByKeyword("");
		assertTrue(list.size() >=2);
	}

}
