package edu.colostate.cs.cs414.enigma.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseDurationException;

@Entity
@Table(name="exercise_duration")
@NamedQueries({
	@NamedQuery(name="ExerciseDuration.findAll", query="SELECT d FROM ExerciseDuration d"),
	@NamedQuery(name="ExerciseDuration.findId", query="SELECT d FROM ExerciseDuration d WHERE d.id = :id")
})
public class ExerciseDuration {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@Column(name="hours", unique=false, nullable=false, updatable=true)
	private int hours;
	
	@Column(name="minutes", unique=false, nullable=false, updatable=true)
	private int minutes;
	
	@Column(name="seconds", unique=false, nullable=false, updatable=true)
	private int seconds;
		
	protected ExerciseDuration() {}

	public ExerciseDuration(int hours, int minutes, int seconds) throws ExerciseDurationException {
		this.setHours(hours);
		this.setMinutes(minutes);
		this.setSeconds(seconds);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) throws ExerciseDurationException {
		if(hours < 0) {
			throw new ExerciseDurationException("Exercise duration hours cannot be less than zero");
		}
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) throws ExerciseDurationException {
		if(minutes < 0) {
			throw new ExerciseDurationException("Exercise duration minutes cannot be less than zero");
		}
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) throws ExerciseDurationException {
		if(seconds < 0) {
			throw new ExerciseDurationException("Exercise duration seconds cannot be less than zero");
		}
		this.seconds = seconds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hours;
		result = prime * result + id;
		result = prime * result + minutes;
		result = prime * result + seconds;
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
		ExerciseDuration other = (ExerciseDuration) obj;
		if (hours != other.hours)
			return false;
		if (id != other.id)
			return false;
		if (minutes != other.minutes)
			return false;
		if (seconds != other.seconds)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Duration [id=" + id + ", hours=" + hours + ", minutes=" + minutes + ", seconds=" + seconds + "]";
	}
}
