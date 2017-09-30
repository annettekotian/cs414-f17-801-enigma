package edu.colostate.cs.cs414.enigma.users;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import edu.colostate.cs.cs414.enigma.listeners.EntityManagerFactoryListener;


/**
 * The persistent class for the user_level database table.
 * 
 */
@Entity
@Table(name="user_level")
@NamedQueries({
	@NamedQuery(name="UserLevel.findAll", query="SELECT u FROM UserLevel u"),
	@NamedQuery(name="UserLevel.findLevel", query="SELECT u FROM UserLevel u WHERE u.description = :level")
})
public class UserLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=255)
	private String description;

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

	public static List<UserLevel> getUserLevels() {
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		Query query = em.createNamedQuery("UserLevel.findAll");
		List<UserLevel> results;
		try {
			results = (List<UserLevel>) query.getResultList();
		} catch(javax.persistence.NoResultException e) {
			results = null;
		}
		em.close();
		return results;
	}
	
	public static UserLevel findUser(String level) {
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		Query query = em.createNamedQuery("UserLevel.findLevel");
		query.setParameter("level", level);
		UserLevel userLevel;
		try {
			userLevel = (UserLevel) query.getSingleResult();
		} catch(javax.persistence.NoResultException e) {
			userLevel = null;
		}
		em.close();
		return userLevel;
	}
}