package edu.colostate.cs.cs414.enigma.handler;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
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
import edu.colostate.cs.cs414.enigma.entity.Machine;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.exception.MachineException;
import edu.colostate.cs.cs414.enigma.handler.builder.ManagerBuilder;
import edu.colostate.cs.cs414.enigma.handler.builder.TrainerBuilder;


public class ManagerHandlerTest {
	
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
	
	/*********** test for search Manager *****************/ 
	
	public void testSearchManagerByKeyword() throws AddressException {
		String fName = "AnnetteRachel123456yeyeteyety";
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName(fName).setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annkot1").setPassword("12345678").setConfirmPassword("12345678");
		Manager persistedM = mb.createManager();
		persistedObjects.add(persistedM);
		mb.close();
		
		ManagerHandler mh = new ManagerHandler();
		List<Manager> list = mh.searchManager(fName);
		assertTrue(list.size() == 1 && list.get(0).getPersonalInformation().getFirstName().equals(fName));
	}
	
	@Test
	public void testSearchManagerEmptyKeyword()throws AddressException {
		ManagerBuilder mb = new ManagerBuilder();
		mb.setFirstName("Annette123").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb.setUsername("annkot1").setPassword("12345678").setConfirmPassword("12345678");
		Manager persistedM = mb.createManager();
		persistedObjects.add(persistedM);
	
		
		mb.close();
		
		ManagerBuilder mb2 = new ManagerBuilder();
		mb2.setFirstName("Annette22123").setLastName("Kotian").setEmail("ann@email.com").setPhoneNumber("998-899-8884").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("55555");
		mb2.setUsername("annkot123").setPassword("12345678").setConfirmPassword("12345678");
		Manager persistedM2 = mb2.createManager();
		persistedObjects.add(persistedM2);
		mb2.close();
		
		ManagerHandler mh = new ManagerHandler();
		List<Manager> list = mh.searchManager("");
		assertTrue(list.size() >=2);
		mh.close();
	}
	
	
	/*@Test
	public void testAddMachine() throws IOException, MessagingException, MachineException {
		InputStream in = getClass().getResourceAsStream("treadmill.jpg");
		String name = "machine1234";
		String quantity = "4";
		String uploadPath = System.getProperty("user.home");
		Machine m = new ManagerHandler().addMachine(name, in, uploadPath, quantity);
		String location = m.getPictureLocation();
		persistedObjects.add(m);
		File file = new File(uploadPath + "/" + location);
		file.delete();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", m.getId());
		Machine m1 = (Machine) dao.querySingle("Machine.findId", params);
		assertTrue(m1.getName().equals(name) && m1.getQuantity() == Integer.parseInt(quantity) );
				
	}*/
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddMachineWithNoName() throws IOException, MachineException {
		InputStream in = getClass().getResourceAsStream("images/treadmill.jpg");
		String name = "";
		String quantity = "4";
		String uploadPath = System.getProperty("user.home");
		Machine m = new ManagerHandler().createMachine(name, in, uploadPath, quantity);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddMachineWithNoQuantity() throws IOException, MachineException {
		InputStream in = getClass().getResourceAsStream("images/treadmill.jpg");
		String name = "Machine1234";
		String quantity = "";
		String uploadPath = System.getProperty("user.home");
		Machine m = new ManagerHandler().createMachine(name, in, uploadPath, quantity);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddMachineWithNoInputStream() throws IOException, MachineException {
		InputStream in = getClass().getResourceAsStream("images/treadmill1.jpg");
		//InputStream in = null;
		String name = "Machine1234";
		String quantity = "4";
		String uploadPath = System.getProperty("user.home");
		Machine m = new ManagerHandler().createMachine(name, in, uploadPath, quantity);
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddMachineWithNoUploadPath() throws IOException, MachineException {
		InputStream in = getClass().getResourceAsStream("images/treadmill.jpg");
		String name = "Machine1234";
		String quantity = "4";
		String uploadPath = "";
		Machine m = new ManagerHandler().createMachine(name, in, uploadPath, quantity);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddMachineWithWrongImageFormat() throws IOException, MachineException {
		InputStream in = getClass().getResourceAsStream("images/test.txt");
		String name = "Machine1234";
		String quantity = "4";
		String uploadPath = System.getProperty("user.home");
		Machine m = new ManagerHandler().createMachine(name, in, uploadPath, quantity);
	}
	
	
	@Test (expected = PersistenceException.class)
	public void testAddDuplicateMachine() throws IOException, MachineException {
		String name = "testMachine1234567789";
		int quantity = 4;
		String pictureLocation = "/" + name + ".png";
		Machine m = new Machine(name, pictureLocation, quantity);
		dao.persist(m);
		persistedObjects.add(m);
		
		String name2 = "testMachine1234567789";
		String pictureLocation2 = "/" + name2 + ".png";
		Machine m2 = new Machine(name, pictureLocation2, quantity);
		dao.persist(m2);
		persistedObjects.add(m2);
		
	}	
	
	@Test
	public void testRemoveMachine() throws MachineException {
		String name = "testMachine1234567789";
		int quantity = 4;
		String pictureLocation = "/" + name + ".png";
		Machine m = new Machine(name, pictureLocation, quantity);
		int id = m.getId();
		dao.persist(m);
		persistedObjects.add(m);
		dao.remove(m);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		Machine m2 = (Machine)dao.querySingle("Machine.findId", params);
		
		assertNull(m2);
	}
}
