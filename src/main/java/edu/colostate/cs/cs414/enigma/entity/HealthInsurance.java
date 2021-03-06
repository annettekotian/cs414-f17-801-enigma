package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the health_insurance database table.
 * 
 */
@Entity
@Table(name="health_insurance")
@NamedQueries({
	@NamedQuery(name="HealthInsurance.findAll", query="SELECT h FROM HealthInsurance h"),
	
	// TODO: Remove HealthInsurance.findDescription
	@NamedQuery(name="HealthInsurance.findDescription", query="SELECT h FROM HealthInsurance h WHERE h.name = :description"),
	
	@NamedQuery(name="HealthInsurance.findByName", query="SELECT h FROM HealthInsurance h WHERE h.name = :name"),
	@NamedQuery(name="HealthInsurance.findId", query="SELECT h FROM HealthInsurance h WHERE h.id = :id")
})
public class HealthInsurance implements Serializable {
	private static final long serialVersionUID = 1L;

	/* Primary key generated by the database. Key must be unique, not null, and not updatable */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;

	/* Description cannot be updated once set. Also, it must be unique. */
	@Column(name="name", nullable=false, updatable=false, unique=true, length=255)
	private String name;

	public HealthInsurance(String name) {
		this.setName(name);
	}
	
	protected HealthInsurance() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Health Insurance name cannot be empty");
		}
		if(name.isEmpty()) {
			throw new IllegalArgumentException("Health Insurance name cannot be empty");
		}
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + id;
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
		HealthInsurance other = (HealthInsurance) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}