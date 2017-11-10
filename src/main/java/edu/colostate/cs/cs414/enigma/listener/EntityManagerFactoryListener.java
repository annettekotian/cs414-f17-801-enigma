package edu.colostate.cs.cs414.enigma.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;

public class EntityManagerFactoryListener implements ServletContextListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// Create a new EntityManagerDao to close EntityManagerFactory for DB
		new EntityManagerDao().shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// Create a new EntityManagerDao to initialize EntityManagerFactory for DB
		new EntityManagerDao().close();
	}
}
