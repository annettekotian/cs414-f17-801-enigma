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
	
	/********** Test for Create Manager 
	 * @throws AddressException ************/

	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoEmail() throws AddressException {
		String fName = "Annette";
		String lName = "Kotian";
		//String  email = "ann@email.com";
		String email = "";
		String phone = "998-899-8834";
		String insurance = "Cigna";
		String userName = "annKot";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
		
	}
	
	@Test(expected = AddressException.class)
	public void testCreateManagerWithInvalidEmail() throws AddressException {
		String fName = "Annette";
		String lName = "Kotian";
		//String  email = "ann@email.com";
		String email = "abc";
		String phone = "998-899-8834";
		String insurance = "Cigna";
		String userName = "annKot";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
		
	}

		
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoFirstName() throws AddressException {
		//String fName = "Annette";
		String fName = "";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "998-899-8834";;
		String insurance = "Cigna";
		String userName = "annKot";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoLastName() throws AddressException{
		String fName = "Annette";
		String lName = "";
		String  email = "ann@email.com";
		String phone = "998-899-8884";;
		String insurance = "Cigna";
		String userName = "annKot";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
//		assertNull(m);
			
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoPhone() throws AddressException {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "";
		String insurance = "Cigna";
		String userName = "annKot";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
			
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoHI() throws AddressException{
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "998-899-8884";
		String insurance = "";
		String userName = "annKot";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
			
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoUserName() throws AddressException{
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "998-899-8884";
		String insurance = "Cigna";
		String userName = "";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
			
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoPass() throws AddressException {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "998-899-8883";
		String insurance = "Cigna";
		String userName = "annKot1";
		String userPass = "";
		String confirmPass = "12345678";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
			
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithDifferentPasses()throws AddressException {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String insurance = "Cigna";
		String userName = "annKot1";
		String userPass = "12345678";
		String confirmPass = "12345";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
			
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoStreet() throws AddressException {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String insurance = "Cigna";
		String userName = "annKot1";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
			
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoCity() throws AddressException {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String insurance = "Cigna";
		String userName = "annKot1";
		String userPass = "12345678";
		String confirmPass = "12345";
		String street = "720 City Park";
		String city = "";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m  = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoZip() throws AddressException {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String insurance = "Cigna";
		String userName = "annKot1";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoState() throws AddressException {
		String fName = "Annette";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "99999999";
		String insurance = "Cigna";
		String userName = "annKot1";
		String userPass = "12345678";
		String confirmPass = "12345";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		//assertNull(m);
	}
	
	@Test
	public void testCreateManager() throws AddressException {
		String fName = "AnnetteRachel1";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "999-999-9999";
		String insurance = "Cigna";
		String userName = "annKotRac1";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager persistedM = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		persistedObjects.add(persistedM);
		
		Map<String, Object> managerParams = new HashMap<String, Object>();
		managerParams.put("firstName", fName);
		managerParams.put("lastName", lName);
		Manager m = (Manager) dao.querySingle("Manager.findByName", managerParams);
		assertTrue(m.getId() == persistedM.getId());
		
		
		}
	
	@Test(expected = PersistenceException.class)
	public void testCreateDuplicateManager() throws AddressException {
		String fName = "AnnetteRachel";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "999-999-9988";
		String insurance = "Cigna";
		String userName = "annKotRac";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		persistedObjects.add(m);
		mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
			
		
		
	}
	
	/*********** test for search Manager *****************/ 
	
	public void testSearchManagerByKeyword() throws AddressException {
		
		String fName = "AnnetteRachel123456yeyeteyety";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "999-999-9999";
		String insurance = "Cigna";
		String userName = "annKotRac1";
		String userPass = "12345678";
		String confirmPass = "12345678";
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager persistedM = mh.createManager(email, fName , lName, phone, insurance, userName, userPass, confirmPass, street, city, zip, state);
		persistedObjects.add(persistedM);
		
		List<Manager> list = mh.searchManager(fName);
		assertTrue(list.size() == 1 && list.get(0).getPersonalInformation().getFirstName().equals(fName));
	}
	
	@Test
	public void testSearchManagerEmptyKeyword()throws AddressException {
		
		String fName = "AnnetteRachel123456yeyeteyety";
		String lName = "Kotian";
		String  email = "ann@email.com";
		String phone = "999-999-9999";
		String hiId = "Cigna";
		String userName = "annKotRac1";
		String userPass = "12345678";
		String confirmPass = "12345678";
		
		String street = "720 City Park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		
		ManagerHandler mh = new ManagerHandler();
		Manager m1 = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, confirmPass, street, city, zip, state);
		persistedObjects.add(m1);
		Manager m2 = mh.createManager(email, fName , lName, phone, hiId, userName + "12334", userPass, confirmPass,  street, city, zip, state);
		persistedObjects.add(m2);
		
		
		List<Manager> list = mh.searchManager("");
		assertTrue(list.size() >=2);
	}
	
}
