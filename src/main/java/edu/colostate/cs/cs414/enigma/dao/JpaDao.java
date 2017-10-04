package edu.colostate.cs.cs414.enigma.dao;

/**
 * Interface for implementing a JPA data access object.
 * @author Ian Ziemba
 *
 * @param <T> Entity object type.
 */
public interface JpaDao<T> {
	
	/**
	 * Persist a new entity with a database.
	 * @param entity
	 */
	void persist(T entity);
	
	/**
	 * Remove a entity from a database.
	 * @param entity
	 */
	void remove(T entity);
}