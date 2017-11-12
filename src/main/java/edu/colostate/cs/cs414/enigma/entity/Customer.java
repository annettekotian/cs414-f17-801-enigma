package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the trainer database table.
 */
@Entity
@Table(name="customer")
@NamedQueries({
	@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c"),
	@NamedQuery(name="Customer.findById", query="SELECT c FROM Customer c where c.id = :id"),
	@NamedQuery(name="Customer.findByKeywords", query = "SELECT c FROM Customer c where c.personalInformation.firstName LIKE :keyword" 
			+ " OR c.personalInformation.lastName LIKE :keyword OR c.personalInformation.email LIKE :keyword"
			+ " OR c.personalInformation.phoneNumber LIKE :keyword  OR c.personalInformation.lastName LIKE :keyword" 
			+ " OR c.personalInformation.healthInsurance.name LIKE :keyword OR c.personalInformation.address.city LIKE :keyword"
			+ " OR c.personalInformation.address.street LIKE :keyword OR c.personalInformation.address.zipcode LIKE :keyword"
			+ " OR c.personalInformation.address.state.state LIKE :keyword OR c.id LIKE :keyword"
			+ " OR c.membership.type LIKE :keyword") 
	
})
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="personal_information_id", unique=true, nullable=false, updatable=false)
	private PersonalInformation personalInformation;
	
	//uni-directional many-to-one association to Membership
	@ManyToOne
	@JoinColumn(name="membership_id", nullable=false, updatable=true)
	private Membership membership;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="customer_workout_routine",
			joinColumns=@JoinColumn(name="customer_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="workout_routine_id", referencedColumnName="id"))
	private List<Workout> workouts;

	protected Customer() {}

	public Customer(PersonalInformation personalInformation, Membership membership) {
		this.personalInformation = personalInformation;
		this.membership = membership;
		this.workouts = new ArrayList<Workout>();
	}

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

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}
	
	public List<Workout> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(List<Workout> workouts) {
		this.workouts = workouts;
	}
	
	public void addWorkout(Workout workout) {
		if(!this.workouts.contains(workout)) {
			this.workouts.add(workout);
		}
	}
	
	public void removeWorkout(Workout workout) {
		this.workouts.remove(workout);
	}
	
	public void removeAllWorkouts() {
		this.workouts.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((membership == null) ? 0 : membership.hashCode());
		result = prime * result + ((personalInformation == null) ? 0 : personalInformation.hashCode());
		result = prime * result + ((workouts == null) ? 0 : workouts.hashCode());
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
		Customer other = (Customer) obj;
		if (id != other.id)
			return false;
		if (membership == null) {
			if (other.membership != null)
				return false;
		} else if (!membership.equals(other.membership))
			return false;
		if (personalInformation == null) {
			if (other.personalInformation != null)
				return false;
		} else if (!personalInformation.equals(other.personalInformation))
			return false;
		if (workouts == null) {
			if (other.workouts != null)
				return false;
		} else if (!workouts.equals(other.workouts))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", personalInformation=" + personalInformation + ", membership=" + membership
				+ ", workouts=" + workouts + "]";
	}
}
