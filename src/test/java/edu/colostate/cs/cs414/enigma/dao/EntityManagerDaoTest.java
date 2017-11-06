package edu.colostate.cs.cs414.enigma.dao;

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

import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.ExerciseDuration;
import edu.colostate.cs.cs414.enigma.entity.Exercise;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Machine;
import edu.colostate.cs.cs414.enigma.entity.Manager;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.Qualification;
import edu.colostate.cs.cs414.enigma.entity.ExerciseSet;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.entity.WorkHours;
import edu.colostate.cs.cs414.enigma.entity.Workout;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseException;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class EntityManagerDaoTest {
	
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

	@Test
	public void getAllUserLevels() {
		List userLevels = dao.query("UserLevel.findAll", null);
		assertNotNull("Failed to get user levels", userLevels);
	}
	
	@Test
	public void getAdminUserLevel() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("level", "ADMIN");
		UserLevel admin = (UserLevel) dao.querySingle("UserLevel.findLevel", parameters);
		assertNotNull("Failed to get admin user level", admin);
	}
	
	@Test
	public void getTrainerUserLevel() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("level", "TRAINER");
		UserLevel trainer = (UserLevel) dao.querySingle("UserLevel.findLevel", parameters);
		assertNotNull("Failed to get trainer user level", trainer);
	}

	@Test
	public void getManagerUserLevel() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("level", "MANAGER");
		UserLevel manager = (UserLevel) dao.querySingle("UserLevel.findLevel", parameters);
		assertNotNull("Failed to get trainer user level", manager);
	}
	
	@Test
	public void getAllUsers() {
		List users = dao.query("User.findAll", null);
		assertNotNull("Failed to get users", users);
	}
	
	@Test
	public void getAdminUser() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", "admin");
		User admin = (User) dao.querySingle("User.findUser", parameters);
		assertNotNull("Failed to get admin user", admin);
	}
	
	@Test
	public void persistUser() {
		UserLevel userLevel = (UserLevel) dao.querySingle("UserLevel.findAll", null);
		User newUser = new User("johndoe", "password", userLevel);
		dao.persist(newUser);
		persistedObjects.add(newUser);
	}
	
	@Test(expected = PersistenceException.class)
	public void persistRedundantUser() {
		UserLevel userLevel = (UserLevel) dao.querySingle("UserLevel.findAll", null);
		User newUser = new User("johndoe", "password", userLevel);
		dao.persist(newUser);
		persistedObjects.add(newUser);
		
		User redundantUser = new User("johndoe", "password", userLevel);
		dao.persist(redundantUser);
	}
	
	@Test
	public void getAllStates() {
		List states = dao.query("State.findAll", null);
		assertEquals("Failed to get all 50 states from lookup table", states.size(), 50);
	}
	
	@Test
	public void getColorado() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		assertNotNull("Failed to get a specific state", colorado);
	}
	
	@Test
	public void getAllHealthInsurances() {
		List insurances = dao.query("HealthInsurance.findAll", null);
		assertNotNull("Failed to get health insurances", insurances);
	}
	
	@Test
	public void getSpecificHealthInsurance() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("description", "Cigna");
		HealthInsurance insurance = (HealthInsurance) dao.querySingle("HealthInsurance.findDescription", parameters);
		assertNotNull("Failed to get specific health insurance", insurance);
	}
	
	@Test
	public void persisNewHealthInsurance() {
		HealthInsurance newInsurance = new HealthInsurance("Free Insurance");
		dao.persist(newInsurance);
		persistedObjects.add(newInsurance);
	}
	
	@Test
	public void getAllMembershipStatus() {
		List memberships = dao.query("Membership.findAll", null);
		assertNotNull("Failed to get all memebrship statuses", memberships);
	}
	
	@Test
	public void getActiveMembershipStatus() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", "ACTIVE");
		Membership membership = (Membership) dao.querySingle("Membership.findType", parameters);
		assertNotNull("Failed to get ACTIVE membership type", membership);
	}
	
	@Test
	public void getInactiveMembershipStatus() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", "INACTIVE");
		Membership membership = (Membership) dao.querySingle("Membership.findType", parameters);
		assertNotNull("Failed to get INACTIVE membership type", membership);
	}
	
	@Test
	public void persistAddress() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555-5555", colorado);
		dao.persist(newAddress);
		persistedObjects.add(newAddress);
	}
	
	@Test
	public void persistPersonalInformation() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555-5555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", insurance, newAddress);
		dao.persist(personalInformation);
		persistedObjects.add(personalInformation);
		persistedObjects.add(insurance);
	}
	
	@Test
	public void persistCustomer() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", "INACTIVE");
		Membership membership = (Membership) dao.querySingle("Membership.findType", parameters);
		parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555-5555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", insurance, newAddress);
		Customer customer = new Customer(personalInformation, membership);
		dao.persist(customer);
		persistedObjects.add(customer);
		persistedObjects.add(insurance);
	}
	
	@Test
	public void persistTrainer() {
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
	}
	
	@Test
	public void persistManager() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555-5555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", insurance, newAddress);
		parameters = new HashMap<String, Object>();
		parameters.put("level", "MANAGER");
		UserLevel userLevel = (UserLevel) dao.querySingle("UserLevel.findLevel", parameters);
		User user = new User("trainer", "password", userLevel);
		Manager managaer = new Manager(personalInformation, user);
		dao.persist(managaer);
		persistedObjects.add(managaer);
		persistedObjects.add(insurance);
	}
	
	@Test
	public void getManagerByFirstLastName() {
		persistManager();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("firstName", "John");
		parameters.put("lastName", "Doe");
		Manager manager = (Manager) dao.querySingle("Manager.findByName", parameters);
		assertTrue("Failed to retrieve manager by name", manager.getPersonalInformation().getFirstName().equals("John")
				&& manager.getPersonalInformation().getLastName().equals("Doe"));
	}
	
	@Test
	public void persistQualification() {
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
		
		Qualification qualification = new Qualification("Best Employee");
		
		Trainer trainer = new Trainer(personalInformation, user);
		trainer.addQualification(qualification);
		dao.persist(trainer);
		persistedObjects.add(trainer);
		persistedObjects.add(insurance);
		persistedObjects.add(qualification);		
	}
	
	@Test
	public void persistWorkHours() {
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
		
		Date startDateTime = new Date(117, 9, 1, 12, 30, 00);
		Date endDateTime = new Date(117, 9, 1, 120, 30, 00);
		WorkHours workHours = new WorkHours(startDateTime, endDateTime);
		try {
			trainer.addWorkHours(workHours);
			dao.update(trainer);
			
			trainer.removeAllWorkHours();
			dao.update(trainer);
		}
		finally {
			for(int i=0; i<trainer.getWorkHours().size(); i++) {
				persistedObjects.add(trainer.getWorkHours().get(i));
			}
			persistedObjects.add(trainer);
			persistedObjects.add(insurance);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", trainer.getId());
		trainer = (Trainer) dao.querySingle("Trainer.findById", params);
	}
	
	@Test
	public void persistNewMachine() throws Exception {
		Machine machine = new Machine("Treadmill", "/images/treadmill.png", 1);
		dao.persist(machine);
		persistedObjects.add(machine);
	}
	
	@Test
	public void persistNewExercise() throws Exception {
		Exercise exercise = new Exercise("Push-ups");
		dao.persist(exercise);
		persistedObjects.add(exercise);
	}
	
	@Test
	public void persistNewExerciseMachine() throws Exception {
		Machine machine = new Machine("Treadmill", "/images/treadmill.png", 1);
		Exercise exercise = new Exercise("Push-ups");
		exercise.setMachine(machine);
		dao.persist(exercise);
		persistedObjects.add(exercise);
		persistedObjects.add(exercise.getMachine());
	}
	
	@Test
	public void persistNewExerciseDuration() throws Exception {
		ExerciseDuration duration = new ExerciseDuration(5, 5, 5);
		Exercise exercise = new Exercise("Push-ups");
		exercise.setDuration(duration);
		dao.persist(exercise);
		persistedObjects.add(exercise);
	}
	
	@Test
	public void persistNewExerciseSet() throws Exception {
		Exercise exercise = new Exercise("Push-ups");
		dao.persist(exercise);
		
		ExerciseSet set = new ExerciseSet(5);
		exercise.addSet(set);
		dao.update(exercise);
		
		exercise.deleteSets();
		dao.update(exercise);
		
		persistedObjects.add(set);
		persistedObjects.add(exercise);
	}
	
	@Test
	public void persisNewWorkout() throws Exception {
		Exercise exercise = new Exercise("Push-ups");
		
		Workout workout =  new Workout("Extreme Workout");
		workout.addExercise(exercise);
		
		dao.persist(workout);
		
		persistedObjects.add(exercise);
		persistedObjects.add(workout);
	}
	
	@Test
	public void persisNewWorkoutMultipleExercises() throws Exception {
		Exercise exercise1 = new Exercise("Push-ups");
		Exercise exercise2 = new Exercise("Jumping Jacks");
		
		Workout workout =  new Workout("Extreme Workout");
		workout.addExercise(exercise1);
		workout.addExercise(exercise2);
		dao.persist(workout);
		
		persistedObjects.add(exercise1);
		persistedObjects.add(exercise2);
		persistedObjects.add(workout);
	}
	
	@Test
	public void removeSingleExerciseFromWorkout() throws Exception {
		Exercise exercise1 = new Exercise("Push-ups");
		Exercise exercise2 = new Exercise("Jumping Jacks");
		
		Workout workout =  new Workout("Extreme Workout");
		workout.addExercise(exercise1);
		workout.addExercise(exercise2);
		dao.persist(workout);
		
		workout.removeExercise(exercise2);
		dao.update(workout);
		assertEquals("Failed to remove exercise from workout", workout.getExercises().size(), 1);
		
		persistedObjects.add(exercise1);
		persistedObjects.add(exercise2);
		persistedObjects.add(workout);
	}
	
	@Test
	public void addWorkoutToCustomer() throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", "ACTIVE");
		Membership membership = (Membership) dao.querySingle("Membership.findType", parameters);
		parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", insurance, newAddress);
		Customer customer = new Customer(personalInformation, membership);
		dao.persist(customer);
		persistedObjects.add(customer);
		persistedObjects.add(insurance);
		
		Exercise exercise1 = new Exercise("Push-ups");
		Exercise exercise2 = new Exercise("Jumping Jacks");
		Workout workout =  new Workout("Extreme Workout");
		workout.addExercise(exercise1);
		workout.addExercise(exercise2);
		dao.persist(workout);
		persistedObjects.add(exercise1);
		persistedObjects.add(exercise2);
		persistedObjects.add(workout);
		
		customer.addWorkout(workout);
		dao.update(customer);
	}
	
	@Test
	public void removeWorkoutFromCustomer() throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", "ACTIVE");
		Membership membership = (Membership) dao.querySingle("Membership.findType", parameters);
		parameters = new HashMap<String, Object>();
		parameters.put("state", "Colorado");
		State colorado = (State) dao.querySingle("State.findState", parameters);
		Address newAddress = new Address("12345 Ave", "My Town", "55555", colorado);
		HealthInsurance insurance = new HealthInsurance("Free Insurance");
		PersonalInformation personalInformation = new PersonalInformation("johndoe@gmail.com", "John", "Doe", "5555555555", insurance, newAddress);
		Customer customer = new Customer(personalInformation, membership);
		dao.persist(customer);
		persistedObjects.add(customer);
		persistedObjects.add(insurance);
		
		Exercise exercise1 = new Exercise("Push-ups");
		Exercise exercise2 = new Exercise("Jumping Jacks");
		Workout workout =  new Workout("Extreme Workout");
		workout.addExercise(exercise1);
		workout.addExercise(exercise2);
		dao.persist(workout);
		persistedObjects.add(exercise1);
		persistedObjects.add(exercise2);
		persistedObjects.add(workout);
		
		customer.addWorkout(workout);
		dao.update(customer);
		
		customer.removeWorkout(workout);
		dao.update(customer);
		assertEquals("Failed to remove workout from customer", customer.getWorkouts().size(), 0);
	}
}
