package edu.colostate.cs.cs414.enigma.handler;

import java.util.List;

import edu.colostate.cs.cs414.enigma.dao.HealthInsuranceDao;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;

public class HealthInsuranceHandler {
	
	private HealthInsuranceDao healthInsuranceDao;
	
	public HealthInsuranceHandler() {
		healthInsuranceDao = new HealthInsuranceDao();
	}

	public void close() {
		healthInsuranceDao.close();
	}
	
	public List<HealthInsurance> getHealthInsurances() {
		return healthInsuranceDao.getHealthInsurances();
	}
}
