package edu.colostate.cs.cs414.enigma.dao;

import java.util.List;

import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.entity.UserLevel;

public class UserLevelDao extends EntityManagerDao<UserLevel> {
	
	public UserLevel findUserLevelById(int id) {
		return this.getEntityManager().find(UserLevel.class, id);
	}
	
	public List<UserLevel> getUserLevels() {
		Query query = this.getEntityManager().createNamedQuery("UserLevel.findAll");
		return query.getResultList();
	}
	
	public UserLevel findUser(String level) {
		Query query = this.getEntityManager().createNamedQuery("UserLevel.findLevel");
		query.setParameter("level", level);
		UserLevel userLevel;
		try {
			userLevel = (UserLevel) query.getSingleResult();
		} catch(javax.persistence.NoResultException e) {
			userLevel = null;
		}
		return userLevel;
	}

}
