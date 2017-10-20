package edu.colostate.cs.cs414.enigma.dao;

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

import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class EntityManagerDaoTest {
	
	private static EntityManagerFactoryListener emfl;
	private EntityManagerDao dao;
	private List<Object> persistedObjects;

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
		persistedObjects = new ArrayList<Object>();
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<persistedObjects.size(); i++) {
			dao.remove(persistedObjects.get(i));
		}
		dao.close();
	}

	@Test
	public void getAllUserLevels() {
		List userLevels = dao.query("UserLevel.findAll", null);
		assertNotNull("Failed to get user levels", userLevels);
	}
	
	@Test
	public void getAdminUserLevel() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("level", "ADMIN");
		UserLevel admin = (UserLevel) dao.querySingle("UserLevel.findLevel", parameters);
		assertNotNull("Failed to get admin user level", admin);
	}
	
	@Test
	public void getTrainerUserLevel() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("level", "TRAINER");
		UserLevel trainer = (UserLevel) dao.querySingle("UserLevel.findLevel", parameters);
		assertNotNull("Failed to get trainer user level", trainer);
	}

	@Test
	public void getManagerUserLevel() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("level", "MANAGER");
		UserLevel manager = (UserLevel) dao.querySingle("UserLevel.findLevel", parameters);
		assertNotNull("Failed to get trainer user level", manager);
	}
	
	@Test
	public void getAllUsers() {
		List users = dao.query("User.findAll", null);
		assertNotNull("Failed to get users", users);
	}
	
	@Test
	public void getAdminUser() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", "admin");
		User admin = (User) dao.querySingle("User.findUser", parameters);
		assertNotNull("Failed to get admin user", admin);
	}
	
	@Test
	public void persistUser() {
		UserLevel userLevel = (UserLevel) dao.querySingle("UserLevel.findAll", null);
		User newUser = new User("johndoe", "password", userLevel);
		dao.persist(newUser);
		persistedObjects.add(newUser);
	}
	
	@Test(expected = PersistenceException.class)
	public void persistRedundantUser() {
		UserLevel userLevel = (UserLevel) dao.querySingle("UserLevel.findAll", null);
		User newUser = new User("johndoe", "password", userLevel);
		dao.persist(newUser);
		persistedObjects.add(newUser);
		
		User redundantUser = new User("johndoe", "password", userLevel);
		dao.persist(redundantUser);
	}
	
	@Test
	public void getAllStates() {
		List states = dao.query("State.findAll", null);
		assertEquals("Failed to get all 50 states from lookup table", states.size(), 50);
	}
	
	@Test
	public void getColorado() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		assertNotNull("Failed to get a specific state", colorado);
	}
	
	@Test
	public void getAllHealthInsurances() {
		List insurances = dao.query("HealthInsurance.findAll", null);
		assertNotNull("Failed to get health insurances", insurances);
	}
	
	@Test
	public void getSpecificHealthInsurance() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("description", "Cigna");
		HealthInsurance insurance = (HealthInsurance) dao.querySingle("HealthInsurance.findDescription", parameters);
		assertNotNull("Failed to get specific health insurance", insurance);
	}
	
	@Test
	public void persisNewHealthInsurance() {
		HealthInsurance newInsurance = new HealthInsurance("Free Insurance");
		dao.persist(newInsurance);
		persistedObjects.add(newInsurance);
	}
	
	@Test
	public void getAllMembershipStatus() {
		List memberships = dao.query("Membership.findAll", null);
		assertNotNull("Failed to get all memebrship statuses", memberships);
	}
	
	@Test
	public void getActiveMembershipStatus() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", "ACTIVE");
		Membership membership = (Membership) dao.querySingle("Membership.findType", parameters);
		assertNotNull("Failed to get ACTIVE membership type", membership);
	}
	
	@Test
	public void getInactiveMembershipStatus() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", "INACTIVE");
		Membership membership = (Membership) dao.querySingle("Membership.findType", parameters);
		assertNotNull("Failed to get INACTIVE membership type", membership);
	}
	
	@Test
	public void persistAddress() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555-5555", colorado);
		dao.persist(newAddress);
		persistedObjects.add(newAddress);
	}
	
	@Test
	public void persistPersonalInformation() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555-5555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", insurance, newAddress);
		dao.persist(personalInformation);
		persistedObjects.add(personalInformation);
		persistedObjects.add(insurance);
	}
	
	@Test
	public void persistCustomer() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", "INACTIVE");
		Membership membership = (Membership) dao.querySingle("Membership.findType", parameters);
		parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555-5555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", insurance, newAddress);
		Customer customer = new Customer(personalInformation, membership);
		dao.persist(customer);
		persistedObjects.add(customer);
		persistedObjects.add(insurance);
	}
	
	@Test
	public void persistTrainer() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555-5555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", insurance, newAddress);
		parameters = new HashMap<String, Object>();
		parameters.put("level", "TRAINER");
		UserLevel userLevel = (UserLevel) dao.querySingle("UserLevel.findLevel", parameters);
		User user = new User("trainer", "password", userLevel);
		Trainer trainer = new Trainer(personalInformation, user);
		dao.persist(trainer);
		persistedObjects.add(trainer);
		persistedObjects.add(insurance);
	}
	
	@Test
	public void persistManager() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555-5555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", insurance, newAddress);
		parameters = new HashMap<String, Object>();
		parameters.put("level", "MANAGER");
		UserLevel userLevel = (UserLevel) dao.querySingle("UserLevel.findLevel", parameters);
		User user = new User("trainer", "password", userLevel);
		Manager managaer = new Manager(personalInformation, user);
		dao.persist(managaer);
		persistedObjects.add(managaer);
		persistedObjects.add(insurance);
	}
}
