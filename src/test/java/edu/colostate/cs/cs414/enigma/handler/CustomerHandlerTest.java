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
		String fName = "Annetteqweqwepoqweqwpfsdfoqased";
		
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName(fName);
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("999-999-9999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);

		List<Customer> list = new CustomerHandler().getCustomerByKeyword(fName);
		assertTrue(list.size() == 1 && list.get(0).getPersonalInformation().getFirstName().equals(fName));
	}
	
	//****************** Tests for Create Customer *******************/
	

	@Test
	public void testCreateCustomer() throws AddressException {
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("999-999-9999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
		cb.close();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", c1.getId());
		
		Customer c = (Customer) dao.querySingle("Customer.findById", params);
		assertTrue(c.getId() == c1.getId());
	}
	
	/*@Test
	public void testCreateDuplicateCustomer() throws AddressException {
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("999-999-9999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
		cb.createCustomer();
	}*/
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoEmail() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("");
		cb.setPhoneNumber("999-999-9999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	@Test (expected = AddressException.class)
	public void testCreateCustomerWithInvalidEmail() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann");
		cb.setPhoneNumber("999-999-9999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoFirstName() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("999-999-9999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoLastName() throws AddressException {
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("999-999-9999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoPhone() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithWrongPhoneFormat() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("9999999999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoInsurance() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("9999999999");
		cb.setHealthInsurance("");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoStreet() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("9999999999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoCity() throws AddressException {
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("9999999999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoState() throws AddressException {
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("9999999999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoZip() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("9999999999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithIncorrectZipFormat() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("9999999999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("805214566875");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	@Test (expected = IllegalArgumentException.class)
	public void testCreateCustomerWithNoMembership() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("9999999999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
	}
	
	
	@Test
	public void testSearchCustomerEmptyKeyword() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("999-999-9999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);

		cb.setFirstName("asdfasf");
		Customer c2 = cb.createCustomer();
		cb.close();
		
		List<Customer> list = new CustomerHandler().getCustomerByKeyword("");
		assertTrue(list.size() >=2);
	}
	
	@Test
	public void testGetCustomerById() throws AddressException{
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("999-999-9999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		persistedObjects.add(c1);
		cb.close();
		
		Customer c2 = new CustomerHandler().getCustomerById(c1.getId());
		assertTrue(c1.getId() == c2.getId());
	}
	
	// ******************* Tests for update customer ***************************/
	
	private Customer createCustomer() throws AddressException {
		CustomerBuilder cb = new CustomerBuilder();
		cb.setFirstName("Annetteqweqwepoqweqwpfsdfoqased");
		cb.setLastName("Kotian");
		cb.setEmail("ann@email.com");
		cb.setPhoneNumber("999-999-9999");
		cb.setHealthInsurance("Cigna");
		cb.setStreet("720 City park");
		cb.setCity("Fort Collins");
		cb.setState("Colorado");
		cb.setZipcode("80521");
		cb.setMembershipStatus("ACTIVE");
		Customer c1 = cb.createCustomer();
		cb.close();
		return c1;
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoEmail() throws AddressException {
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setEmail("");
		cb.modifyCustomer(c1.getId());
	}
	
	@Test (expected = AddressException.class)
	public void testUpdateCustomerWithInvalideEmail() throws AddressException {
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setEmail("asdf");
		cb.modifyCustomer(c1.getId());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoFirstName() throws AddressException{
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setFirstName("");
		cb.modifyCustomer(c1.getId());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoLastName() throws AddressException{
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setLastName("");
		cb.modifyCustomer(c1.getId());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoPhone() throws AddressException {
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setPhoneNumber("");
		cb.modifyCustomer(c1.getId());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithInvalidPhone() throws AddressException {
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setPhoneNumber("5555555555");
		cb.modifyCustomer(c1.getId());
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoInsurance() throws AddressException{
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setHealthInsurance("");
		cb.modifyCustomer(c1.getId());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoStreet() throws AddressException{
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setStreet("");
		cb.modifyCustomer(c1.getId());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoCity() throws AddressException {
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setCity("");
		cb.modifyCustomer(c1.getId());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoState() throws AddressException {
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setState("");
		cb.modifyCustomer(c1.getId());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoZip() throws AddressException{
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setZipcode("");
		cb.modifyCustomer(c1.getId());
	}

	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithInvalidZip() throws AddressException{
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setZipcode("5555555555");
		cb.modifyCustomer(c1.getId());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testUpdateCustomerWithNoMembership() throws AddressException {
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setMembershipStatus("");
		cb.modifyCustomer(c1.getId());
	}

	@Test
	public void testUpdateCustomer() throws AddressException{
		Customer c1 = createCustomer();
		persistedObjects.add(c1);
		
		CustomerBuilder cb = new CustomerBuilder(c1);
		cb.setFirstName("asdfasfdasdf");
		cb.setLastName("asfasdfas");
		cb.setEmail("asdfa@email.com");
		cb.setPhoneNumber("123-456-7890");
		cb.setHealthInsurance("Java Insurance");
		cb.setStreet("720 ark");
		cb.setCity("St. Paul");
		cb.setState("Minnesota");
		cb.setZipcode("55555");
		cb.setMembershipStatus("INACTIVE");
		cb.modifyCustomer(c1.getId());
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


