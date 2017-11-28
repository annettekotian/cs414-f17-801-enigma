package edu.colostate.cs.cs414.enigma.builder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.handler.builder.TrainerBuilder;

public class TrainerBuilderTest {

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
	public void presistNewTrainer() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John").setLastName("Doe").setEmail("johndoe@email.com").setPhoneNumber("555-555-5555").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("80521");
		tb.setUsername("johndoe").setPassword("password").setConfirmPassword("password");

		Trainer newTrainer = tb.createTrainer();
		persistedObjects.add(newTrainer);
		tb.close();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", newTrainer.getId());
		Trainer trainer = (Trainer) dao.querySingle("Trainer.findById", params);
		assertTrue(newTrainer.getId() == trainer.getId());
	}
	
	@Test(expected = PersistenceException.class)
	public void presistNewTrainerDuplicateUsername() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John").setLastName("Doe").setEmail("johndoe@email.com").setPhoneNumber("555-555-5555").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("80521");
		tb.setUsername("johndoe").setPassword("password").setConfirmPassword("password");

		Trainer newTrainer = tb.createTrainer();
		persistedObjects.add(newTrainer);
		newTrainer = tb.createTrainer();
	}
	

	@Test(expected = AddressException.class)
	public void incorrectEmailAddressFormat() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John").setLastName("Doe").setEmail("johndoe").setPhoneNumber("555-555-5555").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("80521");
		tb.createTrainer();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void incorrectZipcodeFormat() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John").setLastName("Doe").setEmail("johndoe@email.com").setPhoneNumber("555-555-5555").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("8052");
		tb.setUsername("johndoe").setPassword("password").setConfirmPassword("password");
		tb.createTrainer();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyCreateTrainerParameters() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John").setLastName("Doe").setEmail("johndoe@email.com").setPhoneNumber("555-555-5555").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("");
		tb.setUsername("johndoe").setPassword("password").setConfirmPassword("password");
		tb.createTrainer();
	}
	
	@Test
	public void modifyTrainer() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John").setLastName("Doe").setEmail("johndoe@email.com").setPhoneNumber("555-555-5555").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("80521");
		tb.setUsername("johndoe").setPassword("password").setConfirmPassword("password");

		Trainer newTrainer = tb.createTrainer();
		persistedObjects.add(newTrainer);
		
		tb.setFirstName("Bob");
		tb.setId(newTrainer.getId());
		tb.updateTrainer(newTrainer.getId());
		tb.close();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void trainerPasswordsDoNotMatch() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John").setLastName("Doe").setEmail("johndoe@email.com").setPhoneNumber("555-555-5555").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("80521");
		tb.setUsername("johndoe").setPassword("password").setConfirmPassword("12345678");

		Trainer newTrainer = tb.createTrainer();
	}
	
	@Test
	public void deleteTrainer() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John").setLastName("Doe").setEmail("johndoe@email.com").setPhoneNumber("555-555-5555").setHealthInsurance("Cigna")
		.setStreet("720 City park").setCity("Fort Collins").setState("Colorado").setZipcode("80521");
		tb.setUsername("johndoe").setPassword("password").setConfirmPassword("password");

		Trainer newTrainer = tb.createTrainer();
		persistedObjects.add(newTrainer);
		tb.setId(newTrainer.getId());
		tb.deleteTrainer(newTrainer.getId());
		tb.close();
	}
}
