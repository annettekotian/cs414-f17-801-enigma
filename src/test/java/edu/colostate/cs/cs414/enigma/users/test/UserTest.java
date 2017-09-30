package edu.colostate.cs.cs414.enigma.users.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.listeners.EntityManagerFactoryListener;
import edu.colostate.cs.cs414.enigma.users.User;
import edu.colostate.cs.cs414.enigma.users.UserLevel;
import edu.colostate.cs.cs414.enigma.users.UserPK;

public class UserTest {
	
	private static EntityManagerFactoryListener emfl;

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

	@Test
	public void verifyAdminUser() {
		User admin = User.findUser("admin");
		assertNotNull("Default admin user was not found", admin);
		assertEquals("Admin user level not set to 'ADMIN'", "ADMIN", admin.getUserLevel().getDescription());
	}
	
	@Test
	public void commitModifyRemoveUser() {
		UserLevel level = UserLevel.findUser("MANAGER");
		
		UserPK key = new UserPK();
		key.setUserLevelId(level.getId());
		
		User manager = new User();
		manager.setId(key);
		manager.setUsername("java_runs_on");
		manager.setPassword("toasters");
		
		User.commitUser(manager);
		assertNotNull("User was not committed to database", User.findUser(manager.getUsername()));
		
		manager = User.findUser(manager.getUsername());
		assertNotNull("Unabled to find user in database", manager);
		
		User.modifyPassword(manager.getId(), "password");
		manager = User.findUser(manager.getUsername());
		assertNotEquals("User password was not successfully changed", "toasters", manager.getPassword());
		
		manager = User.findUser(manager.getUsername());
		User.removeUser(manager);
		assertNull("User was not removed from database", User.findUser(manager.getUsername()));
	}
}
