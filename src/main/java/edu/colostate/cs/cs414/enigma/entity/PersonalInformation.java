package edu.colostate.cs.cs414.enigma.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import edu.colostate.cs.cs414.enigma.listeners.EntityManagerFactoryListener;


/**
 * The persistent class for the personal_information database table.
 * 
 */
@Entity
@Table(name="personal_information")
@NamedQueries({
	@NamedQuery(name="PersonalInformation.findAll", query="SELECT p FROM PersonalInformation p")
})
public class PersonalInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=255)
	private String email;

	@Column(name="first_name", nullable=false, length=45)
	private String firstName;

	@Column(name="last_name", nullable=false, length=45)
	private String lastName;

	@Column(name="phone_number", nullable=false, length=45)
	private String phoneNumber;

	//uni-directional many-to-one association to HealthInsurance
	@ManyToOne
	@JoinColumn(name="health_insurance_id", nullable=false, insertable=false, updatable=false)
	private HealthInsurance healthInsurance;

	public PersonalInformation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public HealthInsurance getHealthInsurance() {
		return this.healthInsurance;
	}

	public void setHealthInsurance(HealthInsurance healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

	public static void commitPersonalInformation(PersonalInformation info) throws IllegalArgumentException {
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		em.getTransaction().begin();
		em.persist(info);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void removePersonalInformation(PersonalInformation info) {
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		PersonalInformation infoUser = em.find(PersonalInformation.class, info.getId());
		em.getTransaction().begin();
		em.remove(infoUser);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<PersonalInformation> getAllPersonalInformation() {
		EntityManager em = EntityManagerFactoryListener.createEntityManager();
		Query query = em.createNamedQuery("PersonalInformation.findAll");
		List<PersonalInformation> info = (List<PersonalInformation>) query.getResultList();
		em.close();
		return info;
	}
}