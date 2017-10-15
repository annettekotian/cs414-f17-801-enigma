package edu.colostate.cs.cs414.enigma.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.entity.Manager;

public class ManagerDao extends EntityManagerDao<Manager> {
	
	/**
	 * Get a manager by the unique primary ID key from the GymSystem database
	 * manager table. Note that this function will attach the Manager entity with
	 * the underlying entity manager.
	 * @param id Primary ID key.
	 * @return Manager object on success, else null.
	 * @see Manager
	 */
	public Manager findManagerById(int id) {
		return this.getEntityManager().find(Manager.class, id);
	}
	
	/**
	 * Get all the managers in the GymSystem database manager table.
	 * Note that this function will attach the Manager entity with the underlying entity
	 * manager.
	 * @return List of Manager entities.
	 * @see Manager
	 */
	public List<Manager> getManagers() {
		
		// Issue a NamedQuery found in Manager.class
		Query query = this.getEntityManager().createNamedQuery("Manager.findAll");
		List<Manager> managers = new ArrayList<Manager>();
		List<?> results = query.getResultList();
		for(int i=0; i<results.size(); i++) {
			managers.add((Manager) results.get(i));
		}
		return managers;
	}
}
