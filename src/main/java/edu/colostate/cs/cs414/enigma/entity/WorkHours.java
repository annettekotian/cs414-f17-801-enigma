package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="work_hours")
@NamedQueries({
	@NamedQuery(name="WorkHours.findAll", query="SELECT w FROM WorkHours w"),
	@NamedQuery(name="WorkHours.findById", query="SELECT w FROM WorkHours w WHERE w.id = :id")
})
public class WorkHours implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@Column(name="start_date_time", nullable=false, updatable=false, unique=false)
	private Date startDateTime;
	
	@Column(name="end_date_time", nullable=false, updatable=false, unique=false)
	private Date endDateTime;
	
	@Column(name="trainer_id")
	private int trainerId;
	
	protected WorkHours() {}

	public WorkHours(Date startDateTime, Date endDateTime) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public int getTrainerId() {
		return this.trainerId;
	}

	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDateTime == null) ? 0 : endDateTime.hashCode());
		result = prime * result + id;
		result = prime * result + ((startDateTime == null) ? 0 : startDateTime.hashCode());
		result = prime * result + trainerId;
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
		WorkHours other = (WorkHours) obj;
		if (endDateTime == null) {
			if (other.endDateTime != null)
				return false;
		} else if (!endDateTime.equals(other.endDateTime))
			return false;
		if (id != other.id)
			return false;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
			return false;
		if (trainerId != other.trainerId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WorkHours [id=" + id + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime
				+ ", trainerId=" + trainerId + "]";
	}
}
