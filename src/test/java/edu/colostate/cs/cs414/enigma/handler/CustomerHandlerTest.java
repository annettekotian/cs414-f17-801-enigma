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
	public void testSearchCustomerByKeyword() throws AddressException {

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

		CustomerHandler ch = new CustomerHandler();
		Customer c1 = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state,
				membershipStatus);
		persistedObjects.add(c1);

		List<Customer> list = new CustomerHandler().getCustomerByKeyword(fName);
		assertTrue(list.size() == 1 && list.get(0).getPersonalInformation().getFirstName().equals(fName));
	}
	
	//****************** Tests for Create Customer *******************/
	

	@Test
	public void testCreateCustomer() throws AddressException {
		
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		Customer persistedC  = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		persistedObjects.add(persistedC);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", persistedC.getId());
		
		Customer c = (Customer) dao.querySingle("Customer.findById", params);
		assertTrue(c.getId() == persistedC.getId());
	}
	
	@Test
	public void testCreateDuplicateCustomer() throws AddressException {
		
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		Customer persistedC1  = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		persistedObjects.add(persistedC1);
		Customer persistedC2  = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		persistedObjects.add(persistedC2);
		assertTrue(persistedC1.getId() != persistedC2.getId());
		
		
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoEmail() throws AddressException{
		
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
		
		CustomerHandler ch = new CustomerHandler();
		ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
	}
	
	@Test (expected = AddressException.class)
	public void testCreateCustomerWithInvalidEmail() throws AddressException{
		
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoFirstName() throws AddressException{
		
		String fName = "";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoLastName() throws AddressException {
		
		String fName = "Annette";
		String lName = "";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoPhone() throws AddressException{
		
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoInsurance() throws AddressException{
		
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoStreet() throws AddressException{
		
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoCity() throws AddressException {
		
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoState() throws AddressException {
		
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "";
		String zip = "80521";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		Customer persistedC1  = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoZip() throws AddressException{
		
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "";
		String membershipStatus = "ACTIVE";
		
		CustomerHandler ch = new CustomerHandler();
		ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
	}

	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoMembership() throws AddressException{
		
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "";
		
		CustomerHandler ch = new CustomerHandler();
		Customer persistedC1  = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
	}
	
	
	@Test
	public void testSearchCustomerEmptyKeyword() throws AddressException{
		
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

		CustomerHandler ch = new CustomerHandler();
		Customer c1 = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state,
				membershipStatus);
		persistedObjects.add(c1);
		Customer c2 = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state,
				membershipStatus);
		persistedObjects.add(c2);
		
		List<Customer> list = new CustomerHandler().getCustomerByKeyword("");
		assertTrue(list.size() >=2);
	}
	
	@Test
	public void testGetCustomerById() throws AddressException{
		
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

		CustomerHandler ch= new CustomerHandler();
		Customer c1 = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state,
				membershipStatus);
		persistedObjects.add(c1);
		
		Customer c2 = ch.getCustomerById(c1.getId());
		assertTrue(c1.getId() == c2.getId());
	}
	
	// ******************* Tests for update customer ***************************/
	
	private Customer createCustomer() throws AddressException {
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
		
		CustomerHandler ch= new CustomerHandler();
		return ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoEmail() throws AddressException {
		
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
	public void testUpdateCustomerWithNoFirstName() throws AddressException{
		
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
	public void testUpdateCustomerWithNoLastName() throws AddressException{
		
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
	public void testUpdateCustomerWithNoPhone() throws AddressException {
		
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
	public void testUpdateCustomerWithNoInsurance() throws AddressException{
		
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
	public void testUpdateCustomerWithNoStreet() throws AddressException{
		
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
	public void testUpdateCustomerWithNoCity() throws AddressException {
		
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
	public void testUpdateCustomerWithNoState() throws AddressException {
		
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
	public void testUpdateCustomerWithNoZip() throws AddressException{
		
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
	public void testUpdateCustomerWithNoMembership() throws AddressException {
		
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
	public void testUpdateCustomer() throws AddressException{
		
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
	
	
	@Test 
	public void testDeleteCustomer() throws AddressException {
		
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		new CustomerHandler().removeCustomer(Integer.toString(c1.getId()));
		HashMap<String, Object> params = new HashMap<>();
		params.put("id", c1.getId());
		Customer c = (Customer) dao.querySingle("Customer.findById", params);
		assertNull(c);
		
	}
}


