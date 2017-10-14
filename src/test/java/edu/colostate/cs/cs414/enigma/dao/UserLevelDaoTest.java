package edu.colostate.cs.cs414.enigma.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.listeners.EntityManagerFactoryListener;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;

public class UserLevelDaoTest {
	
	private static EntityManagerFactoryListener emfl;
	private List<UserLevel> addedUserLevels;
	private UserLevelDao userLevelDao;

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
		addedUserLevels = new ArrayList<UserLevel>();
		userLevelDao = new UserLevelDao();
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<addedUserLevels.size(); i++) {
			userLevelDao.remove(addedUserLevels.get(i));
		}
		userLevelDao.close();
		addedUserLevels = null;
	}

	@Test
	public void persistUserLevel() {
		UserLevel newUserLevel = new UserLevel("SUPERUSER");
		userLevelDao.persist(newUserLevel);
		userLevelDao.commit();
		assertEquals("UserLevel was not committed to the database", userLevelDao.findUserLevelById(newUserLevel.getId()), newUserLevel);
		addedUserLevels.add(newUserLevel);
	}
	
	@Test
	public void getAllUserLevels() {
		// Default user levels should be predefined
		List<UserLevel> userLevels = userLevelDao.getUserLevels();
		assertNotEquals("Failed to retrieve default user levels", 0, userLevels.size());
	}
	
	@Test
	public void removeUserLevel() {
		UserLevel newUserLevel = new UserLevel("SUPERUSER");
		userLevelDao.persist(newUserLevel);
		userLevelDao.commit();
		userLevelDao.remove(newUserLevel);
		userLevelDao.commit();
		assertNull("Failed to remove user level from the databased", userLevelDao.findUserLevelById(newUserLevel.getId()));
	}
	
	@Test
	public void verifyManagerUserLevel() {
		assertNotNull("Failed to retrieve default manager user level", userLevelDao.findUserLevelByDescription("MANAGER"));
	}
	
	@Test
	public void verifyTrainerUserLevel() {
		assertNotNull("Failed to retrieve default trainer user level", userLevelDao.findUserLevelByDescription("TRAINER"));
	}
	
	@Test
	public void verifyAdminUserLevel() {
		assertNotNull("Failed to retrieve default admin user level", userLevelDao.findUserLevelByDescription("ADMIN"));
	}
	
	@Test
	public void verifyCustomerUserLevel() {
		assertNotNull("Failed to retrieve default admin user level", userLevelDao.findUserLevelByDescription("CUSTOMER"));
	}
	
	@Test
	public void modifyUserLevel() {
		
		// Change the manager user level description
		UserLevel manager = userLevelDao.findUserLevelByDescription("MANAGER");
		manager.setDescription("BOSS");
		userLevelDao.commit();
		assertEquals("Failed to modify a UserLevel", userLevelDao.findUserLevelById(manager.getId()).getDescription(), "BOSS");
		
		// Reset the description
		manager.setDescription("MANAGER");
		userLevelDao.commit();
	}
}
