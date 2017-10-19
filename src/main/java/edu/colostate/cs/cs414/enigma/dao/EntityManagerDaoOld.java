package edu.colostate.cs.cs414.enigma.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

/**
 * Data access object for an entity class manager by a JPA entity manager.
 * @author Ian Ziemba
 *
 * @param <T> Entity object type.
 * @see JpaDao
 */
public abstract class EntityManagerDaoOld<T> implements JpaDao<T> {
	
	private EntityManager em;
	
	/**
	 * Initialize the entity manager.
	 */
	public EntityManagerDaoOld() {
		em = EntityManagerFactoryListener.createEntityManager();
		em.getTransaction().begin();
	}
	
	/**
	 * Commit all transactions to the database and close the entity manager.
	 */
	public void close() throws IllegalStateException {
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
	 * Persist an entity with the entity manager. In the case where the entity cannot be persisted
	 * (a PersistenceException occurs), the current transaction with the entity manager will be
	 * rolled back, and a new transaction started. Note that the entity will not be written to the
	 * database until close() or commit() is called.
	 * @param entity
	 */
	@Override
	public void persist(T entity) throws PersistenceException {
		try {
			this.em.persist(entity);
			this.em.flush();	
		} catch(PersistenceException e) {
			this.em.getTransaction().rollback();
			this.em.getTransaction().begin();
			throw e;
		}
	}

	/**
	 * Remove an entity from the database. If entity is not attached, the entity will be attached
	 * before removed. Note that the entity will not be removed from the database until close()
	 * or commit() is called.
	 * @param entity
	 */
	@Override
	public void remove(T entity) {
		T removeEntity = entity;
		if(!this.em.contains(entity)) {
			removeEntity = this.attach(entity);
		}
		this.em.remove(removeEntity);
	}
	
	/**
	 * Attach a UserLevel entity with the underlying entity manager.
	 * @param entity
	 */
	@Override
	public T attach(T entity) {
		return this.em.merge(entity);
	}
	
	public EntityManager getEntityManager() {
		return this.em;
	}
}
