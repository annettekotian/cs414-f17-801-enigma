package edu.colostate.cs.cs414.enigma.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.entity.Trainer;

public class TrainerDao extends EntityManagerDaoOld<Trainer> {

	/**
	 * Get a trainer by the unique primary ID key from the GymSystem database
	 * trainer table. Note that this function will attach the Manager entity with
	 * the underlying entity manager.
	 * @param id Primary ID key.
	 * @return Trainer object on success, else null.
	 * @see Trainer
	 */
	public Trainer findTrainerById(int id) {
		return this.getEntityManager().find(Trainer.class, id);
	}
	
	/**
	 * Get all the trainers in the GymSystem database trainer table.
	 * Note that this function will attach the Trainer entity with the underlying entity
	 * manager.
	 * @return List of Trainer entities.
	 * @see Trainer
	 */
	public List<Trainer> getTrainers() {
		
		// Issue a NamedQuery found in Trainer.class
		Query query = this.getEntityManager().createNamedQuery("Trainer.findAll");
		List<Trainer> trainers = new ArrayList<Trainer>();
		List<?> results = query.getResultList();
		for(int i=0; i<results.size(); i++) {
			trainers.add((Trainer) results.get(i));
		}
		return trainers;
	}	
}
