package edu.colostate.cs.cs414.enigma.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

import edu.colostate.cs.cs414.enigma.listeners.EntityManagerFactoryListener;

/**
 * Data access object for an entity class manager by a JPA entity manager.
 * @author Ian Ziemba
 *
 * @param <T> Entity object type.
 * @see JpaDao
 */
public abstract class EntityManagerDao<T> implements JpaDao<T> {
	
	private EntityManager em;
	
	/**
	 * Initialize the entity manager.
	 */
	public EntityManagerDao() {
		em = EntityManagerFactoryListener.createEntityManager();
		em.getTransaction().begin();
	}
	
	/**
	 * Commit all transactions to the database and close the entity manager.
	 */
	public void close() {
		this.em.getTransaction().commit();
		this.em.close();
	}
	
	/**
	 * Commit all current transactions to the database and start new transactions.
	 */
	public void commit() {
		this.em.getTransaction().commit();
		this.em.getTransaction().begin();
	}


	@Override
	public void persist(T entity) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException {
		this.em.persist(entity);
		this.em.flush();
	}

	@Override
	public void remove(T entity) throws IllegalArgumentException, TransactionRequiredException {
		this.em.remove(entity);
	}
	
	public EntityManager getEntityManager() {
		return this.em;
	}
}
