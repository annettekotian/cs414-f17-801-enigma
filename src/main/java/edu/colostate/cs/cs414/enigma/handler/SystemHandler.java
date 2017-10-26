package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.List;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.State;

public class SystemHandler {
	EntityManagerDao dao;

	public SystemHandler() {
		dao = new EntityManagerDao();
	}
	
	private void close() {
		dao.close();
	}


	public List getAllStates() {
		// Open up a connection to the db
		dao = new EntityManagerDao();

		// Issue a query to get all the customers
		List<State> states = new ArrayList<State>();
		List<?> results = dao.query("State.findAll", null);
		for (int i = 0; i < results.size(); i++) {
			states.add((State) results.get(i));
		}

		// Shutdown connection to database
		close();

		return states;
	}

	public List<HealthInsurance> getHealthInsurances() {
		List rawHealthInsurance = dao.query("HealthInsurance.findAll", null);
		List<HealthInsurance> insurances = new ArrayList<HealthInsurance>();
		for (int i = 0; i < rawHealthInsurance.size(); i++) {
			insurances.add((HealthInsurance) rawHealthInsurance.get(i));
		}

		close();
		return insurances;
	}
	
	
	public List<Membership> getMembershipTypes() {
		List rawMemberships = dao.query("Membership.findAll", null);
		List<Membership> memberships = new ArrayList<Membership>();
		for(int i=0; i<rawMemberships.size(); i++) {
			memberships.add((Membership) rawMemberships.get(i));
		}
		close();
		return memberships;
	}

	
}
