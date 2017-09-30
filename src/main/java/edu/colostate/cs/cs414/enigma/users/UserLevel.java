package edu.colostate.cs.cs414.enigma.users;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user_level database table.
 * 
 */
@Entity
@Table(name="user_level")
@NamedQuery(name="UserLevel.findAll", query="SELECT u FROM UserLevel u")
public class UserLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=255)
	private String description;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="userLevel")
	private List<User> users;

	public UserLevel() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserLevel(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserLevel(null);

		return user;
	}

}