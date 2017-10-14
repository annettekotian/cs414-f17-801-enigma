package edu.colostate.cs.cs414.enigma.listener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EntityManagerFactoryListener implements ServletContextListener {

	private static EntityManagerFactory emf;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		emf.close();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		emf = Persistence.createEntityManagerFactory("GymSystem");
	}

	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
}
