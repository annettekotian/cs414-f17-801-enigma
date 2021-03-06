package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseDurationException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseException;
import edu.colostate.cs.cs414.enigma.entity.exception.ExerciseSetException;

@Entity
@Table(name="exercise")
@NamedQueries({
	@NamedQuery(name="Exercise.findAll", query="SELECT e FROM Exercise e"),
	@NamedQuery(name="Exercise.findByName", query="SELECT e FROM Exercise e WHERE e.name = :name"),
	@NamedQuery(name="Exercise.findId", query="SELECT e FROM Exercise e WHERE e.id = :id")
})
public class Exercise implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@Column(name="name", unique=true, nullable=false, updatable=true)
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name="machine_id", nullable=true, updatable=true)
	
	private Machine machine;
	
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="duration_id", nullable=true, updatable=true)
	private ExerciseDuration duration;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	@JoinColumn(name="exercise_id", referencedColumnName="id")
	private List<ExerciseSet> sets;
	
	protected Exercise() {}

	public Exercise(String name) throws ExerciseException {
		this.setName(name);
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

	public void setName(String name) throws ExerciseException {
		if(name == null || name.isEmpty()) {
			throw new ExerciseException("Exercise name cannot be empty");
		}
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
	
	public void setDuration(int hours, int minutes, int seconds) throws ExerciseDurationException {
		if(hours != 0 || minutes != 0 || seconds != 0) {
			if(this.duration == null) {
				this.duration = new ExerciseDuration(hours, minutes, seconds);
			} else {
				this.duration.setHours(hours);
				this.duration.setMinutes(minutes);
				this.duration.setSeconds(seconds);
			}
		} else {
			this.duration = null;
		}
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
	
	public void setSets(List<Integer> sets) throws ExerciseSetException {
		boolean rebuildSets = false;
		if(this.getSets().size() == sets.size()) {
			for(int i=0; i<this.getSets().size(); i++) {
				if(this.getSets().get(i).getRepetitions() != sets.get(i)) {
					rebuildSets = true;
					break;
				}
			}
		} else if(sets.size() != 0) {
			rebuildSets = true;
		} else {
			this.deleteSets();
		}
		
		if(rebuildSets) {
			this.deleteSets();
			for(int i=0; i<sets.size(); i++) {
				ExerciseSet set = new ExerciseSet(sets.get(i));
				this.addSet(set);
			}
		}
	}
	
	public String searchString() {
		String machineName = "";
		String duration = "";
		String sets = "";
		
		if(this.getMachine() != null) {
			machineName = this.getMachine().getName();
		}
		if(this.getDuration() != null) {
			duration = "" + this.getDuration().getHours() + "H " + this.getDuration().getMinutes() + "M " + this.getDuration().getSeconds() + "S";
		}
		for(int i=0; i<this.getSets().size(); i++) {
			if(i == 0) {
				sets = "" + this.getSets().get(i).getRepetitions() + " repetition(s)";
			} else {
				sets = sets +  ", " + this.getSets().get(i).getRepetitions() + " repetition(s)";
			}
		}
		
		return this.getName() + " " + machineName + " " + duration + " " + sets;
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
