package edu.colostate.cs.cs414.enigma.handler;

import java.util.List;

import edu.colostate.cs.cs414.enigma.dao.CustomerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;

public class CustomerHandler {

	private CustomerDao customerDao;
	
	public CustomerHandler() {
		customerDao = new CustomerDao();
	}
	
	public List<Customer> getCustomers() {
		return customerDao.getCustomers();
	}
	
	public void close() {
		customerDao.close();
	}
}
