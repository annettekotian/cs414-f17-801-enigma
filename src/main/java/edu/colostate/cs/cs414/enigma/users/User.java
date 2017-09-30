package edu.colostate.cs.cs414.enigma.users;

import java.io.Serializable;
import javax.persistence.*;

import edu.colostate.cs.cs414.enigma.listeners.EntityManagerFactoryListener;


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

	@EmbeddedId
	private UserPK id;

	@Column(nullable=false, length=32)
	private String password;

	@Column(nullable=false, length=16)
	private String username;

	//bi-directional many-to-one association to UserLevel
	@ManyToOne
	@JoinColumn(name="user_level_id", nullable=false, insertable=false, updatable=false)
	private UserLevel userLevel;

	public User() {
	}

	public UserPK getId() {
		return this.id;
	}

	public void setId(UserPK id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserLevel getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}

	public static User findUser(String userName) {
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		Query query = em.createNamedQuery("User.findUser");
		query.setParameter("name", userName);
		try {
			return (User) query.getSingleResult();
		} catch(javax.persistence.NoResultException e) {
			return null;
		}
	}
}