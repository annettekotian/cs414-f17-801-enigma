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
	public void testSearchCustomerByKeyword() {

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
	public void testSearchCustomerEmptyKeyword() {
		
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
	
	@Test
	public void testGetCustomerById() {
		
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
		
		Customer c2 = new CustomerHandler().getCustomerById(c1.getId());
		assertTrue(c1.getId() == c2.getId());
	}
	
	// ******************* Tests for update customer ***************************/
	
	private Customer createCustomer() {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "annette@gmail.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		ManagerHandler mh = new ManagerHandler();
		return mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoEmail() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(c2);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoFirstName() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "";
		String lName = "Kotian";
		String  email = "annette@gmail.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(c2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoLastName() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "Annette";
		String lName = "";
		String  email = "annette@gmail.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(c2);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoPhone() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "annette@gmail.com";
		String phone = "";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(c2);
		
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoInsurance() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "annette@gmail.com";
		String phone = "98765465";
		String insurance = "";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(c2);
		
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoStreet() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "annette@gmail.com";
		String phone = "98765465";
		String insurance = "Cigna";
		String street = "";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(c2);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoCity() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "annette@gmail.com";
		String phone = "98765465";
		String insurance = "Cigna";
		String street = "720 City Park";
		String city = "";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(c2);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoState() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "annette@gmail.com";
		String phone = "98765465";
		String insurance = "Cigna";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(c2);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoZip() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "annette@gmail.com";
		String phone = "98765465";
		String insurance = "Cigna";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "";
		String membershipStatus = "ACTIVE";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(c2);;
	}

	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoMembership() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "annette@gmail.com";
		String phone = "98765465";
		String insurance = "Cigna";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(c2);
		
	}

	@Test
	public void testUpdateCustomer() {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		String fName = "Annette123";
		String lName = "Kotian123";
		String  email = "annett123e@gmail.com";
		String phone = "98765465123";
		String insurance = "Blue Cross Blue Shield";
		String street = "720 City Park123";
		String city = "Fort Collins123";
		String state = "California";
		String zip = "80522";
		String membershipStatus = "INACTIVE";
		Customer c2 = new CustomerHandler().updateCustomer(c1.getId(), email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertTrue(c2.getId() == c1.getId()&& c2.getPersonalInformation().getFirstName().equals(fName) && c2.getPersonalInformation().getLastName().equals(lName)
				&& c2.getPersonalInformation().getEmail().equals(email) && c2.getMembership().getType().equals(membershipStatus) 
				&& c2.getPersonalInformation().getAddress().getCity().equals(city) && c2.getPersonalInformation().getAddress().getState().getState().equals(state)
				&& c2.getPersonalInformation().getAddress().getStreet().equals(street) && c2.getPersonalInformation().getAddress().getZipcode().equals(zip)
				&& c2.getPersonalInformation().getHealthInsurance().getName().equals(insurance));
		
	}
}
