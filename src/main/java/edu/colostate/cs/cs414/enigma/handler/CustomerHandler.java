package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Manager;

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
		close();
		return customers;
	}
	
	public void close() {
		dao.close();
	}
	
	public List<Customer> getCustomerByKeyword(String keywords) {
		
		EntityManagerDao dao = new EntityManagerDao();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("keyword", "%" + keywords + "%");
		List<?> results = dao.query("Customer.findByKeywords", parameters);
		List<Customer> customers= new ArrayList<Customer>();
		
			for(int i=0; i<results.size(); i++) {
				customers.add((Customer) results.get(i));
			}
		dao.close();
		return customers;
	}
	
	public Customer getCustomerById(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return (Customer) dao.querySingle("Customer.findById", params);
	}
}
