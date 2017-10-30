package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the manager database table.
 */
@Entity
@Table(name="manager")
@NamedQueries({
	@NamedQuery(name="Manager.findAll", query="SELECT m FROM Manager m"),
	@NamedQuery(name="Manager.findByName", query = "SELECT m FROM Manager m WHERE m.personalInformation.firstName = :firstName "
			+ "AND m.personalInformation.lastName = :lastName"), 
	@NamedQuery(name="Manager.findById", query = "SELECT m FROM Manager m where m.id = :id"),
	@NamedQuery(name="Manager.findByKeywords", query = "SELECT m FROM Manager m where m.personalInformation.firstName LIKE :keyword" 
				+ " OR m.personalInformation.lastName LIKE :keyword OR m.personalInformation.email LIKE :keyword"
				+ " OR m.personalInformation.phoneNumber LIKE :keyword  OR m.personalInformation.lastName LIKE :keyword" 
				+ " OR m.personalInformation.healthInsurance.name LIKE :keyword OR m.personalInformation.address.city LIKE :keyword"
				+ " OR m.personalInformation.address.street LIKE :keyword OR m.personalInformation.address.zipcode LIKE :keyword"
				+ " OR m.personalInformation.address.state.state LIKE :keyword OR m.id LIKE :keyword") 
			
	
})


public class Manager extends GymSystemUser implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Manager(PersonalInformation personalInformation, User user) {
		super(personalInformation, user);
		// TODO Auto-generated constructor stub
	}
	
	
	public void addManager (PersonalInformation p, User u) {
		
	}
}
