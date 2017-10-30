package edu.colostate.cs.cs414.enigma.entity;

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
	
	@Column(name="picture_location", unique=true, nullable=false, updatable=true)
	private String pictureLocation;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="machine_id", nullable=true, updatable=true)
	private Machine machine;
	
	protected Exercise() {}

	public Exercise(String name, String pictureLocation) {
		this.name = name;
		this.pictureLocation = pictureLocation;
	}
	
	public Exercise(String name, String pictureLocation, Machine machine) {
		this.name = name;
		this.pictureLocation = pictureLocation;
		this.machine = machine;
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

	public String getPictureLocation() {
		return pictureLocation;
	}

	public void setPictureLocation(String pictureLocation) {
		this.pictureLocation = pictureLocation;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}
}
