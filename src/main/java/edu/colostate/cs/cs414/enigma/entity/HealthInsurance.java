package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the health_insurance database table.
 * 
 */
@Entity
@Table(name="health_insurance")
@NamedQuery(name="HealthInsurance.findAll", query="SELECT h FROM HealthInsurance h")
public class HealthInsurance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=255)
	private String description;

	public HealthInsurance() {
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

}