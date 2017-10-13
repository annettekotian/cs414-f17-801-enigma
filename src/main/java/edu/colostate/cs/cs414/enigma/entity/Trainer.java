package edu.colostate.cs.cs414.enigma.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the trainer database table.
 * 
 */
@Entity
@Table(name="trainer")
@NamedQueries({
	@NamedQuery(name="Trainer.findAll", query="SELECT t FROM Trainer t"),
	
})

public class Trainer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private int id;
	
	
	/*@Column(name="personal_information_id", nullable=false)
	private int personal_information_id;
	
	
	@Column(name="user_id", nullable=false)
	private int user_id;*/
	
	@OneToOne
	@JoinColumn(name="personal_information_id")
	private PersonalInformation personalInformation;
	
	@OneToOne
	@JoinColumn (name="user_id")
	private User user;

	public Trainer(PersonalInformation personalInformation) {
		
		this.personalInformation = personalInformation;
	}
	
	
	public Trainer() {
		super();
	}


	public PersonalInformation getPersonalInformation() {
		return personalInformation;
	}


	public void setPersonalInformation(PersonalInformation personalInformation) {
		this.personalInformation = personalInformation;
	}
	
	
	
	
}
