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
@Table(name="state")
@NamedQueries({
	@NamedQuery(name="State.findAll", query="SELECT s FROM State s"),
	@NamedQuery(name="State.findState", query="SELECT s FROM State s WHERE s.state = :state")
})
public class State implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@Column(name="state", nullable=false, updatable=false, unique=true)
	private String state;

	@Column(name="state_abbrev", nullable=false, updatable=false, unique=true)
	private String stateAbbrev;
	
	protected State() {}

	public State(String state, String stateAbbrev) {
		super();
		this.state = state;
		this.stateAbbrev = stateAbbrev;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateAbbrev() {
		return stateAbbrev;
	}

	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((stateAbbrev == null) ? 0 : stateAbbrev.hashCode());
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
		State other = (State) obj;
		if (id != other.id)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (stateAbbrev == null) {
			if (other.stateAbbrev != null)
				return false;
		} else if (!stateAbbrev.equals(other.stateAbbrev))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", state=" + state + ", stateAbbrev=" + stateAbbrev + "]";
	}
}
