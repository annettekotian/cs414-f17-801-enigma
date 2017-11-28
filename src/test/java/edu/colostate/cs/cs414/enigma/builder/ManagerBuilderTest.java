package edu.colostate.cs.cs414.enigma.builder;

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
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.handler.builder.ManagerBuilder;

public class ManagerBuilderTest {

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
	
	/********** Test for Create Manager 
	 * @throws AddressException ************/

	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoEmail() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("").setPhoneNumber("998-899-8834").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annKot").setPassword("12345678").setConfirmPassword("12345678");
		mb.createManager();
	}
	
	
	@Test(expected = AddressException.class)
	public void testCreateManagerWithInvalidEmail() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("abc").setPhoneNumber("998-899-8834").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annKot").setPassword("12345678").setConfirmPassword("12345678");
		mb.createManager();
	}

		
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoFirstName() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8834").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annKot").setPassword("12345678").setConfirmPassword("12345678");
		
		mb.createManager();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoLastName() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("").setEmail("ann@email.com").setPhoneNumber("998-899-8834").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annKot").setPassword("12345678").setConfirmPassword("12345678");
		
		mb.createManager();			
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoPhone() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annKot").setPassword("12345678").setConfirmPassword("12345678");
		
		mb.createManager();
	}
	

	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithWrongPhoneformat() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("88888888").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annKot").setPassword("12345678").setConfirmPassword("12345678");
		
		mb.createManager();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoHI() throws AddressException{
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annKot").setPassword("12345678").setConfirmPassword("12345678");
		
		mb.createManager();			
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoUserName() throws AddressException{
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("").setPassword("12345678").setConfirmPassword("12345678");
		
		mb.createManager();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoPass() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annkot1").setPassword("").setConfirmPassword("");
		mb.createManager();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithDifferentPasses()throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annkot1").setPassword("12345678").setConfirmPassword("123456778");
		mb.createManager();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoStreet() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annkot1").setPassword("12345678").setConfirmPassword("12345678");
		mb.createManager();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoCity() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("").setState("Colorado").setZipcode("55555");
		mb.setUsername("annkot1").setPassword("12345678").setConfirmPassword("12345678");
		mb.createManager();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoZip() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("");
		mb.setUsername("annkot1").setPassword("12345678").setConfirmPassword("12345678");
		mb.createManager();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithIncorrectZipFormat() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("5555885");
		mb.setUsername("annkot1").setPassword("12345678").setConfirmPassword("12345678");
		mb.createManager();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateManagerWithNoState() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("").setZipcode("55555");
		mb.setUsername("annkot1").setPassword("12345678").setConfirmPassword("12345678");
		mb.createManager();
	}
	
	@Test
	public void testCreateManager() throws AddressException {
		String fName = "Annette";
		String lName = "Kotian";
		
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName(fName).setLastName(lName).setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annkot1").setPassword("12345678").setConfirmPassword("12345678");
		
		Manager persistedM = mb.createManager();
		persistedObjects.add(persistedM);
		
		Map<String, Object> managerParams = new HashMap<String, Object>();
		managerParams.put("firstName", fName);
		managerParams.put("lastName", lName);
		Manager m = (Manager) dao.querySingle("Manager.findByName", managerParams);
		assertTrue(m.getId() == persistedM.getId());
	}
	
	@Test(expected = PersistenceException.class)
	public void testCreateDuplicateManager() throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annkot1").setPassword("12345678").setConfirmPassword("12345678");
		Manager persistedM = mb.createManager();
		persistedObjects.add(persistedM);
		mb.createManager();
	}

}
