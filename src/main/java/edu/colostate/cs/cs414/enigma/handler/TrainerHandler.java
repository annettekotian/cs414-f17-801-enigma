package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.PersistenceException;

import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Machine;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.Qualification;
import edu.colostate.cs.cs414.enigma.entity.State;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.User;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;
import edu.colostate.cs.cs414.enigma.entity.WorkHours;
import edu.colostate.cs.cs414.enigma.entity.Workout;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseDurationException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseSetException;
import edu.colostate.cs.cs414.enigma.entity.exception.WorkHoursException;
import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Address;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Exercise;
import edu.colostate.cs.cs414.enigma.entity.ExerciseDuration;
import edu.colostate.cs.cs414.enigma.entity.ExerciseSet;

public class TrainerHandler extends GymSystemHandler {
	
	/**
	 * Manager specific function to create a new trainer.
	 * @param firstName First name of trainer.
	 * @param lastName Last name of the trainer.
	 * @param phoneNumber Phone number of trainer.
	 * @param email Email of trainer.
	 * @param street Street of the trainer.
	 * @param city City of the trainer.
	 * @param state State of the trainer.
	 * @param zipcode Zipcode of the trainer.
	 * @param healthInsurance Health insurance of the trainer.
	 * @param userName Unique user name of the trainer.
	 * @param password Password of the trainer.
	 * @param confirmPassword Password of the trainer.
	 * @return Trainer
	 * @throws PersistenceException
	 */
	public Trainer createNewTrainer(String firstName, String lastName, String phoneNumber, String email, String street,
			String city, String state, String zipcode, String healthInsurance, String userName, String password,
			String confirmPassword) throws PersistenceException, AddressException, IllegalArgumentException {
		
		// Get a state entity/object
		Map<String, Object> stateParams = new HashMap<String, Object>();
		stateParams.put("state", state);
		State stateEntity = (State) getDao().querySingle("State.findState", stateParams);
		
		// Create an address for the new trainer
		Address addressEntity = new Address(street, city, zipcode, stateEntity);
		
		// Attempt to get a healthInsurance object
		Map<String, Object> healthInsuranceParams = new HashMap<String, Object>();
		healthInsuranceParams.put("name", healthInsurance);
		HealthInsurance healthInsuranceEntity = (HealthInsurance) getDao().querySingle("HealthInsurance.findByName", healthInsuranceParams);
		if(healthInsuranceEntity == null) {
			healthInsuranceEntity = new HealthInsurance(healthInsurance);
		}
		
		// Create a personalInformation for the new trainer
		PersonalInformation personalInformationEntity = new PersonalInformation(email, firstName, lastName, phoneNumber,
				healthInsuranceEntity, addressEntity);
		
		// Get the trainer userLevel object for the new trainer
		Map<String, Object> userLevelParams = new HashMap<String, Object>();
		userLevelParams.put("level", "TRAINER");
		UserLevel userLevelEntity = (UserLevel) getDao().querySingle("UserLevel.findLevel", userLevelParams);
		
		// Create a user for the new trainer
		User userEntity = new User(userName, password, userLevelEntity);
		
		// Create the new trainer
		Trainer trainer = new Trainer(personalInformationEntity, userEntity);
		
		// Persist the new trainers information with the db
		getDao().persist(trainer);
		
		return trainer;
	}
	
	/**
	 * Modify a trainer based of a specific trainer ID.
	 * @param id ID of trainer to be modified.
	 * @param firstName Modified first name of trainer.
	 * @param lastName Modified last name of the trainer.
	 * @param phoneNumber Modified phone number of trainer.
	 * @param email Modified email of trainer.
	 * @param street Modified street of the trainer.
	 * @param city Modified city of the trainer.
	 * @param state Modified state of the trainer.
	 * @param zipcode Modified zipcode of the trainer.
	 * @param healthInsurance Modified health insurance of the trainer.
	 * @param userName Modified unique user name of the trainer.
	 * @param password Modified password of the trainer.
	 * @return Trainer
	 * @throws PersistenceException
	 */
	public Trainer modifyTrainer(int id, String firstName, String lastName, String phoneNumber, String email,
			String street, String city, String state, String zipcode, String healthInsurance, String username,
			String password, String confirmPassword) throws PersistenceException, AddressException, IllegalArgumentException {
		
		// Get the trainer entity to be updated
		Trainer trainer = this.getTrainerById(id);
		
		// Need to get or create health insurance object
		Map<String, Object> healthInsuranceParams = new HashMap<String, Object>();
		healthInsuranceParams.put("name", healthInsurance);
		HealthInsurance healthInsuranceEntity = (HealthInsurance) getDao().querySingle("HealthInsurance.findByName", healthInsuranceParams);
		if(healthInsuranceEntity == null) {
			healthInsuranceEntity = new HealthInsurance(healthInsurance);
			getDao().persist(healthInsuranceEntity);
		}
		
		// Need to get state object
		Map<String, Object> stateParams = new HashMap<String, Object>();
		stateParams.put("state", state);
		State stateEntity = (State) getDao().querySingle("State.findState", stateParams);
		
		// Update the trainer entity
		trainer.getPersonalInformation().setFirstName(firstName);
		trainer.getPersonalInformation().setFirstName(lastName);
		trainer.getPersonalInformation().setPhoneNumber(phoneNumber);
		trainer.getPersonalInformation().setEmail(email);
		trainer.getPersonalInformation().getAddress().setStreet(street);
		trainer.getPersonalInformation().getAddress().setCity(city);
		trainer.getPersonalInformation().getAddress().setZipcode(zipcode);
		trainer.getPersonalInformation().getAddress().setState(stateEntity);
		trainer.getPersonalInformation().setHealthInsurance(healthInsuranceEntity);
		trainer.getUser().setUsername(username);
		trainer.getUser().setPassword(password);
		
		// Modify/update the trainers information with the db
		getDao().update(trainer);
		
		return trainer;
	}
	
	private Qualification getQualificationByName(String name) {
		// Attempt to get the Qualification entity from the DB
		Map<String, Object> qualificationParams = new HashMap<String, Object>();
		qualificationParams.put("name", name);
		Qualification qualificationEntity = (Qualification) getDao().querySingle("Qualification.findByName", qualificationParams);
		return qualificationEntity;
	}
	
	public void addQualification(int trainerId, String qualification) {
		
		Qualification qualificationEntity = this.getQualificationByName(qualification);
		if(qualificationEntity == null) {
			qualificationEntity = new Qualification(qualification);
			getDao().persist(qualificationEntity);
		}
		
		// Get the Trainer entity from the DB
		Trainer trainerEntity = this.getTrainerById(trainerId);
		
		// Add the qualification to the trainer
		trainerEntity.addQualification(qualificationEntity);
		getDao().update(trainerEntity);
	}
	
	public void deleteQualification(int trainerId, String qualification) {
		Qualification qualificationEntity = this.getQualificationByName(qualification);
		if(qualificationEntity == null) {
			return;
		}
		
		// Get the Trainer entity from the DB
		Trainer trainerEntity = this.getTrainerById(trainerId);
		trainerEntity.removeQualification(qualificationEntity);
		
		// Update the db
		getDao().update(trainerEntity);
	}
	
	public void addWorkHours(int trainerId, Date startDateTime, Date endDateTime) throws WorkHoursException {
		
		// Create new work hours
		WorkHours workHours = new WorkHours(startDateTime, endDateTime);
		
		// Get the Trainer entity from the DB
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", trainerId);
		Trainer trainerEntity = (Trainer) getDao().querySingle("Trainer.findById", trainerParams);

		trainerEntity.addWorkHours(workHours);
		getDao().update(trainerEntity);
	}
	
	public void deleteWorkHours(int trainerId, int workHoursId) {
		
		// Get the trainer to be removed
		Trainer trainer = this.getTrainerById(trainerId);
		
		// Get the work hours to be remove
		WorkHours workHours = this.getWorkHoursById(workHoursId);
		
		// Remove the workhours
		trainer.removeWorkHours(workHours);
		
		// Update db
		getDao().update(trainer);
	}
	
	/**
	 * Get a specific trainer by the trainer ID. Note that if the trainer does not exist, a null
	 * trainer will be returned.
	 * @param trainerId Trainer ID.
	 * @return Trainer.
	 */
	public Trainer getTrainerById(int trainerId) {		
		// Get a state entity/object
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", trainerId);
		
		// Get the trainer
		Trainer trainer = (Trainer) getDao().querySingle("Trainer.findById", trainerParams);
				
		return trainer;		
	}
	
	/**Given the user id, this method returns the trainer object
	 * 
	 * @param userId
	 * @return: Trainer
	 */
	public Trainer getTrainerByUserId(int userId) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", userId);		
		// Get the trainer
		Trainer trainer = (Trainer) getDao().querySingle("Trainer.findByUserId", params);
				
		return trainer;	
	}
	
	/**
	 * Get all the trainer employees of a gym system. Note that if no trainers exists, a empty
	 * list is returned.
	 * @return List of trainers.
	 */
	public List<Trainer> getAllTrainers() {
		
		// Issue a query to get all the customers
		List<Trainer> trainers = new ArrayList<Trainer>();
		List<?> results = getDao().query("Trainer.findAll", null);
		for(int i=0; i<results.size(); i++) {
			trainers.add((Trainer) results.get(i));
		}
		
		return trainers;
	}

	/**
	 * Return a list of trainers matching a specific search value.
	 * @param value Value each trainer must have.
	 * @return List of trianers.
	 */
	public List<Trainer> searchTrainers(String value) {
		
		// Check to see if the toString() of each trainer contains the desired value
		List<Trainer> trainers = getAllTrainers();
		List<Trainer> matchedTrainers = new ArrayList<Trainer>();
		for(int i=0; i<trainers.size(); i++) {
			Trainer trainer = trainers.get(i);
			if(trainer.searchString().contains(value)) {
				matchedTrainers.add(trainer);
			}
		}
		return matchedTrainers;
	}
	
	/**
	 * Delete a trainer based on a specific trainer ID.
	 * @param id ID of trainer to be deleted.
	 */
	public void deleteTrainer(int id) {
		
		// Get the trainer entity to be updated
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", id);
		Trainer trainer = (Trainer) getDao().querySingle("Trainer.findById", trainerParams);
		trainer.removeAllWorkHours();
		
		// Delete the trainer
		getDao().remove(trainer);
	}
	
	public Exercise createExercise(String name, int machineId, int durationHours, int durationMinutes,
			int durationSeconds, List<Integer> sets) throws PersistenceException, ExerciseDurationException, ExerciseSetException, ExerciseException {
		
		// Create a new exercise
		Exercise exercise = new Exercise(name);
		exercise.setDuration(durationHours, durationMinutes, durationSeconds);
		
		// Only add a machine if the ID is not zero
		if(machineId > 0) {
			Machine machine = this.getMachineById(machineId);
			exercise.setMachine(machine);
		}
		
		// Persist the new exercise in order for any sets to be added
		getDao().persist(exercise);
		
		// Check to see if repetitions need to be added
		exercise.setSets(sets);
		getDao().update(exercise);
		
		return exercise;
	}
	
	public Exercise modifyExercise(int exerciseId, String name, int machineId, int durationHours, int durationMinutes,
			int durationSeconds, List<Integer> sets) throws PersistenceException, ExerciseDurationException, ExerciseSetException, ExerciseException {
		Exercise exercise = this.getExerciseById(exerciseId);
		exercise.setName(name);
		exercise.setDuration(durationHours, durationMinutes, durationSeconds);
		exercise.setSets(sets);
		if(machineId > 0) {
			Machine machine = this.getMachineById(machineId);
			exercise.setMachine(machine);
		}
		getDao().update(exercise);
		return exercise;
	}
	
	public void deleteExercise(int exerciseId) {
		
		// Get the exercise to be deleted
		Exercise exercise = this.getExerciseById(exerciseId);
		if(exercise == null) {
			return;
		}
		
		// Delete the exercise
		getDao().remove(exercise);
	}
	
	public List<Qualification> getAllQualifications() {
		List<?> results = getDao().query("Qualification.findAll", null);
		List<Qualification> qualifications = new ArrayList<Qualification>();
		for(int i=0; i<results.size(); i++) {
			qualifications.add((Qualification) results.get(i));
		}
		return qualifications;
	} 
	
	public List<Exercise> getAllExercises() {
		List<?> results = getDao().query("Exercise.findAll", null);
		List<Exercise> exercises = new ArrayList<Exercise>();
		for(int i=0; i<results.size(); i++) {
			exercises.add((Exercise) results.get(i));
		}
		return exercises;
	}
	
	public List<Exercise> searchExercises(String value) {
		List<?> results = getDao().query("Exercise.findAll", null);
		List<Exercise> exercises = new ArrayList<Exercise>();
		for(int i=0; i<results.size(); i++) {
			Exercise exercise = (Exercise) results.get(i);
			if(exercise.searchString().contains(value)) {
				exercises.add(exercise);
			}
		}
		return exercises;
	}
	
	private Exercise getExerciseById(int exerciseId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", exerciseId);
		Exercise exercise = (Exercise) getDao().querySingle("Exercise.findId", parameters);
		return exercise;
	}
	
	private WorkHours getWorkHoursById(int workHoursId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", workHoursId);
		WorkHours workHours = (WorkHours) getDao().querySingle("WorkHours.findById", parameters);
		return workHours;
	}
	
	private Machine getMachineById(int machineId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", machineId);
		Machine machine = (Machine) getDao().querySingle("Machine.findId", parameters);
		return machine;
	}
	
	/**
	 * 
	 * @param name
	 * @param exerciseNames
	 * @return
	 * @throws ExerciseException
	 */
	public Workout createWorkout(String name, String[] exerciseNames) throws ExerciseException {
		
		//validations
		if(name.isEmpty() || exerciseNames.length == 0) {
			throw new IllegalArgumentException("missing input");
		}
		
		ArrayList<Exercise> exList = new ArrayList<Exercise>();
		for (String exName : exerciseNames) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", exName );
			Exercise ex = (Exercise) getDao().querySingle("Exercise.findByName", map);
			exList.add(ex);
		}
		
		
		Workout w = new Workout(name);
		w.setExercises(exList);
		getDao().persist(w);
		
		return w;
		
		
	}
	
	/** This method returns all the workouts in the system
	 * 
	 * @return
	 */
	public List<Workout> getAllWorkouts() {
		List<?> results = getDao().query("Workout.findAll", null);
		List<Workout> wList= new ArrayList<Workout>();
		for(int i=0; i<results.size(); i++) {
			wList.add((Workout) results.get(i));
		}
		return wList;
	}
	
	/** Given the id, this method updates a workout
	 * @param workoutId: String
	 * @param name: String
	 * @param exerciseIds: String[], array of exercise names
	 * @return
	 * @throws ExerciseException
	 */
	public Workout updateWorkout(String workoutId, String name, String[] exerciseNames) throws ExerciseException {
		
		//validations
		if(workoutId.isEmpty() || name.isEmpty() || exerciseNames == null || exerciseNames.length == 0) {
			throw new IllegalArgumentException("missing input");
		}
		
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("id", Integer.parseInt(workoutId));
		Workout w = (Workout) getDao().querySingle("Workout.findId", map1);
		
		
		ArrayList<Exercise> exList = new ArrayList<Exercise>();
		for (String exName : exerciseNames) {
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put("name", exName );
			Exercise ex = (Exercise) getDao().querySingle("Exercise.findByName", map2);
			exList.add(ex);
		}
		
		
		w.setName(name);
		w.setExercises(exList);
		getDao().update(w);
		
		return w;
	}
	
	/**
	 * Given a keyword this method returns a list of workouts matching the keyword
	 * @param keyword: String 
	 * @return List<Workout>
	 */
	public List<Workout> searchWorkouts(String keyword) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("keyword", "%" + keyword + "%");
		List<?> results = getDao().query("Workout.findByKeyword", params);
		List<Workout> wList= new ArrayList<Workout>();
		for(int i=0; i<results.size(); i++) {
			wList.add((Workout) results.get(i));
		}
		return wList;
	}
	
	public void deleteWorkout(String workoutId) {
		if(workoutId.isEmpty()) {
			throw new IllegalArgumentException("missing id");
		}
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("id", Integer.parseInt(workoutId));
		Workout w = (Workout) getDao().querySingle("Workout.findId", map1);
		List<Exercise> exList = w.getExercises();
		exList.clear();
		w.setExercises(exList);
		getDao().update(w);
		getDao().remove(w);
	}
		
	public void assignWorkout(int customerId, int workoutId) throws IllegalArgumentException {
		
		// Get the customer and the workout entity
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("id", customerId);
		Customer customer = (Customer) getDao().querySingle("Customer.findById", customerParams);
		if(customer == null) {
			throw new IllegalArgumentException("Customer does not exist");
		}
		
		Map<String, Object> workoutParams = new HashMap<String, Object>();
		workoutParams.put("id", workoutId);
		Workout workout = (Workout) getDao().querySingle("Workout.findId", workoutParams);
		if(workout == null) {
			throw new IllegalArgumentException("Workout does not exist");
		}
		
		// Assign (add) the customer the workout
		customer.addWorkout(workout);
		
		// Persist the changes
		getDao().update(customer);
	}
	
	public void unassignWorkout(int customerId, int workoutId) throws IllegalArgumentException {
		
		// Get the customer and the workout entity
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("id", customerId);
		Customer customer = (Customer) getDao().querySingle("Customer.findById", customerParams);
		if(customer == null) {
			throw new IllegalArgumentException("Customer does not exist");
		}
		
		Map<String, Object> workoutParams = new HashMap<String, Object>();
		workoutParams.put("id", workoutId);
		Workout workout = (Workout) getDao().querySingle("Workout.findId", workoutParams);
		if(workout == null) {
			throw new IllegalArgumentException("Workout does not exist");
		}
		
		// Unassign (remove) the customer the workout
		customer.removeWorkout(workout);
		
		// Persist the changes
		getDao().update(customer);

	}
	
	public List<Workout> getWorkoutsByCustomerId(int customerId) {
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("id", customerId);
		Customer customer = (Customer) getDao().querySingle("Customer.findById", customerParams);
		if(customer == null) {
			throw new IllegalArgumentException("Customer does not exist");
		}
		return customer.getWorkouts();
	}
}
