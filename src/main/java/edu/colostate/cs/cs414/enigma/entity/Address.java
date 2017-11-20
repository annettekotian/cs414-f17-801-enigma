package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;

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

@Entity
@Table(name="address")
@NamedQueries({
	@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
})
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;

	@Column(name="street", nullable=false, updatable=true, length=255)
	private String street;
	
	@Column(name="city", nullable=false, updatable=true, length=255)
	private String city;
	
	@Column(name="zipcode", nullable=false, updatable=true, length=12)
	private String zipcode;
	
	//uni-directional many-to-one association to HealthInsurance
	@ManyToOne
	@JoinColumn(name="state_id", nullable=false, insertable=true, updatable=true)
	private State state;
	
	protected Address() {}

	public Address(String street, String city, String zipcode, State state) {
		super();
		this.setStreet(street);
		this.setCity(city);
		this.setZipcode(zipcode);
		this.setState(state);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		if(street == null) {
			throw new IllegalArgumentException("Street cannot be empty");
		}
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if(street == null) {
			throw new IllegalArgumentException("City cannot be empty");
		}
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		if(zipcode == null) {
			throw new IllegalArgumentException("Zipcode cannot be empty");
		}
		if(!zipcode.matches("^[0-9]{5}$")) {
			throw new IllegalArgumentException("Zipcode must be 5 digits");
		}
		this.zipcode = zipcode;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		if(state == null) {
			throw new IllegalArgumentException("State cannot be empty");
		}
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
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
		Address other = (Address) obj;
		if (id != other.id)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (zipcode == null) {
			if (other.zipcode != null)
				return false;
		} else if (!zipcode.equals(other.zipcode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", town=" + city + ", zipcode=" + zipcode + ", state="
				+ state + "]";
	}
}
