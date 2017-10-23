package edu.colostate.cs.cs414.enigma.handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;


public class ManagerHandlerTest {
	
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
	
	/********** Test for Create Manager ************/

	@Test
	public void testCreateManagerWithNoEmail() {
		String fName = "Annette";
		String lName = "Kotian";
		//String  email = "ann@email.com";
		String email = "";
		String phone = "99889988834";
		String hiId = "2";
		String userName = "annKot";
		String userPass = "123456";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
		
	}

		
	@Test
	public void testCreateManagerWithNoFirstName() {
		//String fName = "Annette";
		String fName = "";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String hiId = "2";
		String userName = "annKot";
		String userPass = "123456";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
	}
	
	public void testCreateManagerWithNoLastName() {
		String fName = "Annette";
		String lName = "";
		String  email = "ann@email.com";
		String phone = "99889988834";
		String hiId = "2";
		String userName = "annKot";
		String userPass = "123456";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
			
	}
	
	@Test
	public void testCreateManagerWithNoPhone() {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "";
		String hiId = "2";
		String userName = "annKot";
		String userPass = "123456";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
			
	}
	
	@Test
	public void testCreateManagerWithNoHI() {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "";
		String userName = "annKot";
		String userPass = "123456";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
			
	}
	
	@Test
	public void testCreateManagerWithNoUserName() {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "2";
		String userName = "";
		String userPass = "123456";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
			
	}
	
	@Test
	public void testCreateManagerWithNoPass() {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "2";
		String userName = "annKot1";
		String userPass = "";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
			
	}
	
	@Test
	public void testCreateManagerWithNoStreet() {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "2";
		String userName = "annKot1";
		String userPass = "123456";
		String street = "";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
			
	}
	
	@Test
	public void testCreateManagerWithNoCity() {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "2";
		String userName = "annKot1";
		String userPass = "123456";
		String street = "720 City Park";
		String city = "";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
	}
	
	@Test
	public void testCreateManagerWithNoZip() {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "2";
		String userName = "annKot1";
		String userPass = "123456";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
	}
	
	@Test
	public void testCreateManagerWithNoState() {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "2";
		String userName = "annKot1";
		String userPass = "123456";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertNull(m);
	}
	
	@Test
	public void testCreateManager() {
		String fName = "AnnetteRachel1";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "2";
		String userName = "annKotRac1";
		String userPass = "123456";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager persistedM = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		persistedObjects.add(persistedM);
		
		Map<String, Object> managerParams = new HashMap<String, Object>();
		managerParams.put("firstName", fName);
		managerParams.put("lastName", lName);
		Manager m = (Manager) dao.querySingle("Manager.findByName", managerParams);
		assertTrue(m.getId() == persistedM.getId());
		
		
		}
	
	@Test(expected = PersistenceException.class)
	public void testCreateDuplicateManager() {
		String fName = "AnnetteRachel";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "2";
		String userName = "annKotRac";
		String userPass = "123456";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		persistedObjects.add(m);
		mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
			
		
		
	}
	
	/************************* Test for create customer *******************/
	
	@Test
	public void testCreateCustomer() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		persistedObjects.add(persistedC);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", persistedC.getId());
		
		Customer c = (Customer) dao.querySingle("Customer.findById", params);
		assertTrue(c.getId() == persistedC.getId());
	}
	
	@Test
	public void testCreateDuplicateCustomer() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		persistedObjects.add(persistedC1);
		Customer persistedC2  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		persistedObjects.add(persistedC2);
		assertTrue(persistedC1.getId() != persistedC2.getId());
		
		
	}
	
	
	@Test
	public void testCreateCustomerWithNoEmail() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(persistedC1);
		
	}
	
	@Test
	public void testCreateCustomerWithNoFirstName() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(persistedC1);
	}
	
	@Test
	public void testCreateCustomerWithNoLastName() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(persistedC1);
		
	}
	
	@Test
	public void testCreateCustomerWithNoPhone() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(persistedC1);
		
	}
	
	@Test
	public void testCreateCustomerWithNoInsurance() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(persistedC1);
		
	}
	
	@Test
	public void testCreateCustomerWithNoStreet() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(persistedC1);
		
	}
	
	@Test
	public void testCreateCustomerWithNoCity() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(persistedC1);
		
	}
	
	@Test
	public void testCreateCustomerWithNoState() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(persistedC1);
		
	}
	
	@Test
	public void testCreateCustomerWithNoZip() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(persistedC1);
	}

	@Test
	public void testCreateCustomerWithNoMembership() {
		
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
		
		ManagerHandler mh = new ManagerHandler();
		Customer persistedC1  = mh.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state, membershipStatus);
		assertNull(persistedC1);
		
	}

	/********* Test for new trainer **************/ 
	@Test
	public void presistNewTrainer() {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe@email.com";
		String phone = "5555555555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String userName = "johndoe";
		String password = "password";
		ManagerHandler mh = new ManagerHandler();
		Trainer newTrainer = mh.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password);
		persistedObjects.add(newTrainer);
	}
	
	@Test(expected = PersistenceException.class)
	public void presistNewTrainerDuplicateUsername() {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe@email.com";
		String phone = "5555555555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String userName = "johndoe";
		String password = "password";
		ManagerHandler mh = new ManagerHandler();
		Trainer newTrainer = mh.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password);
		persistedObjects.add(newTrainer);
		
		firstName = "John";
		lastName = "Doe";
		email = "johndoe@email.com";
		phone = "5555555555";
		insurance = "Cigna";
		street = "720 City park";
		city = "Fort Collins";
		state = "Colorado";
		zip = "80521";
		userName = "johndoe";
		password = "password";
		newTrainer = mh.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password);
	}
	
	/*********** test for search Manager *****************/ 
	
	public void testSearchManagerByKeyword() {
		
		String fName = "AnnetteRachel123456yeyeteyety";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "2";
		String userName = "annKotRac1";
		String userPass = "123456";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager persistedM = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		persistedObjects.add(persistedM);
		
		List<Manager> list = mh.searchManager(fName);
		assertTrue(list.size() == 1 && list.get(0).getPersonalInformation().getFirstName().equals(fName));
	}
	
	@Test
	public void testSearchManagerEmptyKeyword() {
		
		String fName = "AnnetteRachel123456yeyeteyety";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String hiId = "2";
		String userName = "annKotRac1";
		String userPass = "123456";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m1 = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		persistedObjects.add(m1);
		Manager m2 = mh.createManager(email, fName , lName, phone, hiId, userName + "12334", userPass, street, city, zip, state);
		persistedObjects.add(m2);
		
		
		List<Manager> list = mh.searchManager("");
		assertTrue(list.size() >=2);
	}
	
}
