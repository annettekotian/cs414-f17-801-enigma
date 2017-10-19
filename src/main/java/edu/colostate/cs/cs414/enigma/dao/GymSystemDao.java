package edu.colostate.cs.cs414.enigma.dao;

import java.util.List;
import java.util.Map;

public interface GymSystemDao {

	/**
	 * Persist an object with underlying storage.
	 * @param object Object to be persisted.
	 */
	void persist(Object object);
	
	/**
	 * Remove an object from underlying storage.
	 * @param entity
	 */
	void remove(Object object);
	
	/**
	 * Update an object with the underlying storage.
	 */
	void update(Object object);
	
	/**
	 * Close the connection to the underlying storage.
	 */
	void close();
	
	/**
	 * Issue a query to the database with a set of parameters. The parameters are stored as key
	 * value pair with the value being inserted in the key location of a query.
	 * @param query String containing the query.
	 * @param parameters Mapped list of parameters.
	 * @return List of objects
	 */
	List<?> query(String query, Map<String, Object> parameters);
	
	
	/**
	 * Issue a query to the database with a set of parameters. The parameters are stored as key
	 * value pair with the value being inserted in the key location of a query.
	 * @param query String containing the query.
	 * @param parameters Mapped list of parameters.
	 * @return Single object
	 */
	Object querySingle(String query, Map<String, Object> parameters);
}
