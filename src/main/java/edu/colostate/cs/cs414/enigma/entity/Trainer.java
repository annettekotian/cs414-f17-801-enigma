package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the trainer database table.
 */
@Entity
@Table(name="trainer")
@NamedQueries({
	@NamedQuery(name="Trainer.findAll", query="SELECT t FROM Trainer t"),
	@NamedQuery(name="Trainer.findById", query="SELECT t FROM Trainer t WHERE t.id = :id")
})
public class Trainer extends GymSystemUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="trainer_qualification",
			joinColumns=@JoinColumn(name="trainer_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="qualification_id", referencedColumnName="id"))
	private List<Qualification> qualifications;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="trainer", orphanRemoval=true)	
	private List<WorkHours> workHours;
	
	protected Trainer() {
		super();
		workHours = new ArrayList<WorkHours>();
	}

	public Trainer(PersonalInformation personalInformation, User user) {
		super(personalInformation, user);
	}
	
	public Trainer(PersonalInformation personalInformation, User user, List<Qualification> qualifications) {
		super(personalInformation, user);
		this.qualifications = qualifications;
	}

	public List<Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public List<WorkHours> getWorkHours() {
		return workHours;
	}

	public void setWorkHours(List<WorkHours> workHours) {
		this.workHours = workHours;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((qualifications == null) ? 0 : qualifications.hashCode());
		result = prime * result + ((workHours == null) ? 0 : workHours.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trainer other = (Trainer) obj;
		if (qualifications == null) {
			if (other.qualifications != null)
				return false;
		} else if (!qualifications.equals(other.qualifications))
			return false;
		if (workHours == null) {
			if (other.workHours != null)
				return false;
		} else if (!workHours.equals(other.workHours))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trainer [qualifications=" + qualifications + ", workHours=" + workHours + "]";
	}
}
