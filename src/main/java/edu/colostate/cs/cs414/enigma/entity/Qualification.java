package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Table;

@Entity
@Table(name="qualification")
@NamedQueries({
	@NamedQuery(name="Qualification.findAll", query="SELECT q FROM Qualification q"),
	@NamedQuery(name="Qualification.findByName", query="SELECT q FROM Qualification q WHERE q.name = :name")
})
public class Qualification implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@Column(name="name", nullable=false, updatable=false, unique=true)
	private String name;

	/*@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="trainer_qualification",
			joinColumns=@JoinColumn(name="qualification_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="trainer_id", referencedColumnName="id"))
	private List<Trainer> trainers = new ArrayList<Trainer>();*/
	
	protected Qualification() {}
	
	public Qualification(String name) {
		this.name = name;
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
}
