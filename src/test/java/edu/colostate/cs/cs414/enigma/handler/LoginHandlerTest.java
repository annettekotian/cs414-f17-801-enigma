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

public class LoginHandlerTest {

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

	@Test
	public void testAuthenicateWithWrongPass() {
		LoginHandler lh = new LoginHandler();
		boolean isValid = lh.authenticate("admin", "pass123");
		lh.close();
		assertFalse(isValid);
	}
	
	@Test
	public void testAuthenicateWithCorrectPass() {
		LoginHandler lh = new LoginHandler();
		boolean isValid = lh.authenticate("admin", "password");
		lh.close();
		assertTrue(isValid);
	}
	
	@Test
	public void testAuthenicateWithWrongUsername() {
		LoginHandler lh = new LoginHandler();
		boolean isValid = lh.authenticate("admin123", "password");
		lh.close();
		assertFalse(isValid);
	}
	
	@Test
	public void testGetUserLevel() {
		LoginHandler lh = new LoginHandler();
		String level = lh.getUserLevel("admin");
		lh.close();
		assertEquals("ADMIN", level);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetUserLevelForIllegalArgumentException() {
		LoginHandler lh = new LoginHandler();
		String level = lh.getUserLevel("admin123");
		assertEquals("ADMIN", level);
	}
}
