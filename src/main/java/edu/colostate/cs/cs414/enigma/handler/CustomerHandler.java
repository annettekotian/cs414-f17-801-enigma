package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.List;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;

public class CustomerHandler {

	private EntityManagerDao dao;
	
	public CustomerHandler() {
		dao = new EntityManagerDao();
	}
	
	public List<Customer> getCustomers() {
		List rawCustomers = dao.query("Customer.findAll", null);
		List<Customer> customers = new ArrayList<Customer>();
		for(int i=0; i<rawCustomers.size(); i++) {
			customers.add((Customer) rawCustomers.get(i));
		}
		return customers;
	}
	
	public void close() {
		dao.close();
	}
}
