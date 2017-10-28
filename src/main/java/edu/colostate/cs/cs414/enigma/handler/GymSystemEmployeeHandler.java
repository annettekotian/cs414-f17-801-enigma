package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Trainer;

/**
 * Abstract class used to provide functionality for all gym system employees.
 */
public abstract class GymSystemEmployeeHandler {
	
	private EntityManagerDao dao;
	
	public GymSystemEmployeeHandler() {
		dao = new EntityManagerDao();
	}
	
	public void close() {
		dao.close();
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
	
	
}
