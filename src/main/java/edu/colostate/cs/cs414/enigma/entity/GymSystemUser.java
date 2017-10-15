package edu.colostate.cs.cs414.enigma.entity;

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
	@JoinColumn (name="user_id", unique=true, nullable=false, updatable=false)
	private User user;

	public GymSystemUser(PersonalInformation personalInformation, User user) {
		this.personalInformation = personalInformation;
		this.user = user;
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
		this.personalInformation = personalInformation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
