/**
 * 
 */
package edu.colostate.cs.cs414.enigma.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.UserLevel;

/**
 * Data access object for the HealthInsurance entity.
 * @author Ian Ziemba
 * @see EntityManagerDao
 * @see HealthInsurance
 * */
public class HealthInsuranceDao extends EntityManagerDao<HealthInsurance> {

	/**
	 * Get health insurance by the unique primary ID key from the GymSystem database
	 * health_insurance table. Note that this function will attach the HealthInsurance entity with
	 * the underlying entity manager.
	 * @param id Primary ID key.
	 * @return HealthInsurance object on success, else null.
	 * @see HealthInsurance
	 */
	public HealthInsurance findByHealthInsuranceId(int id) {
		return this.getEntityManager().find(HealthInsurance.class, id);
	}
	
	/**
	 * Get all the health insurances in the GymSystem database health_insurance table.
	 * Note that this function will attach the HealthInsurance entity with the underlying entity
	 * manager.
	 * @return List of HealthInsurance entities.
	 * @see HealthInsurance
	 */
	public List<HealthInsurance> getHealthInsurances() {
		
		// Issue a NamedQuery found in UserLevel.class
		Query query = this.getEntityManager().createNamedQuery("HealthInsurance.findAll");
		List<HealthInsurance> healthInsurances = new ArrayList<HealthInsurance>();
		List<?> results = query.getResultList();
		for(int i=0; i<results.size(); i++) {
			healthInsurances.add((HealthInsurance) results.get(i));
		}
		return healthInsurances;
	}
}
