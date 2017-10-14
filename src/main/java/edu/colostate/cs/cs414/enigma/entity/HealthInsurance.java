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
	@NamedQuery(name="HealthInsurance.findAll", query="SELECT h FROM HealthInsurance h")
})
public class HealthInsurance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=255)
	private String description;

	public HealthInsurance(String description) {
		this.description = description;
	}
	
	protected HealthInsurance() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HealthInsurance [id=" + id + ", description=" + description + "]";
	}
}