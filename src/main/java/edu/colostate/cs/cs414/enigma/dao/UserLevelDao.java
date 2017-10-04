package edu.colostate.cs.cs414.enigma.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.entity.UserLevel;

/**
 * Data access object for the UserLevel entity.
 * @author Ian Ziemba
 * @see EntityManagerDao
 * @see UserLevel
 */
public class UserLevelDao extends EntityManagerDao<UserLevel> {
	
	/**
	 * Get a user level by the unique primary ID key from the GymSystem database user_level table.
	 * Note that this function will attach the UserLevel entity with the underlying entity
	 * manager.
	 * @param id Primary ID key.
	 * @return User object on success, else null.
	 * @see UserLevel
	 */
	public UserLevel findUserLevelById(int id) {
		return this.getEntityManager().find(UserLevel.class, id);
	}
	
	/**
	 * Get all the user level in the GymSystem database user_level table.
	 * Note that this function will attach the UserLevel entity with the underlying entity
	 * manager.
	 * @return List of UserLevel entities.
	 * @see UserLevel
	 */
	public List<UserLevel> getUserLevels() {
		
		// Issue a NamedQuery found in UserLevel.class
		Query query = this.getEntityManager().createNamedQuery("UserLevel.findAll");
		List<UserLevel> userLevels = new ArrayList<UserLevel>();
		List<?> results = query.getResultList();
		for(int i=0; i<results.size(); i++) {
			userLevels.add((UserLevel) results.get(i));
		}
		return userLevels;
	}
	
	/**
	 * Get a UserLevel entity based on a description of the level.
	 * Note that this function will attach the UserLevel entity with the underlying entity
	 * manager.
	 * @param description Description of the level.
	 * @return UserLevel object on success, else null.
	 * @see UserLevel
	 */
	public UserLevel findUserLevelByDescription(String description) {
		Query query = this.getEntityManager().createNamedQuery("UserLevel.findLevel");
		query.setParameter("level", description);
		UserLevel userLevel;
		try {
			userLevel = (UserLevel) query.getSingleResult();
		} catch(javax.persistence.NoResultException e) {
			userLevel = null;
		}
		return userLevel;
	}

}
