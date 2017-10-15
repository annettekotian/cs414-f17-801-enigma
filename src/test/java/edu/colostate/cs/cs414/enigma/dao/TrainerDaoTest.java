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
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class TrainerDaoTest {

	private static EntityManagerFactoryListener emfl;
	private List<Trainer> addedTrainer;
	private List<HealthInsurance> addedHealthInsurance;
	private HealthInsuranceDao healthInsuranceDao;
	private TrainerDao trainerDao;
	
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
		addedTrainer = new ArrayList<Trainer>();
		addedHealthInsurance = new ArrayList<HealthInsurance>();
		
		healthInsuranceDao = new HealthInsuranceDao();
		trainerDao = new TrainerDao();
		
		UserLevelDao userLevelDao = new UserLevelDao();
		userLevel = userLevelDao.findUserLevelByDescription("TRAINER");
		userLevelDao.close();
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<addedTrainer.size(); i++) {
			trainerDao.remove(addedTrainer.get(i));
		}
		trainerDao.close();
		addedTrainer = null;
		
		for(int i=0; i<addedHealthInsurance.size(); i++) {
			healthInsuranceDao.remove(addedHealthInsurance.get(i));
		}
		healthInsuranceDao.close();
		addedHealthInsurance = null;
	}

	@Test
	public void addNewTrainer() {
		User user = new User("trainer", "password", userLevel);
		HealthInsurance healthInsurance = new HealthInsurance("Java Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", healthInsurance);
		Trainer trainer = new Trainer(personalInformation, user);
		
		trainerDao.persist(trainer);
		trainerDao.commit();
		assertEquals("New manager was not successully entered", trainerDao.findTrainerById(trainer.getId()), trainer);
		
		addedTrainer.add(trainer);
		addedHealthInsurance.add(healthInsurance);
	}

}
