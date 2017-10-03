package edu.colostate.cs.cs414.enigma.users;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the personal_information database table.
 * 
 */
@Embeddable
public class PersonalInformationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="health_insurance_id", insertable=false, updatable=false, unique=true, nullable=false)
	private int healthInsuranceId;

	public PersonalInformationPK() {
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHealthInsuranceId() {
		return this.healthInsuranceId;
	}
	public void setHealthInsuranceId(int healthInsuranceId) {
		this.healthInsuranceId = healthInsuranceId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PersonalInformationPK)) {
			return false;
		}
		PersonalInformationPK castOther = (PersonalInformationPK)other;
		return 
			(this.id == castOther.id)
			&& (this.healthInsuranceId == castOther.healthInsuranceId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.healthInsuranceId;
		
		return hash;
	}
}