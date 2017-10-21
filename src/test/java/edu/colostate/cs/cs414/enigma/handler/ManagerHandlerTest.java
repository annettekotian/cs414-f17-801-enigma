package edu.colostate.cs.cs414.enigma.handler;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Manager;

public class ManagerHandlerTest {
	
	private EntityManagerDao dao;
	private Object persistedObject;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);
		
	}
	
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);	
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);
			
	}
	
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);
			
	}
	
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);
			
	}
	
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);
			
	}
	
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);
			
	}
	
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);
			
	}
	
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);
	}
	
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);
	}
	
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		assertFalse(isSuccessful);
	}
	
	public void testCreateManager() {
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
		boolean isSuccessful = mh.createManager(email, fName , lName, phone, hiId, userName, userPass, street, city, zip, state);
		
		EntityManagerDao dao = new EntityManagerDao();
		Map<String, Object> managerParams = new HashMap<String, Object>();
		managerParams.put("name", fName);
		Manager m = (Manager) dao.querySingle("Manager.findByName", managerParams);
		System.out.println(m.getPersonalInformation().getFirstName());
		
		assertTrue(m.getPersonalInformation().getFirstName().equals(fName) && isSuccessful == true);
	}
	
	

}
