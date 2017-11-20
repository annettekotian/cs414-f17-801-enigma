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

import edu.colostate.cs.cs414.enigma.builder.TrainerBuilder;
import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Exercise;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.entity.Workout;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseDurationException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseSetException;
import edu.colostate.cs.cs414.enigma.entity.exception.WorkHoursException;

public class TrainerHandlerTest {

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
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John");
		tb.setLastName("Doe");
		tb.setEmail("johndoe@email.com");
		tb.setPhoneNumber("555-555-5555");
		tb.setHealthInsurance("Cigna");
		tb.setStreet("720 City park");
		tb.setCity("Fort Collins");
		tb.setState("Colorado");
		tb.setZipcode("80521");
		tb.setUsername("johndoe");
		tb.setPassword("password");
		tb.setConfirmPassword("password");

		Trainer newTrainer = tb.createTrainer();
		persistedObjects.add(newTrainer);
		tb.close();
	}
	
	@Test(expected = PersistenceException.class)
	public void presistNewTrainerDuplicateUsername() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John");
		tb.setLastName("Doe");
		tb.setEmail("johndoe@email.com");
		tb.setPhoneNumber("555-555-5555");
		tb.setHealthInsurance("Cigna");
		tb.setStreet("720 City park");
		tb.setCity("Fort Collins");
		tb.setState("Colorado");
		tb.setZipcode("80521");
		tb.setUsername("johndoe");
		tb.setPassword("password");
		tb.setConfirmPassword("password");

		Trainer newTrainer = tb.createTrainer();
		persistedObjects.add(newTrainer);
		newTrainer = tb.createTrainer();
	}
	
	
	public Trainer createArbitraryTrainer() throws AddressException {
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
	public void addQualification() throws AddressException {
		Trainer trainer = createArbitraryTrainer();
		int trainerId = trainer.getId();
		String qualification = "Iron Man";
		
		TrainerHandler th = new TrainerHandler();
		th.addQualification(trainerId, qualification);
		
		th.close();
	}
	
	@Test
	public void removeQualification() throws AddressException {
		Trainer trainer = createArbitraryTrainer();
		int trainerId = trainer.getId();
		String qualification = "Iron Man";
		
		TrainerHandler th = new TrainerHandler();
		th.addQualification(trainerId, qualification);
		th.deleteQualification(trainerId, qualification);
		th.close();
	}
	
	@Test
	public void addWorkHours() throws WorkHoursException, AddressException {
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
	public void deleteSingleWorkHours() throws WorkHoursException, AddressException {
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
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John");
		tb.setLastName("Doe");
		tb.setEmail("johndoe");
		tb.setPhoneNumber("555-555-5555");
		tb.setHealthInsurance("Cigna");
		tb.setStreet("720 City park");
		tb.setCity("Fort Collins");
		tb.setState("Colorado");
		tb.setZipcode("80521");
		tb.setUsername("johndoe");
		tb.setPassword("password");
		tb.setConfirmPassword("password");
		tb.createTrainer();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void incorrectZipcodeFormat() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John");
		tb.setLastName("Doe");
		tb.setEmail("johndoe");
		tb.setPhoneNumber("555-555-5555");
		tb.setHealthInsurance("Cigna");
		tb.setStreet("720 City park");
		tb.setCity("Fort Collins");
		tb.setState("Colorado");
		tb.setZipcode("8052");
		tb.setUsername("johndoe");
		tb.setPassword("password");
		tb.setConfirmPassword("password");
		tb.createTrainer();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyCreateTrainerParameters() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John");
		tb.setLastName("Doe");
		tb.setEmail("johndoe");
		tb.setPhoneNumber("555-555-5555");
		tb.setHealthInsurance("Cigna");
		tb.setStreet("720 City park");
		tb.setCity("Fort Collins");
		tb.setState("Colorado");
		tb.setZipcode("");
		tb.setUsername("johndoe");
		tb.setPassword("password");
		tb.setConfirmPassword("password");
		tb.createTrainer();
	}
	
	@Test
	public void searchForTrainer() throws AddressException {
		Trainer trainer = createArbitraryTrainer();
		TrainerHandler th = new TrainerHandler();
		List<Trainer> searchTrainers = th.searchTrainers(trainer.getPersonalInformation().getLastName());
		th.close();
		assertNotEquals("Failed to succesfully search for trainers", searchTrainers.size(), 0);
	}
	
	@Test
	public void getAllTrainers() throws AddressException {
		Trainer trainer = createArbitraryTrainer();
		TrainerHandler th = new TrainerHandler();
		List<Trainer> trainers = th.getAllTrainers();
		th.close();
		assertTrue("Failed to get all trainers", trainers.size() != 0);
	}
	
	@Test
	public void getTrainerById() throws AddressException {
		Trainer trainer = createArbitraryTrainer();
		TrainerHandler th = new TrainerHandler();
		Trainer searchTrainer = th.getTrainerById(trainer.getId());
		assertEquals("Failed to get trainer from db", trainer.getId(), searchTrainer.getId());
		th.close();
	}
	
	@Test
	public void getTrainerByUserId() throws AddressException {
		Trainer trainer = createArbitraryTrainer();
		TrainerHandler th = new TrainerHandler();
		Trainer searchTrainer = th.getTrainerByUserId(trainer.getUser().getId());
		assertEquals("Failed to get trainer from db", trainer.getId(), searchTrainer.getId());
		th.close();
	}
	
	@Test
	public void modifyTrainer() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John");
		tb.setLastName("Doe");
		tb.setEmail("johndoe");
		tb.setPhoneNumber("555-555-5555");
		tb.setHealthInsurance("Cigna");
		tb.setStreet("720 City park");
		tb.setCity("Fort Collins");
		tb.setState("Colorado");
		tb.setZipcode("");
		tb.setUsername("johndoe");
		tb.setPassword("password");
		tb.setConfirmPassword("password");

		Trainer newTrainer = tb.createTrainer();
		persistedObjects.add(newTrainer);
		
		tb.setFirstName("Bob");
		tb.setId(newTrainer.getId());
		tb.modifyTrainer();
		tb.close();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void trainerPasswordsDoNotMatch() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John");
		tb.setLastName("Doe");
		tb.setEmail("johndoe");
		tb.setPhoneNumber("555-555-5555");
		tb.setHealthInsurance("Cigna");
		tb.setStreet("720 City park");
		tb.setCity("Fort Collins");
		tb.setState("Colorado");
		tb.setZipcode("");
		tb.setUsername("johndoe");
		tb.setPassword("password");
		tb.setConfirmPassword("asdfasd");

		Trainer newTrainer = tb.createTrainer();
	}
	
	@Test
	public void deleteTrainer() throws Exception {
		TrainerBuilder tb = new TrainerBuilder();
		tb.setFirstName("John");
		tb.setLastName("Doe");
		tb.setEmail("johndoe");
		tb.setPhoneNumber("555-555-5555");
		tb.setHealthInsurance("Cigna");
		tb.setStreet("720 City park");
		tb.setCity("Fort Collins");
		tb.setState("Colorado");
		tb.setZipcode("");
		tb.setUsername("johndoe");
		tb.setPassword("password");
		tb.setConfirmPassword("password");

		Trainer newTrainer = tb.createTrainer();
		persistedObjects.add(newTrainer);
		tb.setId(newTrainer.getId());
		tb.deleteTrainer();
		tb.close();
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
	
	//////***************** Create workout **************////////////////
	
	@Test
	public void testCreateWorkout() throws ExerciseException, PersistenceException, ExerciseDurationException, ExerciseSetException {
		String name = "testWorkout123456";
		TrainerHandler th = new TrainerHandler();
		Exercise ex1 = th.createExercise("ex1122346", 0, 0, 30, 30, new ArrayList<Integer>());
		Exercise ex2 = th.createExercise("ex112234567", 0, 0, 30, 30, new ArrayList<Integer>());
		String[] exList = {ex2.getName(), ex1.getName()}; 
		persistedObjects.add(ex1);
		persistedObjects.add(ex2);
		 th = new TrainerHandler();
		Workout w = th.createWorkout(name, exList);
		persistedObjects.add(w);
		List<Exercise> list = w.getExercises();
		assertTrue(list.get(0).getName().equals(ex2.getName()) && list.get(1).getName().equals(ex1.getName()));
	}
	
	@Test (expected = PersistenceException.class)
	public void testCreateDuplicateWorkout() throws ExerciseException, PersistenceException, ExerciseDurationException, ExerciseSetException {
		String name = "testWorkout12348856";
		TrainerHandler th = new TrainerHandler();
		Exercise ex1 = th.createExercise("ex1122346", 0, 0, 30, 30, new ArrayList<Integer>());
		Exercise ex2 = th.createExercise("ex112234567", 0, 0, 30, 30, new ArrayList<Integer>());
		String[] exList = {ex2.getName(), ex1.getName()}; 
		persistedObjects.add(ex1);
		persistedObjects.add(ex2);
		Workout w = th.createWorkout(name, exList);
		persistedObjects.add(w);
		th.createWorkout(name, exList);
		th.close();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateWorkoutWithoutName() throws ExerciseException, PersistenceException, ExerciseDurationException, ExerciseSetException {
		String name = "";
		TrainerHandler th = new TrainerHandler();
		Exercise ex1 = th.createExercise("ex1122346", 0, 0, 30, 30, new ArrayList<Integer>());
		Exercise ex2 = th.createExercise("ex112234567", 0, 0, 30, 30, new ArrayList<Integer>());
		String[] exList = {ex2.getName(), ex1.getName()}; 
		persistedObjects.add(ex1);
		persistedObjects.add(ex2);
		th = new TrainerHandler();
		
		Workout w = th.createWorkout(name, exList);
		persistedObjects.add(w);
		
	}
	
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateWorkoutWithoutExercises() throws ExerciseException, PersistenceException, ExerciseDurationException, ExerciseSetException {
		String name = "testWorkout123456";
		TrainerHandler th = new TrainerHandler();
		String[] exList = {}; 
		th = new TrainerHandler();
		Workout w = th.createWorkout(name, exList);
		persistedObjects.add(w);
		
	}
	

	
//////***************** Update workout **************//////////
	private Workout createWorkout() throws ExerciseException, PersistenceException, ExerciseDurationException, ExerciseSetException {
		String name = "testWorkout123456";
		TrainerHandler th = new TrainerHandler();
		Exercise ex1 = th.createExercise("ex1122346", 0, 0, 30, 30, new ArrayList<Integer>());
		Exercise ex2 = th.createExercise("ex112234567", 0, 0, 30, 30, new ArrayList<Integer>());
		String[] exList = new String[2];
		exList[0] = ex2.getName();
		exList[1] = ex1.getName();
		persistedObjects.add(ex1);
		persistedObjects.add(ex2);
		th = new TrainerHandler();
		Workout w = th.createWorkout(name, exList);
		persistedObjects.add(w);
		
		return w;
	}
	@Test
	public void updateWorkoutName() throws PersistenceException, ExerciseException, ExerciseDurationException, ExerciseSetException {
		Workout w = createWorkout();
		String name = "testWorkout1234567766";
		TrainerHandler th = new TrainerHandler();
		Exercise ex1 = th.createExercise("ex1122346777", 0, 0, 30, 30, new ArrayList<Integer>());
		persistedObjects.add(ex1);
		String[] exerciseNames = new String[3];
		List<Exercise> list = w.getExercises();
		for(int i = 0; i<list.size(); i++) {
			exerciseNames[i] = list.get(i).getName();
		}
		exerciseNames[2] = ex1.getName();
		Workout w2 = th.updateWorkout(Integer.toString(w.getId()), name, exerciseNames);
		assertTrue(w2.getName().equals(name) && w2.getExercises().size() == exerciseNames.length && w2.getExercises().get(2).getName() == ex1.getName());
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateWorkoutWithoutName() throws PersistenceException, ExerciseException, ExerciseDurationException, ExerciseSetException {
		Workout w = createWorkout();
		String name = "";
		TrainerHandler th = new TrainerHandler();
		String[] exerciseNames = new String[2];
		List<Exercise> list = w.getExercises();
		for(int i = 0; i<list.size(); i++) {
			exerciseNames[i] = list.get(i).getName();
		}
		th.updateWorkout(Integer.toString(w.getId()), name, exerciseNames);
		
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateWorkoutWithoutExercises() throws PersistenceException, ExerciseException, ExerciseDurationException, ExerciseSetException {
		Workout w = createWorkout();
		String name = "testabcdef";
		TrainerHandler th = new TrainerHandler();
		
		th.updateWorkout(Integer.toString(w.getId()), name, null);
		
	}
		
//************************ Search Workout ************************//
	
	@Test
	public void testSearchWorkout() throws PersistenceException, ExerciseException, ExerciseDurationException, ExerciseSetException {
		Workout w = createWorkout();
		TrainerHandler th = new TrainerHandler();
		List<Workout> list = th.searchWorkouts(w.getName());
		assertTrue(list.size() == 1 && list.get(0).getName().equals(w.getName()));
	}
	
	@Test
	public void testSearchWorkoutWithEmptyKeyword() throws PersistenceException, ExerciseException, ExerciseDurationException, ExerciseSetException {
		Workout w = createWorkout();
		TrainerHandler th = new TrainerHandler();
		List<Workout> list = th.searchWorkouts("");
		assertTrue(list.size() >= 0);
	}
	
	
	/// ******* assign/ unassign workout***/

	
	
	public void assignWorkout(int customerId, int workoutId) {

		// Get the customer and the workout entity
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("id", customerId);
		Customer customer = (Customer) dao.querySingle("Customer.findById", customerParams);

		Map<String, Object> workoutParams = new HashMap<String, Object>();
		workoutParams.put("id", workoutId);
		Workout workout = (Workout) dao.querySingle("Workout.findId", workoutParams);

		// Assign (add) the customer the workout
		customer.addWorkout(workout);

		// Persist the changes
		dao.update(customer);
	}
	
	
	@Test
	public void assignWorkout() throws Exception {
		// Create a customer
		String fName = "Annetteqweqwepoqweqwpfsdfoqased";
		String lName = "Kotian";
		String email = "ann@email.com";
		String phone = "999-999-9999";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";

		CustomerHandler ch = new CustomerHandler();
		Customer c1 = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state,
				membershipStatus);
		persistedObjects.add(c1);
		
		// Create a workout
		String name = "testWorkout123456";
		TrainerHandler th = new TrainerHandler();
		Exercise ex1 = th.createExercise("ex1122346", 0, 0, 30, 30, new ArrayList<Integer>());
		Exercise ex2 = th.createExercise("ex112234567", 0, 0, 30, 30, new ArrayList<Integer>());
		String[] exList = {ex2.getName(), ex1.getName()}; 
		persistedObjects.add(ex1);
		persistedObjects.add(ex2);
		 th = new TrainerHandler();
		Workout w = th.createWorkout(name, exList);
		persistedObjects.add(w);
		
		// Assign work-out to customer
		th.assignWorkout(c1.getId(), w.getId());
	}
	
	@Test
	public void getWorkoutByCustomerId() throws Exception {
		// Create a customer
		String fName = "Annetteqweqwepoqweqwpfsdfoqased";
		String lName = "Kotian";
		String email = "ann@email.com";
		String phone = "999-999-9999";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";

		CustomerHandler ch = new CustomerHandler();
		Customer c1 = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state,
				membershipStatus);
		persistedObjects.add(c1);
		
		// Create a workout
		String name = "testWorkout123456";
		TrainerHandler th = new TrainerHandler();
		Exercise ex1 = th.createExercise("ex1122346", 0, 0, 30, 30, new ArrayList<Integer>());
		Exercise ex2 = th.createExercise("ex112234567", 0, 0, 30, 30, new ArrayList<Integer>());
		String[] exList = {ex2.getName(), ex1.getName()}; 
		persistedObjects.add(ex1);
		persistedObjects.add(ex2);
		 th = new TrainerHandler();
		Workout w = th.createWorkout(name, exList);
		persistedObjects.add(w);
		
		// Assign work-out to customer
		th.assignWorkout(c1.getId(), w.getId());
		
		List<Workout> workouts = th.getWorkoutsByCustomerId(c1.getId());
		assertEquals("Failed to get workouts for customer", workouts.size(), 1);
	}
	
	@Test
	public void unassignWorkout() throws Exception {
		// Create a customer
		String fName = "Annetteqweqwepoqweqwpfsdfoqased";
		String lName = "Kotian";
		String email = "ann@email.com";
		String phone = "999-999-9999";
		String insurance = "Cigna";
		String street = "720 City park";
		String city = "Fort Collins";
		String state = "Colorado";
		String zip = "80521";
		String membershipStatus = "ACTIVE";

		CustomerHandler ch = new CustomerHandler();
		Customer c1 = ch.createNewCustomer(email, fName, lName, phone, insurance, street, city, zip, state,
				membershipStatus);
		persistedObjects.add(c1);
		
		// Create a workout
		String name = "testWorkout123456";
		TrainerHandler th = new TrainerHandler();
		Exercise ex1 = th.createExercise("ex1122346", 0, 0, 30, 30, new ArrayList<Integer>());
		Exercise ex2 = th.createExercise("ex112234567", 0, 0, 30, 30, new ArrayList<Integer>());
		String[] exList = {ex2.getName(), ex1.getName()}; 
		persistedObjects.add(ex1);
		persistedObjects.add(ex2);
		 th = new TrainerHandler();
		Workout w = th.createWorkout(name, exList);
		persistedObjects.add(w);
		
		// Assign work-out to customer
		th.assignWorkout(c1.getId(), w.getId());
		
		// Assign work-out to customer
		th.unassignWorkout(c1.getId(), w.getId());
		
		// Verify workout was added
		assertEquals("Failled to assign workout to customer", ch.getCustomerById(c1.getId()).getWorkouts().size(), 0);

		
	}
	
	//************************* Delete workout ****************************//
	
	@Test
	public void testDeleteWorkout() throws PersistenceException, ExerciseException, ExerciseDurationException, ExerciseSetException {
		Workout w = createWorkout();
		String id = Integer.toString(w.getId());
		HashMap <String, Object> params = new HashMap<>();
		params.put("id", w.getId() );
		TrainerHandler th = new TrainerHandler();
		th.deleteWorkout(id);
		Workout w2 = (Workout)dao.querySingle("Workout.findId", params);
		assertNull(w2);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDeleteWorkoutWithoutId() throws PersistenceException, ExerciseException, ExerciseDurationException, ExerciseSetException {
		TrainerHandler th = new TrainerHandler();
		th.deleteWorkout("");
			
	}
}
