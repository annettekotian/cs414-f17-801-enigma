package edu.colostate.cs.cs414.enigma.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name="workout_routine")
@NamedQueries({
	@NamedQuery(name="Workout.findAll", query="SELECT w FROM Workout w ORDER BY w.id"),
	@NamedQuery(name="Workout.findByName", query="SELECT w FROM Workout w WHERE w.name = :name"),
	@NamedQuery(name="Workout.findId", query="SELECT w FROM Workout w WHERE w.id = :id")
})
public class Workout {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@Column(name="name", unique=true, nullable=false, updatable=true)
	private String name;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="workout_routine_exercise",
			joinColumns=@JoinColumn(name="workout_routine_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="exercise_id", referencedColumnName="id"))
	@OrderColumn(name="exercise_order")
	private List<Exercise> exercises;
	
	protected Workout() {}
	
	public Workout(String name) {
		this.name = name;
		this.exercises = new ArrayList<Exercise>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}
	
	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}
	
	public void addExercise(Exercise exercise) {
		if(!this.exercises.contains(exercise)) {
			this.exercises.add(exercise);
		}
	}
	
	public void removeExercise(Exercise exercise) {
		this.exercises.remove(exercise);
	}
	
	public void removeAllExercises() {
		this.exercises.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exercises == null) ? 0 : exercises.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Workout other = (Workout) obj;
		if (exercises == null) {
			if (other.exercises != null)
				return false;
		} else if (!exercises.equals(other.exercises))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Workout [id=" + id + ", name=" + name + ", exercises=" + exercises + "]";
	}
}
