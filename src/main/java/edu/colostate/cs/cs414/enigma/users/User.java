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

	//uni-directional many-to-one association to UserLevel
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
		User user;
		try {
			user = (User) query.getSingleResult();
		} catch(javax.persistence.NoResultException e) {
			user = null;
		}
		em.close();
		return user;
	}
	
	public static void commitUser(User user) throws IllegalArgumentException {
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void removeUser(User user) {
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		User removedUser = em.find(User.class, user.getId());
		em.getTransaction().begin();
		em.remove(removedUser);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void modifyPassword(UserPK userId, String password) {
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		User modifyUser = em.find(User.class, userId);
		em.getTransaction().begin();
		modifyUser.setPassword(password);
		em.getTransaction().commit();
		em.close();
	}
	
	public static boolean authenticate(String userName, String password) {
		User user = User.findUser(userName);
		if(user == null) {
			return false;
		}
		return user.getPassword().equals(password);
	}
}