package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;


/**
 * The persistent class for the personal_information database table.
 * 
 */
@Entity
@Table(name="personal_information")
@NamedQueries({
	@NamedQuery(name="PersonalInformation.findAll", query="SELECT p FROM PersonalInformation p")
})
public class PersonalInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;

	@Column(name="email", nullable=false, updatable=true, length=255)
	private String email;

	@Column(name="first_name", nullable=false, updatable=true, length=45)
	private String firstName;

	@Column(name="last_name", nullable=false, updatable=true, length=45)
	private String lastName;

	@Column(name="phone_number", nullable=false, updatable=true, length=45)
	private String phoneNumber;

	//uni-directional many-to-one association to HealthInsurance
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="health_insurance_id", nullable=false, insertable=true, updatable=true)
	private HealthInsurance healthInsurance;

	protected PersonalInformation() {
	}

	public PersonalInformation(String email, String firstName, String lastName, String phoneNumber,
			HealthInsurance healthInsurance) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.healthInsurance = healthInsurance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public final HealthInsurance getHealthInsurance() {
		return healthInsurance;
	}

	public void setHealthInsurance(HealthInsurance healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((healthInsurance == null) ? 0 : healthInsurance.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonalInformation other = (PersonalInformation) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (healthInsurance == null) {
			if (other.healthInsurance != null)
				return false;
		} else if (!healthInsurance.equals(other.healthInsurance))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonalInformation [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName="
				+ lastName + ", phoneNumber=" + phoneNumber + ", healthInsurance=" + healthInsurance + "]";
	}
}