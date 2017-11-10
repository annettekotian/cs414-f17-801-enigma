package edu.colostate.cs.cs414.enigma.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class EntityManagerDao implements GymSystemDao {

	private static EntityManagerFactory emf = null;
	
	private EntityManager em;
	
	public EntityManagerDao() {
		em = getEntityManagerFactory().createEntityManager();
	}
	
	private static synchronized EntityManagerFactory getEntityManagerFactory() {
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory("GymSystem");
		}
		return emf;
	}
	
	public void shutdown() {
		if(emf != null) {
			emf.close();
		}
	}
	
	@Override
	public void persist(Object object) throws PersistenceException {
		em.getTransaction().begin();
		try {
			em.persist(object);
			em.getTransaction().commit();
		} catch(Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public void remove(Object object) {
		em.getTransaction().begin();
		Object removedObject = object;
		if(!em.contains(object)) {
			removedObject = em.merge(object);
		}
		em.remove(removedObject);
		em.getTransaction().commit();
	}
	
	@Override
	public void update(Object object){
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
	}

	@Override
	public void close() {
		em.close();
	}

	@Override
	public List query(String query, Map<String, Object> parameters) {
		Query emQuery = em.createNamedQuery(query);
		if(parameters != null) {
			for(String key: parameters.keySet()) {
				emQuery.setParameter(key, parameters.get(key));
			}
		}
		return emQuery.getResultList();
	}

	@Override
	public Object querySingle(String query, Map<String, Object> parameters)
	{
		List rawResults = query(query, parameters);
		if(rawResults.size() == 0) {
			return null;
		}
		else {
			return rawResults.get(0);
		}
	}
}
