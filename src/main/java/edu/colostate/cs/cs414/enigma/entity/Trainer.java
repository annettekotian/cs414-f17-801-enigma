package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	@NamedQuery(name="Trainer.findById", query="SELECT t FROM Trainer t WHERE t.id = :id"),
	@NamedQuery(name="Trainer.findByUserId", query="SELECT t FROM Trainer t WHERE t.user.id = :id")
})
public class Trainer extends GymSystemUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="trainer_qualification",
			joinColumns=@JoinColumn(name="trainer_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="qualification_id", referencedColumnName="id"))
	private List<Qualification> qualifications = new ArrayList<Qualification>();
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
	@JoinColumn(name="trainer_id", referencedColumnName="id")
	private List<WorkHours> workHours = new ArrayList<WorkHours>();
	
	protected Trainer() {
		super();
	}

	public Trainer(PersonalInformation personalInformation, User user) {
		super(personalInformation, user);
	}

	public List<Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<Qualification> qualifications) {
		this.qualifications = qualifications;
	}
	
	public void addQualification(Qualification qualification) {
		if(!this.qualifications.contains(qualification)) {
			this.qualifications.add(qualification);
		}
	}
	
	public void removeQualification(Qualification qualification) {
		if(this.qualifications.contains(qualification)) {
			this.qualifications.remove(qualification);
		}
	}

	public List<WorkHours> getWorkHours() {
		return workHours;
	}

	public void setWorkHours(List<WorkHours> workHours) {
		this.workHours = workHours;
	}
	
	public void addWorkHours(WorkHours wh) {
		if(!this.workHours.contains(wh)) {
			this.workHours.add(wh);
			wh.setTrainerId(this.getId());
		}
	}
	
	public void deleteWorkHours() {
		this.workHours.clear();
	}
	
	public String searchString() {
		String email = this.getPersonalInformation().getEmail();
		String firstName = this.getPersonalInformation().getFirstName();
		String lastName = this.getPersonalInformation().getLastName();
		String phoneNumber = this.getPersonalInformation().getPhoneNumber();
		String address = this.getPersonalInformation().getAddress().getStreet()
				+ " " + this.getPersonalInformation().getAddress().getCity()
				+ ", " + this.getPersonalInformation().getAddress().getState().getStateAbbrev()
				+ " " + this.getPersonalInformation().getAddress().getZipcode();
		String healthInsurance = this.getPersonalInformation().getHealthInsurance().getName();
		String qualifications = "";
		for(int i=0; i<this.getQualifications().size(); i++) {
			if(i == 0) {
				qualifications += this.getQualifications().get(i);
			}
			else {
				qualifications += " " + this.getQualifications().get(i);
			}
		}	
		String workHours = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy h:mm:ss aaa");
		for(int i=0; i<this.getWorkHours().size(); i++) {
			if(i == 0) {
				workHours += dateFormat.format(this.getWorkHours().get(i).getStartDateTime()) + " - " + dateFormat.format(this.getWorkHours().get(i).getEndDateTime());
			}
			else {
				workHours += " " + dateFormat.format(this.getWorkHours().get(i).getStartDateTime()) + " - " + dateFormat.format(this.getWorkHours().get(i).getEndDateTime());
			}
		}
		
		return email + firstName + lastName + phoneNumber + address + healthInsurance + qualifications + workHours;
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
		return "Trainer [qualifications=" + qualifications + ", workHours=" + workHours + "] " + super.toString();
	}
}
