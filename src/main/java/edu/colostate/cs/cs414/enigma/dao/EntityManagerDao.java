package edu.colostate.cs.cs414.enigma.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class EntityManagerDao implements GymSystemDao {

	private EntityManager em;
	
	public EntityManagerDao() {
		em = EntityManagerFactoryListener.createEntityManager();
	}
	
	@Override
	public void persist(Object object) {
		em.getTransaction().begin();
		em.persist(object);
		em.getTransaction().commit();
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
		if(rawResults == null) {
			return null;
		}
		else {
			return rawResults.get(0);
		}
	}
}
