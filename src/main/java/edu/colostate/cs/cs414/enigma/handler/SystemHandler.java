package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Machine;
import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.entity.State;

public class SystemHandler {
	EntityManagerDao dao;

	public SystemHandler() {
		dao = new EntityManagerDao();
	}
	
	/**this methods shuts down the connection to the database
	 *  
	 */
	private void close() {
		dao.close();
	}

	
	/** this method returns all the states from the states table
	 * 
	 * @return List<State>
	 */
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

	/** This method returns all the health insurances from the HealthInsurance table
	 * 
	 * @return
	 */
	public List<HealthInsurance> getHealthInsurances() {
		List rawHealthInsurance = dao.query("HealthInsurance.findAll", null);
		List<HealthInsurance> insurances = new ArrayList<HealthInsurance>();
		for (int i = 0; i < rawHealthInsurance.size(); i++) {
			insurances.add((HealthInsurance) rawHealthInsurance.get(i));
		}

		close();
		return insurances;
	}
	
	/** This method gets all the memberships from the membership table
	 * 
	 * @return
	 */
	public List<Membership> getMembershipTypes() {
		List rawMemberships = dao.query("Membership.findAll", null);
		List<Membership> memberships = new ArrayList<Membership>();
		for(int i=0; i<rawMemberships.size(); i++) {
			memberships.add((Membership) rawMemberships.get(i));
		}
		close();
		return memberships;
	}

	public List<Machine> getInventory(){

		List rawMachines = dao.query("Machine.findAll", null);
		List<Machine> machines = new ArrayList<Machine>();
		for(int i=0; i<rawMachines.size(); i++) {
			machines.add((Machine) rawMachines.get(i));
		}
		close();
		return machines;
	}
	
	
	public List<Machine> searchByKeyword(String keyword){
		dao = new EntityManagerDao();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("keyword", "%" + keyword + "%");
		//System.out.println("here 1");
		List rawMachines = dao.query("Machine.findByKeyword", parameters);
		//System.out.println("here 2");
		List<Machine> machines = new ArrayList<Machine>();
		for(int i=0; i<rawMachines.size(); i++) {
			machines.add((Machine) rawMachines.get(i));
		}
		close();
		return machines;
	}
	
	
}
