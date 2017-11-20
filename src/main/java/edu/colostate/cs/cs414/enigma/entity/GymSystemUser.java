package edu.colostate.cs.cs414.enigma.entity;

import javax.mail.internet.AddressException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class GymSystemUser {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="personal_information_id", unique=true, nullable=false, updatable=false)
	private PersonalInformation personalInformation;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id", unique=true, nullable=false, updatable=false)
	private User user;

	public GymSystemUser(PersonalInformation personalInformation, User user) {
		this.setPersonalInformation(personalInformation);
		this.setUser(user);
	}
	
	protected GymSystemUser() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PersonalInformation getPersonalInformation() {
		return personalInformation;
	}

	public void setPersonalInformation(PersonalInformation personalInformation) {
		if(personalInformation == null) {
			throw new IllegalArgumentException("Personal Information cannot be empty");
		}
		this.personalInformation = personalInformation;
	}
	
	public void setPersonalInformation(String firstName, String lastName, String phoneNumber, String email,
			String street, String city, State state, String zipcode, HealthInsurance healthInsurance) throws AddressException {
		this.getPersonalInformation().setFirstName(firstName);
		this.getPersonalInformation().setFirstName(lastName);
		this.getPersonalInformation().setPhoneNumber(phoneNumber);
		this.getPersonalInformation().setEmail(email);
		this.getPersonalInformation().getAddress().setStreet(street);
		this.getPersonalInformation().getAddress().setCity(city);
		this.getPersonalInformation().getAddress().setZipcode(zipcode);
		this.getPersonalInformation().getAddress().setState(state);
		this.getPersonalInformation().setHealthInsurance(healthInsurance);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		if(user == null) {
			throw new IllegalArgumentException("User cannot be empty");
		}
		this.user = user;
	}
	
	public void setUser(String username, String password) {
		this.getUser().setUsername(username);
		this.getUser().setPassword(password);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((personalInformation == null) ? 0 : personalInformation.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		GymSystemUser other = (GymSystemUser) obj;
		if (id != other.id)
			return false;
		if (personalInformation == null) {
			if (other.personalInformation != null)
				return false;
		} else if (!personalInformation.equals(other.personalInformation))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GymSystemUser [id=" + id + ", personalInformation=" + personalInformation + ", user=" + user + "]";
	}
}
