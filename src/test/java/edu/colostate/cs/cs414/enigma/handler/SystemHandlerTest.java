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
import edu.colostate.cs.cs414.enigma.entity.Machine;
import edu.colostate.cs.cs414.enigma.entity.exception.MachineException;

public class SystemHandlerTest {

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
	public void testSearchMachineByKeyword() throws MachineException {
		String name = "testMachine1234567789";
		int quantity = 4;
		String pictureLocation = "/" + name + ".png";
		Machine m = new Machine(name, pictureLocation, quantity);
		dao.persist(m);
		persistedObjects.add(m);
		SystemHandler s = new SystemHandler();
		List<Machine> list = s.searchByKeyword(name);
		assertTrue(list.size() == 1 && list.get(0).getName().equals(name));
	}
	
	
	@Test
	public void testSearchMachineByEmptyKeyword() throws MachineException {
		String name = "testMachine1234567789";
		int quantity = 4;
		String pictureLocation = "/" + name + ".png";
		Machine m = new Machine(name, pictureLocation, quantity);
		dao.persist(m);
		persistedObjects.add(m);
		SystemHandler s = new SystemHandler();
		List<Machine> list = s.searchByKeyword(name);
		assertTrue(list.size() > 0);
	}

}
