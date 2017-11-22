package edu.colostate.cs.cs414.enigma.handler.builder;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;

import edu.colostate.cs.cs414.enigma.entity.Trainer;

public class TrainerBuilder extends GymSystemUserBuilder {
	
	private Trainer getTrainer(int id) {
		// Get the trainer entity to be updated
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", id);
		
		// Get the trainer
		Trainer trainer = (Trainer) getDao().querySingle("Trainer.findById", trainerParams);
		if(trainer == null) {
			throw new IllegalArgumentException("Trainer does not exist with id " + id);
		}
		return trainer;
	}
	
	public Trainer createTrainer() throws AddressException {
		// Create the new trainer
		Trainer trainer = new Trainer(this.createPersonalInformation(), this.createUser());
		
		// Persist the new trainers information with the db
		getDao().persist(trainer);
		
		return trainer;
	}
	
	public Trainer updateTrainer(int trainerId) throws AddressException {
		return (Trainer) this.updateGymSystemUser(this.getTrainer(trainerId));
	}

	public void deleteTrainer(int trainerId) {
		Trainer trainer = this.getTrainer(trainerId);
		trainer.removeAllWorkHours();
		
		// Delete the trainer
		getDao().remove(trainer);
	}
}