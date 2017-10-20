package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.List;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.HealthInsurance;
import edu.colostate.cs.cs414.enigma.entity.Membership;

public class HealthInsuranceHandler {
	
	private EntityManagerDao dao;
	
	public HealthInsuranceHandler() {
		dao = new EntityManagerDao();
	}

	public void close() {
		dao.close();
	}
	
	public List<HealthInsurance> getHealthInsurances() {
		List rawHealthInsurance = dao.query("HealthInsurance.findAll", null);
		List<HealthInsurance> insurances = new ArrayList<HealthInsurance>();
		for(int i=0; i<rawHealthInsurance.size(); i++) {
			insurances.add((HealthInsurance) rawHealthInsurance.get(i));
		}
		
		close();
		return insurances;
	}
}