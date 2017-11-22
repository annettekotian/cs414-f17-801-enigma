package edu.colostate.cs.cs414.enigma.handler.builder;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;

import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Membership;

public class CustomerBuilder extends PersonalInformationBuilder {

	private String membershipStatus;

	public CustomerBuilder() {
		super();
	}
	
	public CustomerBuilder(Customer customer) {
		super();
		this.firstName = customer.getPersonalInformation().getFirstName();
		this.lastName = customer.getPersonalInformation().getLastName();
		this.email = customer.getPersonalInformation().getEmail();
		this.phoneNumber = customer.getPersonalInformation().getPhoneNumber();
		this.street = customer.getPersonalInformation().getAddress().getStreet();
		this.city = customer.getPersonalInformation().getAddress().getCity();
		this.state = customer.getPersonalInformation().getAddress().getState().getState();
		this.zipcode = customer.getPersonalInformation().getAddress().getZipcode();
		this.membershipStatus = customer.getMembership().getType();
	}

	public void setMembershipStatus(String membershipStatus) {
		this.membershipStatus = membershipStatus;
	}
	
	private Membership getMembership() {
		// Get the membership object based on type
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", membershipStatus);
		Membership membership = (Membership) getDao().querySingle("Membership.findType", parameters);
		return membership;
	}
	
	private Customer getCustomer(int id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Customer customer = (Customer) getDao().querySingle("Customer.findById", params);
		if(customer == null) {
			throw new IllegalArgumentException("Customer does not exist with id " + id);
		}
		return customer;
	}
	
	public Customer createCustomer() throws AddressException {
		Customer customer = new Customer(this.createPersonalInformation(), this.getMembership());
		getDao().persist(customer);
		return customer;
	}
	
	public Customer updateCustomer(int customerId) throws AddressException {
		Customer customer = this.getCustomer(customerId);
		this.updatePersonalInformation(customer.getPersonalInformation());
		customer.setMembership(this.getMembership());
		this.getDao().persist(customer);
		return customer;
	}
}
