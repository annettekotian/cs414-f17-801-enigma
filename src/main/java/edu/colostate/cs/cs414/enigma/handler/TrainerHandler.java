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
import edu.colostate.cs.cs414.enigma.entity.Exercise;
import edu.colostate.cs.cs414.enigma.entity.ExerciseDuration;
import edu.colostate.cs.cs414.enigma.entity.ExerciseSet;

public class TrainerHandler {
	
	private EntityManagerDao dao;
	
	public TrainerHandler() {
		this.dao = new EntityManagerDao();
	}
	
	public void close() {
		this.dao.close();
	}
	
	private void validateTrainerInformation(String firstName, String lastName, String phoneNumber, String email, String street,
			String city, String state, String zipcode, String healthInsurance, String userName, String password, String confirmPassword) throws AddressException, IllegalArgumentException {
		
		if(email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || healthInsurance.isEmpty() || userName.isEmpty()
				|| password.isEmpty() || street.isEmpty() || city.isEmpty() || zipcode.isEmpty() || state.isEmpty()) {			
			throw new IllegalArgumentException("Missing Input");
		}
		if(password.length() < 0) {
			throw new IllegalArgumentException("Password must be 8 characters");
		}
		if(!password.equals(confirmPassword)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		if(!zipcode.matches("^[0-9]{5}$")) {
			throw new IllegalArgumentException("Zipcode must be 5 digits");
		}
		if(!phoneNumber.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$")) {
			throw new IllegalArgumentException("Phone number must be 10 digits in format ###-###-####");
		}
		new InternetAddress(email).validate();
	}
	
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
		
		// Validate parameters
		validateTrainerInformation(firstName, lastName, phoneNumber, email, street, city, state, zipcode,
				healthInsurance, userName, password, confirmPassword);
		
		// Get a state entity/object
		Map<String, Object> stateParams = new HashMap<String, Object>();
		stateParams.put("state", state);
		State stateEntity = (State) dao.querySingle("State.findState", stateParams);
		
		// Create an address for the new trainer
		Address addressEntity = new Address(street, city, zipcode, stateEntity);
		
		// Attempt to get a healthInsurance object
		Map<String, Object> healthInsuranceParams = new HashMap<String, Object>();
		healthInsuranceParams.put("name", healthInsurance);
		HealthInsurance healthInsuranceEntity = (HealthInsurance) dao.querySingle("HealthInsurance.findByName", healthInsuranceParams);
		if(healthInsuranceEntity == null) {
			healthInsuranceEntity = new HealthInsurance(healthInsurance);
		}
		
		// Create a personalInformation for the new trainer
		PersonalInformation personalInformationEntity = new PersonalInformation(email, firstName, lastName, phoneNumber,
				healthInsuranceEntity, addressEntity);
		
		// Get the trainer userLevel object for the new trainer
		Map<String, Object> userLevelParams = new HashMap<String, Object>();
		userLevelParams.put("level", "TRAINER");
		UserLevel userLevelEntity = (UserLevel) dao.querySingle("UserLevel.findLevel", userLevelParams);
		
		// Create a user for the new trainer
		User userEntity = new User(userName, password, userLevelEntity);
		
		// Create the new trainer
		Trainer trainer = new Trainer(personalInformationEntity, userEntity);
		
		// Persist the new trainers information with the db
		dao.persist(trainer);
		
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
			String street, String city, String state, String zipcode, String healthInsurance, String userName,
			String password, String confirmPassword) throws PersistenceException, AddressException, IllegalArgumentException {
	
		// Validate parameters
		validateTrainerInformation(firstName, lastName, phoneNumber, email, street, city, state, zipcode,
						healthInsurance, userName, password, confirmPassword);
		
		// Get the trainer entity to be updated
		Trainer trainer = this.getTrainerById(id);
		
		// Update the trainers information that does not require a new object
		PersonalInformation personalInformation = trainer.getPersonalInformation();
		personalInformation.setFirstName(firstName);
		personalInformation.setLastName(lastName);
		personalInformation.setEmail(email);
		personalInformation.setPhoneNumber(phoneNumber);
		if(!personalInformation.getHealthInsurance().getName().equals(healthInsurance)) {
			Map<String, Object> healthInsuranceParams = new HashMap<String, Object>();
			healthInsuranceParams.put("name", healthInsurance);
			HealthInsurance healthInsuranceEntity = (HealthInsurance) dao.querySingle("HealthInsurance.findByName", healthInsuranceParams);
			if(healthInsuranceEntity == null) {
				healthInsuranceEntity = new HealthInsurance(healthInsurance);
				dao.persist(healthInsuranceEntity);
			}
			personalInformation.setHealthInsurance(healthInsuranceEntity);
		}
		
		Address address = personalInformation.getAddress();
		address.setCity(city);
		address.setStreet(street);
		address.setZipcode(zipcode);
		if(!address.getState().getState().equals(state)) {
			Map<String, Object> stateParams = new HashMap<String, Object>();
			stateParams.put("state", state);
			State stateEntity = (State) dao.querySingle("State.findState", stateParams);
			address.setState(stateEntity);
		}
		
		User user = trainer.getUser();
		user.setUsername(userName);
		user.setPassword(password);
		
		
		// Modify/update the trainers information with the db
		dao.update(trainer);
		
		return trainer;
	}
	
	private Qualification getQualificationByName(String name) {
		// Attempt to get the Qualification entity from the DB
		Map<String, Object> qualificationParams = new HashMap<String, Object>();
		qualificationParams.put("name", name);
		Qualification qualificationEntity = (Qualification) dao.querySingle("Qualification.findByName", qualificationParams);
		return qualificationEntity;
	}
	
	public void addQualification(int trainerId, String qualification) {
		
		Qualification qualificationEntity = this.getQualificationByName(qualification);
		if(qualificationEntity == null) {
			qualificationEntity = new Qualification(qualification);
			dao.persist(qualificationEntity);
		}
		
		// Get the Trainer entity from the DB
		Trainer trainerEntity = this.getTrainerById(trainerId);
		
		// Add the qualification to the trainer
		trainerEntity.addQualification(qualificationEntity);
		dao.update(trainerEntity);
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
		dao.update(trainerEntity);
	}
	
	public void addWorkHours(int trainerId, Date startDateTime, Date endDateTime) throws WorkHoursException {
		
		// Verify the start date time is before the end date time
		if(startDateTime.compareTo(endDateTime) > 0) {
			throw new WorkHoursException("Start date time cannot be after end data time");
		}
		
		// Verify the start date time is not before the current date time
		Date currentDateTime = new Date();
		if(startDateTime.compareTo(currentDateTime) < 0) {
			throw new WorkHoursException("Start date time cannot occur in the past");
		}
		
		// Create new work hours
		WorkHours workHours = new WorkHours(startDateTime, endDateTime);
		
		// Get the Trainer entity from the DB
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", trainerId);
		Trainer trainerEntity = (Trainer) dao.querySingle("Trainer.findById", trainerParams);

		trainerEntity.addWorkHours(workHours);
		dao.update(trainerEntity);
	}
	
	public void deleteWorkHours(int trainerId, int workHoursId) {
		
		// Get the trainer to be removed
		Trainer trainer = this.getTrainerById(trainerId);
		
		// Get the work hours to be remove
		WorkHours workHours = this.getWorkHoursById(workHoursId);
		
		// Remove the workhours
		trainer.removeWorkHours(workHours);
		
		// Update db
		dao.update(trainer);
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
		Trainer trainer = (Trainer) dao.querySingle("Trainer.findById", trainerParams);
				
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
		Trainer trainer = (Trainer) dao.querySingle("Trainer.findByUserId", params);
				
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
		List<?> results = dao.query("Trainer.findAll", null);
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
		Trainer trainer = (Trainer) dao.querySingle("Trainer.findById", trainerParams);
		trainer.removeAllWorkHours();
		
		// Delete the trainer
		dao.remove(trainer);
	}
	
	public Exercise createExercise(String name, int machineId, int durationHours, int durationMinutes,
			int durationSeconds, List<Integer> sets) throws PersistenceException, ExerciseDurationException, ExerciseSetException, ExerciseException {
		
		// Create a new exercise
		Exercise exercise = new Exercise(name);
		
		// Only add a duration if hours, minutes, and seconds are non-null
		if(durationHours != 0 || durationMinutes != 0 || durationSeconds != 0) {
			ExerciseDuration duration = new ExerciseDuration(durationHours, durationMinutes, durationSeconds);
			exercise.setDuration(duration);
		}
		
		// Only add a machine if the ID is not zero
		if(machineId > 0) {
			Machine machine = this.getMachineById(machineId);
			exercise.setMachine(machine);
		}
		
		// Persist the new exercise
		dao.persist(exercise);
		
		// Check to see if repetitions need to be added
		for(int i=0; i<sets.size(); i++) {
			ExerciseSet set = new ExerciseSet(sets.get(i));
			exercise.addSet(set);
		}
		if(sets.size() > 0) {
			dao.update(exercise);
		}
		
		return exercise;
	}
	
	public Exercise modifyExercise(int exerciseId, String name, int machineId, int durationHours, int durationMinutes,
			int durationSeconds, List<Integer> sets) throws PersistenceException, ExerciseDurationException, ExerciseSetException, ExerciseException {
		
		// Get the exercise to be modified
		Exercise exercise = this.getExerciseById(exerciseId);
		
		// Check if name needs to be update
		if(!exercise.getName().equals(name)) {
			exercise.setName(name);
		}
		
		// Check if the duration needs to be modified
		ExerciseDuration duration = exercise.getDuration();
		if(durationHours != 0 || durationMinutes != 0 || durationSeconds != 0) {
			if(duration == null) {
				duration = new ExerciseDuration(durationHours, durationMinutes, durationSeconds);
				exercise.setDuration(duration);
			} else {
				if(duration.getHours() != durationHours) {
					duration.setHours(durationHours);
				}
				if(duration.getMinutes() != durationMinutes) {
					duration.setMinutes(durationMinutes);
				}
				if(duration.getSeconds() != durationSeconds) {
					duration.setSeconds(durationSeconds);
				}
			}
		} else {
			if(duration != null) {
				exercise.deleteDuration();
			}
		}
		
		// Check if machine needs to be updated
		Machine machine = exercise.getMachine();
		if(machine == null) {
			if(machineId > 0) {
				machine = this.getMachineById(machineId);
				exercise.setMachine(machine);
			}
		} else {
			if(machine.getId() != machineId) {
				machine = this.getMachineById(machineId);
				exercise.setMachine(machine);
			}
		}		
		
		// Check to see if the sets are the same (order and repetition count)
		boolean rebuildSets = false;
		if(exercise.getSets().size() == sets.size()) {
			for(int i=0; i<exercise.getSets().size(); i++) {
				if(exercise.getSets().get(i).getRepetitions() != sets.get(i)) {
					rebuildSets = true;
				}
			}
		} else if(sets.size() != 0) {
			rebuildSets = true;
		} else {
			exercise.deleteSets();
		}
		
		if(rebuildSets) {
			exercise.deleteSets();
			dao.update(exercise);
			for(int i=0; i<sets.size(); i++) {
				ExerciseSet set = new ExerciseSet(sets.get(i));
				exercise.addSet(set);
			}
		}
		
		// Commit the changes
		dao.update(exercise);
		
		return exercise;
	}
	
	public void deleteExercise(int exerciseId) {
		
		// Get the exercise to be deleted
		Exercise exercise = this.getExerciseById(exerciseId);
		if(exercise == null) {
			return;
		}
		
		// All sets need to be deleted before removing the exercise
		if(exercise.getSets().size() > 0) {
			exercise.deleteSets();
			dao.update(exercise);
		}
		
		// Delete the exercise
		dao.remove(exercise);
	}
	
	public List<Qualification> getAllQualifications() {
		List<?> results = dao.query("Qualification.findAll", null);
		List<Qualification> qualifications = new ArrayList<Qualification>();
		for(int i=0; i<results.size(); i++) {
			qualifications.add((Qualification) results.get(i));
		}
		return qualifications;
	} 
	
	public List<Exercise> getAllExercises() {
		List<?> results = dao.query("Exercise.findAll", null);
		List<Exercise> exercises = new ArrayList<Exercise>();
		for(int i=0; i<results.size(); i++) {
			exercises.add((Exercise) results.get(i));
		}
		return exercises;
	}
	
	public List<Exercise> searchExercises(String value) {
		List<?> results = dao.query("Exercise.findAll", null);
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
		Exercise exercise = (Exercise) dao.querySingle("Exercise.findId", parameters);
		return exercise;
	}
	
	private WorkHours getWorkHoursById(int workHoursId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", workHoursId);
		WorkHours workHours = (WorkHours) dao.querySingle("WorkHours.findById", parameters);
		return workHours;
	}
	
	private Machine getMachineById(int machineId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", machineId);
		Machine machine = (Machine) dao.querySingle("Machine.findId", parameters);
		return machine;
	}
	
	/**
	 * 
	 * @param name
	 * @param exerciseIds
	 * @return
	 * @throws ExerciseException
	 */
	public Workout createWorkout(String name, String[] exerciseIds) throws ExerciseException {
		
		//validations
		if(name.isEmpty() || exerciseIds.length == 0) {
			throw new IllegalArgumentException("missing input");
		}
		
		ArrayList<Exercise> exList = new ArrayList<Exercise>();
		for (String exName : exerciseIds) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", exName );
			Exercise ex = (Exercise) dao.querySingle("Exercise.findByName", map);
			exList.add(ex);
		}
		
		
		Workout w = new Workout(name);
		w.setExercises(exList);
		dao.persist(w);
		
		return w;
		
		
	}
	
	public List<Workout> getAllWorkouts() {
		List<?> results = dao.query("Workout.findAll", null);
		List<Workout> wList= new ArrayList<Workout>();
		for(int i=0; i<results.size(); i++) {
			wList.add((Workout) results.get(i));
		}
		return wList;
	}
}
