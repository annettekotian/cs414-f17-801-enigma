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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="exercise")
@NamedQueries({
	@NamedQuery(name="Exercise.findAll", query="SELECT e FROM Exercise e"),
	@NamedQuery(name="Exercise.findByName", query="SELECT e FROM Exercise e WHERE e.name = :name"),
	@NamedQuery(name="Exercise.findId", query="SELECT e FROM Exercise e WHERE e.id = :id")
})
public class Exercise {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@Column(name="name", unique=true, nullable=false, updatable=true)
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="machine_id", nullable=true, updatable=true)
	private Machine machine;
	
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="duration_id", nullable=true, updatable=true)
	private ExerciseDuration duration;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
	@JoinColumn(name="exercise_id", referencedColumnName="id")
	private List<ExerciseSet> sets;
	
	protected Exercise() {}

	public Exercise(String name) {
		this.name = name;
		this.sets = new ArrayList<ExerciseSet>();
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

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public ExerciseDuration getDuration() {
		return duration;
	}

	public void setDuration(ExerciseDuration duration) {
		this.duration = duration;
	}
	
	public void deleteDuration() {
		this.duration = null;
	}

	public List<ExerciseSet> getSets() {
		return sets;
	}
	
	public void addSet(ExerciseSet set) {
		if(!this.sets.contains(set)) {
			this.sets.add(set);
			set.setExerciseId(this.getId());
		}
	}
	
	public void deleteSet(ExerciseSet set) {
		if(this.sets.contains(set)) {
			this.sets.remove(set);
		}
	}
	
	public void deleteSets() {
		this.sets.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + id;
		result = prime * result + ((machine == null) ? 0 : machine.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sets == null) ? 0 : sets.hashCode());
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
		Exercise other = (Exercise) obj;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (id != other.id)
			return false;
		if (machine == null) {
			if (other.machine != null)
				return false;
		} else if (!machine.equals(other.machine))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sets == null) {
			if (other.sets != null)
				return false;
		} else if (!sets.equals(other.sets))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Exercise [id=" + id + ", name=" + name + ", machine=" + machine + ", duration=" + duration + ", sets="
				+ sets + "]";
	}
}
