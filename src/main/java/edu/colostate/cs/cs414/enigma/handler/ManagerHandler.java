package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.List;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Trainer;

public class ManagerHandler {
	
	public static List<Trainer> getAllTrainers() {
		
		// Open up a connection to the db
		EntityManagerDao dao = new EntityManagerDao();
		
		// Issue a query to get all the customers
		List<Trainer> trainers = new ArrayList<Trainer>();
		List<?> results = dao.query("Trainer.findAll", null);
		for(int i=0; i<results.size(); i++) {
			trainers.add((Trainer) results.get(i));
		}

		// Shutdown connection to database
		dao.close();
		
		return trainers;
	}

}
