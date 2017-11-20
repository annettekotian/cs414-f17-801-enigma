package edu.colostate.cs.cs414.enigma.builder;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;

import edu.colostate.cs.cs414.enigma.entity.Trainer;

public class TrainerBuilder extends GymSystemUserBuilder {
	
	public Trainer createTrainer() throws AddressException {
		// Create the new trainer
		Trainer trainer = new Trainer(this.createPersonalInformation(), this.createUser());
		
		// Persist the new trainers information with the db
		getDao().persist(trainer);
		
		return trainer;
	}
	
	public Trainer modifyTrainer() throws AddressException {
		// Get the trainer entity to be updated
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", this.id);
		
		// Get the trainer
		Trainer trainer = (Trainer) getDao().querySingle("Trainer.findById", trainerParams);
		if(trainer == null) {
			return null;
		}
		
		return (Trainer) this.modifyGymSystemUser(trainer);
	}

	public void deleteTrainer() {
		
		// Get the trainer entity to be updated
		Map<String, Object> trainerParams = new HashMap<String, Object>();
		trainerParams.put("id", this.id);
		Trainer trainer = (Trainer) getDao().querySingle("Trainer.findById", trainerParams);
		trainer.removeAllWorkHours();
		
		// Delete the trainer
		getDao().remove(trainer);
	}
}