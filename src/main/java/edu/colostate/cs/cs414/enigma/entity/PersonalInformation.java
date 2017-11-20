package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


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
	
	//uni-directional ono-to-one association to Address
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="address_id", nullable=false, insertable=true, updatable=true)
	private Address address;

	protected PersonalInformation() {}

	public PersonalInformation(String email, String firstName, String lastName, String phoneNumber,
			HealthInsurance healthInsurance, Address address) throws AddressException {
		super();
		this.setEmail(email);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPhoneNumber(phoneNumber);
		this.setHealthInsurance(healthInsurance);
		this.setAddress(address);
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

	public void setEmail(String email) throws AddressException {
		if(email == null) {
			throw new IllegalArgumentException("Email cannot be empty");
		}
		if(email.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be empty");
		}
		new InternetAddress(email).validate();
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName == null) {
			throw new IllegalArgumentException("First name cannot be empty");
		}
		if(firstName.isEmpty()) {
			throw new IllegalArgumentException("First name cannot be empty");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName == null) {
			throw new IllegalArgumentException("Last name cannot be empty");
		}
		if(lastName.isEmpty()) {
			throw new IllegalArgumentException("Last name cannot be empty");
		}
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		if(phoneNumber == null) {
			throw new IllegalArgumentException("Phone number must be 10 digits in format ###-###-####");
		}
		if(!phoneNumber.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$")) {
			throw new IllegalArgumentException("Phone number must be 10 digits in format ###-###-####");
		}
		this.phoneNumber = phoneNumber;
	}

	public HealthInsurance getHealthInsurance() {
		return healthInsurance;
	}

	public void setHealthInsurance(HealthInsurance healthInsurance) {
		if(healthInsurance == null) {
			throw new IllegalArgumentException("Health Insurance cannot be empty");
		}
		this.healthInsurance = healthInsurance;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		if(healthInsurance == null) {
			throw new IllegalArgumentException("Addres cannot be empty");
		}
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
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
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
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
				+ lastName + ", phoneNumber=" + phoneNumber + ", healthInsurance=" + healthInsurance + ", address="
				+ address + "]";
	}
}