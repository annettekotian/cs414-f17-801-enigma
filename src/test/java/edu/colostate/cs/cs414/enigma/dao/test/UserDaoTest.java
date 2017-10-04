package edu.colostate.cs.cs414.enigma.dao.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.dao.UserDao;
import edu.colostate.cs.cs414.enigma.dao.UserLevelDao;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.listeners.EntityManagerFactoryListener;

public class UserDaoTest {
	
	private static EntityManagerFactoryListener emfl;
	private static UserLevel userLevel;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emfl = new EntityManagerFactoryListener();
		emfl.contextInitialized(null);
		
		UserLevelDao userLevelDao = new UserLevelDao();
		userLevel = userLevelDao.findUser("MANAGER");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		emfl.contextDestroyed(null);
		emfl = null;
		userLevel = null;
	}

	@Test
	public void persistUser() {
		String userName = "johndoe";
		String password = "password";
		
		User user = new User(userName, password, userLevel);
		
		UserDao userDao = new UserDao();
		userDao.persist(user);
		userDao.commit();
		assertEquals("User was not successfully committed to the database", userDao.findUserById(user.getId()), user);
		userDao.close();
		
		// TODO: User needs to be removed from the database.
	}
}
