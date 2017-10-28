package edu.colostate.cs.cs414.enigma.handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.entity.WorkHoursException;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class TrainerHandlerTest {

	private static EntityManagerFactoryListener emfl;
	private EntityManagerDao dao;
	private List<Object> persistedObjects;

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
		persistedObjects = new ArrayList<Object>();
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<persistedObjects.size(); i++) {
			dao.remove(persistedObjects.get(i));
		}
		dao.close();
	}
	/********* Test for new trainer **************/ 
	@Test
	public void presistNewTrainer() {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe@email.com";
		String phone = "5555555555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String userName = "johndoe";
		String password = "password";
		TrainerHandler th = new TrainerHandler();
		Trainer newTrainer = th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password);
		persistedObjects.add(newTrainer);
	}
	
	@Test(expected = PersistenceException.class)
	public void presistNewTrainerDuplicateUsername() {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe@email.com";
		String phone = "5555555555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String userName = "johndoe";
		String password = "password";
		TrainerHandler th = new TrainerHandler();
		Trainer newTrainer = th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password);
		persistedObjects.add(newTrainer);
		
		firstName = "John";
		lastName = "Doe";
		email = "johndoe@email.com";
		phone = "5555555555";
		insurance = "Cigna";
		street = "720 City park";
		city = "Fort Collins";
		state = "Colorado";
		zip = "80521";
		userName = "johndoe";
		password = "password";
		newTrainer = th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password);
	}
	
	
	public Trainer createArbitraryTrainer() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555-5555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", insurance, newAddress);
		parameters = new HashMap<String, Object>();
		parameters.put("level", "TRAINER");
		UserLevel userLevel = (UserLevel) dao.querySingle("UserLevel.findLevel", parameters);
		User user = new User("trainer", "password", userLevel);
		Trainer trainer = new Trainer(personalInformation, user);
		dao.persist(trainer);
		persistedObjects.add(trainer);
		persistedObjects.add(insurance);
		return trainer;
	}

	@Test
	public void addQualification() {
		Trainer trainer = createArbitraryTrainer();
		int trainerId = trainer.getId();
		String qualification = "Iron Man";
		
		TrainerHandler th = new TrainerHandler();
		th.addQualification(trainerId, qualification);
		
		th.close();
	}
	
	@Test
	public void addWorkHours() throws WorkHoursException {
		Trainer trainer = createArbitraryTrainer();
		int trainerId = trainer.getId();
		String qualification = "Iron Man";
		
		Date startDateTime = new Date();
		startDateTime.setYear(startDateTime.getYear() + 1);
		
		Date endDateTime = new Date();
		endDateTime.setYear(endDateTime.getYear() + 1);
		endDateTime.setMinutes(endDateTime.getMinutes() + 1);
		
		TrainerHandler th = new TrainerHandler();
		th.addWorkHours(trainerId, startDateTime, endDateTime);
		
		th.close();
	}
}
