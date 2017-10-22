package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;

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

	protected Customer() {}

	public Customer(PersonalInformation personalInformation, Membership membership) {
		super();
		this.personalInformation = personalInformation;
		this.membership = membership;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((membership == null) ? 0 : membership.hashCode());
		result = prime * result + ((personalInformation == null) ? 0 : personalInformation.hashCode());
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
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", personalInformation=" + personalInformation + ", membership=" + membership
				+ "]";
	}
}
