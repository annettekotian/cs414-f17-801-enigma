package edu.colostate.cs.cs414.enigma.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
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

	/**
	 * Persist an entity with the entity manager. Note that the entity will not be written to the
	 * database until close() or commit() is called.
	 * @param entity
	 */
	@Override
	public void persist(T entity) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException {
		this.em.persist(entity);
		this.em.flush();
	}

	/**
	 * Remove an entity from the database. If entity is not attached to the underlying entity,
	 * manager an IllegalArgumentException with be thrown. Note that the entity will not be
	 * removed from the database until close() or commit() is called.
	 * @param entity
	 */
	@Override
	public void remove(T entity) throws IllegalArgumentException, TransactionRequiredException {
		this.em.remove(entity);
	}
	
	/**
	 * Attach a UserLevel entity with the underlying entity manager.
	 * @param entity
	 */
	@Override
	public T attach(T entity) throws IllegalArgumentException, TransactionRequiredException {
		return this.em.merge(entity);
	}
	
	public EntityManager getEntityManager() {
		return this.em;
	}
}
