package edu.colostate.cs.cs414.enigma.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="machine")
@NamedQueries({
	@NamedQuery(name="Machine.findAll", query="SELECT m FROM Machine m"),
	@NamedQuery(name="Machine.findByName", query="SELECT m FROM Machine m WHERE m.name = :name"),
	@NamedQuery(name="Machine.findId", query="SELECT m FROM Machine m WHERE m.id = :id")
})
public class Machine {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;
	
	@Column(name="name", unique=true, nullable=false, updatable=true)
	private String name;
	
	@Column(name="picture_location", unique=true, nullable=false, updatable=true)
	private String pictureLocation;
	
	@Column(name="quantity", unique=true, nullable=false, updatable=true)
	private int quantity;
	
	protected Machine() {}
	
	public Machine(String name, String pictureLocation, int quantity) throws MachineException {
		this.name = name;
		this.pictureLocation = pictureLocation;
		if(quantity < 0) {
			throw new MachineException("Machine quantity cannot less than zero");
		}
		this.quantity = quantity;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pictureLocation == null) ? 0 : pictureLocation.hashCode());
		result = prime * result + quantity;
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
		Machine other = (Machine) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} if (pictureLocation == null) {
			if (other.pictureLocation != null)
				return false;
		} else if (!pictureLocation.equals(other.pictureLocation))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Machine [id=" + id + ", name=" + name + ", pictureLocation=" + pictureLocation + ", quantity="
				+ quantity + "]";
	}
}
