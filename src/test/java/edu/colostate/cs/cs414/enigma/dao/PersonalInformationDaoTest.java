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
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class PersonalInformationDaoTest {
	
	private static EntityManagerFactoryListener emfl;
	private List<PersonalInformation> addedPersonalInformation;
	private List<HealthInsurance> addedHealthInsurance;
	private PersonalInformationDao personalInformationDao;
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
		addedPersonalInformation = new ArrayList<PersonalInformation>();
		personalInformationDao = new PersonalInformationDao();
		
		addedHealthInsurance = new ArrayList<HealthInsurance>();
		healthInsuranceDao = new HealthInsuranceDao();
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<addedPersonalInformation.size(); i++) {
			personalInformationDao.remove(addedPersonalInformation.get(i));
		}
		personalInformationDao.close();
		addedPersonalInformation = null;
		
		for(int i=0; i<addedHealthInsurance.size(); i++) {
			healthInsuranceDao.remove(addedHealthInsurance.get(i));
		}
		healthInsuranceDao.close();
		addedHealthInsurance = null;
	}

	@Test
	public void addPersonalInformation() {
		HealthInsurance healthInsurance = new HealthInsurance("Java Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", healthInsurance);
		
		personalInformationDao.persist(personalInformation);
		personalInformationDao.commit();
		assertEquals("Failed to record personal informaiton in database", personalInformationDao.findPersonalInformationById(personalInformation.getId()), personalInformation);
		
		addedPersonalInformation.add(personalInformation);
		addedHealthInsurance.add(healthInsurance);
	}

	@Test
	public void changeHealthInsurance() {
		HealthInsurance healthInsurance = new HealthInsurance("Java Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", healthInsurance);
		personalInformationDao.persist(personalInformation);
		personalInformationDao.commit();
		
		HealthInsurance newHealthInsurance = new HealthInsurance("Python Insurance");
		personalInformation.setHealthInsurance(newHealthInsurance);
		personalInformationDao.commit();
		assertEquals("Unable to update health insurance", personalInformationDao.findPersonalInformationById(personalInformation.getId()).getHealthInsurance(), personalInformation.getHealthInsurance());
	
		addedPersonalInformation.add(personalInformation);
		addedHealthInsurance.add(healthInsurance);
		addedHealthInsurance.add(newHealthInsurance);
	}
}
