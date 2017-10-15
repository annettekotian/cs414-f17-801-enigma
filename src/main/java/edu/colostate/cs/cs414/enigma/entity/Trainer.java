package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the trainer database table.
 */
@Entity
@Table(name="trainer")
@NamedQueries({
	@NamedQuery(name="Trainer.findAll", query="SELECT t FROM Trainer t"),
	
})
public class Trainer extends GymSystemUser implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Trainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Trainer(PersonalInformation personalInformation, User user) {
		super(personalInformation, user);
		// TODO Auto-generated constructor stub
	}
}
