package edu.colostate.cs.cs414.enigma.handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
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
import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.Exercise;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseDurationException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseSetException;
import edu.colostate.cs.cs414.enigma.entity.exception.WorkHoursException;
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
	public void presistNewTrainer() throws Exception {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe@email.com";
		String phone = "555-555-5555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String userName = "johndoe";
		String password = "password";
		TrainerHandler th = new TrainerHandler();
		Trainer newTrainer = th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password, password);
		persistedObjects.add(newTrainer);
	}
	
	@Test(expected = PersistenceException.class)
	public void presistNewTrainerDuplicateUsername() throws Exception {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe@email.com";
		String phone = "555-555-5555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String userName = "johndoe";
		String password = "password";
		TrainerHandler th = new TrainerHandler();
		Trainer newTrainer = th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password, password);
		persistedObjects.add(newTrainer);
		
		firstName = "John";
		lastName = "Doe";
		email = "johndoe@email.com";
		phone = "555-555-5555";
		insurance = "Cigna";
		street = "720 City park";
		city = "Fort Collins";
		state = "Colorado";
		zip = "80521";
		userName = "johndoe";
		password = "password";
		newTrainer = th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password, password);
	}
	
	
	public Trainer createArbitraryTrainer() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "555-555-5555", insurance, newAddress);
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
	public void removeQualification() {
		Trainer trainer = createArbitraryTrainer();
		int trainerId = trainer.getId();
		String qualification = "Iron Man";
		
		TrainerHandler th = new TrainerHandler();
		th.addQualification(trainerId, qualification);
		th.deleteQualification(trainerId, qualification);
		th.close();
	}
	
	@Test
	public void addWorkHours() throws WorkHoursException {
		Trainer trainer = createArbitraryTrainer();
		int trainerId = trainer.getId();
		
		Date startDateTime = new Date();
		startDateTime.setYear(startDateTime.getYear() + 1);
		
		Date endDateTime = new Date();
		endDateTime.setYear(endDateTime.getYear() + 1);
		endDateTime.setMinutes(endDateTime.getMinutes() + 1);
		
		TrainerHandler th = new TrainerHandler();
		th.addWorkHours(trainerId, startDateTime, endDateTime);
		
		th.close();
	}
	
	@Test
	public void deleteSingleWorkHours() throws WorkHoursException {
		Trainer trainer = createArbitraryTrainer();
		int trainerId = trainer.getId();
		
		Date startDateTime = new Date();
		startDateTime.setYear(startDateTime.getYear() + 1);
		
		Date endDateTime = new Date();
		endDateTime.setYear(endDateTime.getYear() + 1);
		endDateTime.setMinutes(endDateTime.getMinutes() + 1);
		
		TrainerHandler th = new TrainerHandler();
		th.addWorkHours(trainerId, startDateTime, endDateTime);
		trainer = th.getTrainerById(trainerId);
		th.deleteWorkHours(trainerId, trainer.getWorkHours().get(0).getId());
	}
	
	@Test(expected = AddressException.class)
	public void incorrectEmailAddressFormat() throws Exception {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe";
		String phone = "555-555-5555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String userName = "johndoe";
		String password = "password";
		TrainerHandler th = new TrainerHandler();
		th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password, password);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void incorrectZipcodeFormat() throws Exception {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe";
		String phone = "555-555-5555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "805adsfasdf21";
		String userName = "johndoe";
		String password = "password";
		TrainerHandler th = new TrainerHandler();
		th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password, password);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyCreateTrainerParameters() throws Exception {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe";
		String phone = "555-555-5555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "";
		String userName = "johndoe";
		String password = "password";
		TrainerHandler th = new TrainerHandler();
		th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password, password);
	}
	
	@Test
	public void searchForTrainer() {
		Trainer trainer = createArbitraryTrainer();
		TrainerHandler th = new TrainerHandler();
		List<Trainer> searchTrainers = th.searchTrainers(trainer.getPersonalInformation().getLastName());
		th.close();
		assertNotEquals("Failed to succesfully search for trainers", searchTrainers.size(), 0);
	}
	
	@Test
	public void getAllTrainers() {
		Trainer trainer = createArbitraryTrainer();
		TrainerHandler th = new TrainerHandler();
		List<Trainer> trainers = th.getAllTrainers();
		th.close();
		assertTrue("Failed to get all trainers", trainers.size() != 0);
	}
	
	@Test
	public void getTrainerById() {
		Trainer trainer = createArbitraryTrainer();
		TrainerHandler th = new TrainerHandler();
		Trainer searchTrainer = th.getTrainerById(trainer.getId());
		assertEquals("Failed to get trainer from db", trainer.getId(), searchTrainer.getId());
		th.close();
	}
	
	@Test
	public void getTrainerByUserId() {
		Trainer trainer = createArbitraryTrainer();
		TrainerHandler th = new TrainerHandler();
		Trainer searchTrainer = th.getTrainerByUserId(trainer.getUser().getId());
		assertEquals("Failed to get trainer from db", trainer.getId(), searchTrainer.getId());
		th.close();
	}
	
	@Test
	public void modifyTrainer() throws Exception {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe@email.com";
		String phone = "555-555-5555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String userName = "johndoe";
		String password = "password";
		TrainerHandler th = new TrainerHandler();
		Trainer newTrainer = th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password, password);
		persistedObjects.add(newTrainer);
		th.modifyTrainer(newTrainer.getId(), "Bob", lastName, phone, email, street, city, state, zip, insurance, userName, password, password);
		th.close();
	}
	
	@Test
	public void deleteTrainer() throws Exception {
		String firstName = "John";
		String lastName = "Doe";
		String email = "johndoe@email.com";
		String phone = "555-555-5555";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String userName = "johndoe";
		String password = "password";
		TrainerHandler th = new TrainerHandler();
		Trainer newTrainer = th.createNewTrainer(firstName, lastName, phone, email, street, city, state, zip, insurance, userName, password, password);
		persistedObjects.add(newTrainer);
		th.deleteTrainer(newTrainer.getId());
		th.close();
	}
	
	@Test
	public void createDeleteExerciseNoDurationNoSetsNoMachine() throws Exception {
		String name = "push-ups";
		int machineId = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		List<Integer> repetitions = new ArrayList<Integer>();
		TrainerHandler th = new TrainerHandler();
		Exercise newExercise = th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
		th.deleteExercise(newExercise.getId());
		th.close();
	}
	
	@Test
	public void createDeleteExerciseDurationNoSetsNoMachine() throws Exception {
		String name = "push-ups";
		int machineId = 0;
		int hours = 0;
		int minutes = 10;
		int seconds = 0;
		List<Integer> repetitions = new ArrayList<Integer>();
		TrainerHandler th = new TrainerHandler();
		Exercise newExercise = th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
		th.deleteExercise(newExercise.getId());
		th.close();
	}
	
	@Test
	public void createDeleteExerciseNoDurationSetsNoMachine() throws Exception {
		String name = "push-ups";
		int machineId = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		List<Integer> repetitions = new ArrayList<Integer>();
		repetitions.add(5);
		repetitions.add(10);
		repetitions.add(15);
		TrainerHandler th = new TrainerHandler();
		Exercise newExercise = th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
		th.deleteExercise(newExercise.getId());
		th.close();
	}

	@Test
	public void createDeleteExerciseDurationSetsNoMachine() throws Exception {
		String name = "push-ups";
		int machineId = 0;
		int hours = 0;
		int minutes = 10;
		int seconds = 0;
		List<Integer> repetitions = new ArrayList<Integer>();
		repetitions.add(5);
		repetitions.add(10);
		repetitions.add(15);
		TrainerHandler th = new TrainerHandler();
		Exercise newExercise = th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
		th.deleteExercise(newExercise.getId());
		th.close();
	}
	
	@Test
	public void modifyExerciseDurationNoMachine() throws Exception {
		String name = "push-ups";
		int machineId = 0;
		int hours = 0;
		int minutes = 10;
		int seconds = 0;
		List<Integer> repetitions = new ArrayList<Integer>();
		repetitions.add(5);
		repetitions.add(10);
		repetitions.add(15);
		TrainerHandler th = new TrainerHandler();
		Exercise exercise = th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
		
		int updatedHours = 10;
		exercise = th.modifyExercise(exercise.getId(), name, machineId, updatedHours, minutes, seconds, repetitions);
		assertEquals("Failed to update duration", exercise.getDuration().getHours(), updatedHours);
		
		th.deleteExercise(exercise.getId());
		th.close();
	}
	
	@Test
	public void modifyExerciseSetsNoMachine() throws Exception {
		String name = "push-ups";
		int machineId = 0;
		int hours = 0;
		int minutes = 10;
		int seconds = 0;
		List<Integer> repetitions = new ArrayList<Integer>();
		repetitions.add(5);
		repetitions.add(10);
		repetitions.add(15);
		TrainerHandler th = new TrainerHandler();
		Exercise exercise = th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
		
		repetitions = new ArrayList<Integer>();
		repetitions.add(5);
		repetitions.add(10);
		repetitions.add(20);
		th.modifyExercise(exercise.getId(), name, machineId, hours, minutes, seconds, repetitions);
		
		
		th.deleteExercise(exercise.getId());
		th.close();
	}
	
	@Test
	public void modifyExerciseNameNoMachine() throws Exception {
		String name = "push-ups";
		int machineId = 0;
		int hours = 0;
		int minutes = 10;
		int seconds = 0;
		List<Integer> repetitions = new ArrayList<Integer>();
		repetitions.add(5);
		repetitions.add(10);
		repetitions.add(15);
		TrainerHandler th = new TrainerHandler();
		Exercise exercise = th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
		
		name = "mega push-ups";
		exercise = th.modifyExercise(exercise.getId(), name, machineId, hours, minutes, seconds, repetitions);
		assertEquals("Failed to update name", exercise.getName(), name);
		
		th.deleteExercise(exercise.getId());
		th.close();
	}
	
	@Test
	public void modifyExerciseDeleteDurationNoMachine() throws Exception {
		String name = "push-ups";
		int machineId = 0;
		int hours = 0;
		int minutes = 10;
		int seconds = 0;
		List<Integer> repetitions = new ArrayList<Integer>();
		repetitions.add(5);
		repetitions.add(10);
		repetitions.add(15);
		TrainerHandler th = new TrainerHandler();
		Exercise exercise = th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
		
		exercise = th.modifyExercise(exercise.getId(), name, machineId, 0, 0, 0, repetitions);
		assertNull("Failed to delete duration", exercise.getDuration());
		
		th.deleteExercise(exercise.getId());
		th.close();
	}
	
	@Test
	public void modifyExerciseDeleteSetsNoMachine() throws Exception {
		String name = "push-ups";
		int machineId = 0;
		int hours = 0;
		int minutes = 10;
		int seconds = 0;
		List<Integer> repetitions = new ArrayList<Integer>();
		repetitions.add(5);
		repetitions.add(10);
		repetitions.add(15);
		TrainerHandler th = new TrainerHandler();
		Exercise exercise = th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
		
		exercise = th.modifyExercise(exercise.getId(), name, machineId, hours, minutes, seconds, new ArrayList<Integer>());
		assertEquals("Failed to delete sets", exercise.getSets().size(), 0);
		
		th.deleteExercise(exercise.getId());
		th.close();
	}
	
	@Test
	public void searchExercise() throws Exception {
		String name = "push-ups";
		int machineId = 0;
		int hours = 0;
		int minutes = 10;
		int seconds = 0;
		List<Integer> repetitions = new ArrayList<Integer>();
		repetitions.add(5);
		repetitions.add(10);
		repetitions.add(15);
		TrainerHandler th = new TrainerHandler();
		Exercise exercise = th.createExercise(name, machineId, hours, minutes, seconds, repetitions);
		
		List<Exercise> exercises = th.searchExercises(name);
		assertTrue("Failed to search for exercise", exercises.contains(exercise));
		
		th.deleteExercise(exercise.getId());
		th.close();
	}
}
