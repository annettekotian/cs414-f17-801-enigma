package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;
import edu.colostate.cs.cs414.enigma.entity.Qualification;
import edu.colostate.cs.cs414.enigma.entity.Trainer;
import edu.colostate.cs.cs414.enigma.entity.WorkHours;
import edu.colostate.cs.cs414.enigma.entity.WorkHoursException;
import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;

public class TrainerHandler {
	
	private EntityManagerDao dao;
	
	public TrainerHandler() {
		this.dao = new EntityManagerDao();
	}
	
	public void close() {
		this.dao.close();
	}
	
	public void addQualification(int trainerId, String qualification) {
		
		// Attempt to get the Qualification entity from the DB
		Map<String, Object> qualificationParams = new HashMap<String, Object>();
		qualificationParams.put("name", qualification);
		Qualification qualificationEntity = (Qualification) dao.querySingle("Qualification.findByName", qualificationParams);
		if(qualificationEntity == null) {
			qualificationEntity = new Qualification(qualification);
			dao.persist(qualificationEntity);
		}
		
		// Get the Trainer entity from the DB
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", trainerId);
		Trainer trainerEntity = (Trainer) dao.querySingle("Trainer.findById", trainerParams);
		
		// Add the qualification to the trainer
		trainerEntity.addQualification(qualificationEntity);
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
			throw new WorkHoursException("Stat date time cannot occur in the past");
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
}
