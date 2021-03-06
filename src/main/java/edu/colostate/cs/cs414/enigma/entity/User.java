package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.findUser", query="SELECT u FROM User u WHERE u.username = :name")
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, updatable=false)
	private int id;

	@Column(name="password", nullable=false, updatable=true, length=32)
	private String password;

	@Column(name="username", unique=true, nullable=false, updatable=true, length=16)
	private String username;

	//uni-directional many-to-one association to UserLevel
	@ManyToOne
	@JoinColumn(name="user_level_id", nullable=false, updatable=true)
	private UserLevel userLevel;

	public User(String username, String password, UserLevel userLevel) {
		this.setPassword(password);
		this.setUsername(username);
		this.setUserLevel(userLevel);
	}
	
	protected User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		if(password == null) {
			throw new IllegalArgumentException("Password cannot be empty");
		}
		if(password.length() < 8) {
			throw new IllegalArgumentException("Password must be 8 characters");
		}
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		if(username == null) {
			throw new IllegalArgumentException("Username cannot be empty");
		}
		if(username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be empty");
		}
		this.username = username;
	}

	public UserLevel getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(UserLevel userLevel) {
		if(userLevel == null) {
			throw new IllegalArgumentException("User level cannot be empty");
		}
		this.userLevel = userLevel;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userLevel == null) ? 0 : userLevel.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userLevel == null) {
			if (other.userLevel != null)
				return false;
		} else if (!userLevel.equals(other.userLevel))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", username=" + username + ", userLevel=" + userLevel
				+ "]";
	}
}