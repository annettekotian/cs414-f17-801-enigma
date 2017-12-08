package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="feedback")
@NamedQueries({
	@NamedQuery(name="feedback.findAll", query="SELECT f FROM Feedback f"),
	@NamedQuery(name="feedback.findById", query="SELECT f FROM Feedback f where f.id = :id"),
})
public class Feedback implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@Column(name="feedback", unique=false, nullable=false, updatable=true)
	private String feedback;
	
	@Column(name="customer_id", unique=false, nullable=true, updatable=true)
	private int customerId;
	
	@Column(name="workout_routine_id", unique=false, nullable=false, updatable=true)
	private int workoutId;
	
	public Feedback(Customer customer, Workout workout, String feedback) {
		if(workout == null) {
			throw new IllegalArgumentException("Workout cannot be null");
		} else {
			this.workoutId = workout.getId();
		}
		
		if(customer == null) {
			throw new IllegalArgumentException("Customer cannot be null");
		} else if(!customer.getWorkouts().contains(workout)) {
			throw new IllegalArgumentException("Customer does not have workout");
		} else {
			this.customerId = customer.getId();
		}
		
		this.setFeedback(feedback);
	}
	
	protected Feedback() {}
	
	public void setFeedback(String feedback) {
		if(feedback == null) {
			throw new IllegalArgumentException("Feedback cannot be null");
		} else {
			this.feedback = feedback;
		}
	}

	public int getId() {
		return id;
	}

	public String getFeedback() {
		return feedback;
	}

	public int getCustomerId() {
		return customerId;
	}

	public int getWorkoutId() {
		return workoutId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerId;
		result = prime * result + ((feedback == null) ? 0 : feedback.hashCode());
		result = prime * result + id;
		result = prime * result + workoutId;
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
		Feedback other = (Feedback) obj;
		if (customerId != other.customerId)
			return false;
		if (feedback == null) {
			if (other.feedback != null)
				return false;
		} else if (!feedback.equals(other.feedback))
			return false;
		if (id != other.id)
			return false;
		if (workoutId != other.workoutId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", feedback=" + feedback + ", customerId=" + customerId + ", workoutId="
				+ workoutId + "]";
	}
}
