package edu.colostate.cs.cs414.enigma.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class ManagerDaoTest {

	private static EntityManagerFactoryListener emfl;
	private List<Manager> addedManager;
	private List<HealthInsurance> addedHealthInsurance;
	private HealthInsuranceDao healthInsuranceDao;
	private ManagerDao managerDao;
	
	private UserLevel userLevel;

	
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
		addedManager = new ArrayList<Manager>();
		addedHealthInsurance = new ArrayList<HealthInsurance>();
		
		healthInsuranceDao = new HealthInsuranceDao();
		managerDao = new ManagerDao();
		
		UserLevelDao userLevelDao = new UserLevelDao();
		userLevel = userLevelDao.findUserLevelByDescription("MANAGER");
		userLevelDao.close();
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<addedManager.size(); i++) {
			managerDao.remove(addedManager.get(i));
		}
		managerDao.close();
		addedManager = null;
		
		for(int i=0; i<addedHealthInsurance.size(); i++) {
			healthInsuranceDao.remove(addedHealthInsurance.get(i));
		}
		healthInsuranceDao.close();
		addedHealthInsurance = null;
	}

	@Test
	public void addNewManager() {
		User user = new User("manager", "password", userLevel);
		HealthInsurance healthInsurance = new HealthInsurance("Java Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", healthInsurance);
		Manager manager = new Manager(personalInformation, user);
		
		managerDao.persist(manager);
		managerDao.commit();
		assertEquals("New manager was not successully entered", managerDao.findManagerById(manager.getId()), manager);
		
		addedManager.add(manager);
		addedHealthInsurance.add(healthInsurance);
	}

}
