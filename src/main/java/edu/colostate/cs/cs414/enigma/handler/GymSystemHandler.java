package edu.colostate.cs.cs414.enigma.handler;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.dao.GymSystemDao;

public abstract class GymSystemHandler {

	private GymSystemDao dao;
	
	public GymSystemHandler() {
		this.dao = new EntityManagerDao();
	}

	public GymSystemHandler(GymSystemDao dao) {
		this.dao = dao;
	}
	
	public void close() {
		this.dao.close();
	}
	
	public GymSystemDao getDao() {
		return dao;
	}
}
