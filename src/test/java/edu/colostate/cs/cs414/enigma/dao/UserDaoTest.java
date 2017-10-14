package edu.colostate.cs.cs414.enigma.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.dao.UserDao;
import edu.colostate.cs.cs414.enigma.dao.UserLevelDao;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class UserDaoTest {
	
	private static EntityManagerFactoryListener emfl;
	private List<User> addedUsers;
	private UserDao userDao;
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
		addedUsers = new ArrayList<User>();
		userDao = new UserDao();
		userLevel = new UserLevelDao().findUserLevelByDescription("MANAGER");
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<addedUsers.size(); i++) {
			userDao.remove(addedUsers.get(i));
		}
		userDao.close();
		addedUsers = null;
	}

	@Test
	public void persistUser() {
		String userName = "johndoe";
		String password = "password";
		User user = new User(userName, password, userLevel);
		
		userDao.persist(user);
		userDao.commit();
		assertEquals("User was not successfully committed to the database", userDao.findUserById(user.getId()), user);
		addedUsers.add(user);
	}
	
	@Test(expected = PersistenceException.class)
	public void presistRedundantUser() {
		String userName = "johndoe";
		String password = "password";
		User user = new User(userName, password, userLevel);
		
		userDao.persist(user);
		userDao.commit();
		addedUsers.add(user);
		
		User redundantUser = new User(userName, password, userLevel);
		userDao.persist(redundantUser);
	}
	
	@Test
	public void verifyAdminAccount() {
		User admin = userDao.findUserByUserName("admin");
		assertNotNull("Admin account does not exist", admin);
	}
	
	@Test(expected = PersistenceException.class)
	public void addDuplicateUser() {
		User admin = new User("admin", "password", userLevel);
		userDao.persist(admin);
		userDao.commit();
	}
}
