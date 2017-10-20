package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.List;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.State;


public class AddressHandler {

	public List getAllStates() {
		// Open up a connection to the db
				EntityManagerDao dao = new EntityManagerDao();
				
				// Issue a query to get all the customers
				List<State> states = new ArrayList<State>();
				List<?> results = dao.query("State.findAll", null);
				for(int i=0; i<results.size(); i++) {
					states.add((State) results.get(i));
				}

				// Shutdown connection to database
				dao.close();
				
				return states;
	}
	
}
