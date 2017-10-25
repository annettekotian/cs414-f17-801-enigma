package edu.colostate.cs.cs414.enigma.handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class LoginHandlerTest {

	private EntityManagerDao dao;
	private static EntityManagerFactoryListener emfl;
	private List<Object> persistedObjects = new ArrayList<Object>();	
	
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
		
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<persistedObjects.size(); i++) {
			dao.remove(persistedObjects.get(i));
		}
		dao.close();
	}

	@Test
	public void testAuthenicateWithWrongPass() {
		
		boolean isValid = LoginHandler.authenticate("admin", "pass123");
		assertFalse(isValid);
	}
	
	@Test
	public void testAuthenicateWithCorrectPass() {
		
		boolean isValid = LoginHandler.authenticate("admin", "password");
		assertTrue(isValid);
	}
	
	@Test
	public void testAuthenicateWithWrongUsername() {
		
		boolean isValid = LoginHandler.authenticate("admin123", "password");
		assertFalse(isValid);
	}
	
	@Test
	public void testGetUserLevel() {
		
		String level = LoginHandler.getUserLevel("admin");
		assertEquals("ADMIN", level);
	}
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetUserLevelForIllegalArgumentException() {
		
		String level = LoginHandler.getUserLevel("admin123");
		assertEquals("ADMIN", level);
	}
	
	


}
