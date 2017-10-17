package edu.colostate.cs.cs414.enigma.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Membership;

/**
 * Data access object for the Membership entity.
 * @author Ian Ziemba
 * @see EntityManagerDao
 * @see Membership
 * */
public class MembershipDao extends EntityManagerDao<Membership> {
	
	/**
	 * Get membership by the unique primary ID key from the GymSystem database
	 * membership table. Note that this function will attach the Membership entity with
	 * the underlying entity manager.
	 * @param id Primary ID key.
	 * @return Membership object on success, else null.
	 * @see Membership
	 */
	public Membership findByMembershipId(int id) {
		return this.getEntityManager().find(Membership.class, id);
	}
	
	/**
	 * Get all the memberships in the GymSystem database membership table.
	 * Note that this function will attach the Membership entity with the underlying entity
	 * manager.
	 * @return List of Membership entities.
	 * @see Membership
	 */
	public List<Membership> getMembershipStatus() {
		
		// Issue a NamedQuery found in UserLevel.class
		Query query = this.getEntityManager().createNamedQuery("Membership.findAll");
		List<Membership> memberships = new ArrayList<Membership>();
		List<?> results = query.getResultList();
		for(int i=0; i<results.size(); i++) {
			memberships.add((Membership) results.get(i));
		}
		return memberships;
	}
}
