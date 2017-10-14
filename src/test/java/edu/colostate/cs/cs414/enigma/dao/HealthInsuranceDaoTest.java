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
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class HealthInsuranceDaoTest {
	
	private static EntityManagerFactoryListener emfl;
	private List<HealthInsurance> addedHealthInsurances;
	private HealthInsuranceDao healthInsuranceDao;

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
		addedHealthInsurances = new ArrayList<HealthInsurance>();
		healthInsuranceDao = new HealthInsuranceDao();
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<addedHealthInsurances.size(); i++) {
			healthInsuranceDao.remove(addedHealthInsurances.get(i));
		}
		healthInsuranceDao.close();
		addedHealthInsurances = null;
	}

	@Test
	public void persistNewHealthInsurance() {
		HealthInsurance newHealthInsurance = new HealthInsurance("CSU Insurance");
		healthInsuranceDao.persist(newHealthInsurance);
		healthInsuranceDao.commit();
		assertEquals("Failed to commit new health insurance to the database", healthInsuranceDao.findByHealthInsuranceId(newHealthInsurance.getId()).getId(), newHealthInsurance.getId());
		addedHealthInsurances.add(newHealthInsurance);
	}

	@Test
	public void getAllHealthInsurances() {
		HealthInsurance newHealthInsurance = new HealthInsurance("CSU Insurance");
		healthInsuranceDao.persist(newHealthInsurance);
		healthInsuranceDao.commit();
		addedHealthInsurances.add(newHealthInsurance);
		List<HealthInsurance> healthInsurances = healthInsuranceDao.getHealthInsurances();
		assertNotEquals("Failed to get List of health insurances", 0, healthInsurances.size());
	}
}
