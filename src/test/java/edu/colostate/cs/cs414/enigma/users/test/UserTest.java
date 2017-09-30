package edu.colostate.cs.cs414.enigma.users.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.listeners.EntityManagerFactoryListener;
import edu.colostate.cs.cs414.enigma.users.User;

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

}
