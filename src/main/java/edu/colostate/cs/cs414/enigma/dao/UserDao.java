package edu.colostate.cs.cs414.enigma.dao;

import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.entity.User;

/**
 * Data access object for the User entity.
 * @author Ian Ziemba
 * @see EntityManagerDao
 * @see User
 */
public class UserDao extends EntityManagerDao<User> {
	
	/**
	 * Get a user by the unique primary ID key from the GymSystem database user table.
	 * @param id Primary ID key.
	 * @return User object on success, else null.
	 * @see User
	 */
	public User findUserById(int id) {
		return this.getEntityManager().find(User.class, id);
	}
	
	/**
	 * Get a user by a unique user name from the GymSystem database user table.
	 * @param userName User name of desired object.
	 * @return User object on success, else null.
	 * @see User
	 */
	public User findUserByUserName(String userName) {
		
		// Issue a NamedQuery found in User.class
		Query query = this.getEntityManager().createNamedQuery("User.findUser");
		query.setParameter("name", userName);
		User user;
		try {
			user = (User) query.getSingleResult();
		} catch(javax.persistence.NoResultException e) {
			user = null;
		}
		return user;
	}
	
	/**
	 * Function to authenticate a a user name and password against the GymSystem database user table.
	 * @param userName User name to be authenticated.
	 * @param password Password to be authenticated.
	 * @return True is authentication is successful, else false.
	 * @see User
	 */
	public boolean authenticateUser(String userName, String password) {
		User user = this.findUserByUserName(userName);
		if(user == null) {
			return false;
		}
		return user.getPassword().equals(password);
	}
}
