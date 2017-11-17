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

import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseSetException;

@Entity
@Table(name="exercise_set")
@NamedQueries({
	@NamedQuery(name="ExerciseSet.findAll", query="SELECT s FROM ExerciseSet s"),
	@NamedQuery(name="ExerciseSet.findId", query="SELECT s FROM ExerciseSet s WHERE s.id = :id")
})
public class ExerciseSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;

	@Column(name="repetitions", unique=false, nullable=false, updatable=true)
	private int repetitions;

	@Column(name="exercise_id", unique=false, nullable=false, updatable=false)
	private int exerciseId;
	
	protected ExerciseSet() {}
	
	public ExerciseSet(int repetitions) throws ExerciseSetException {
		this.setRepetitions(repetitions);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRepetitions() {
		return repetitions;
	}

	public void setRepetitions(int repetitions) throws ExerciseSetException {
		if(repetitions < 1) {
			throw new ExerciseSetException("Exercise set repetitions cannot be less than 1");
		}
		this.repetitions = repetitions;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + exerciseId;
		result = prime * result + id;
		result = prime * result + repetitions;
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
		ExerciseSet other = (ExerciseSet) obj;
		if (exerciseId != other.exerciseId)
			return false;
		if (id != other.id)
			return false;
		if (repetitions != other.repetitions)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Set [id=" + id + ", repetitions=" + repetitions + ", exerciseId=" + exerciseId + "]";
	}
}
