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
}
