package edu.colostate.cs.cs414.enigma.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import edu.colostate.cs.cs414.enigma.entity.PersonalInformation;

public class PersonalInformationDao extends EntityManagerDao<PersonalInformation> {

	/**
	 * Get a personal information by the unique primary ID key from the GymSystem database
	 * personal_information table. Note that this function will attach the UserLevel entity with
	 * the underlying entity manager.
	 * @param id Primary ID key.
	 * @return PersonalInformation object on success, else null.
	 * @see PersonalInformation
	 */
	public PersonalInformation findPersonalInformationById(int id) {
		return this.getEntityManager().find(PersonalInformation.class, id);
	}
	
	/**
	 * Get all the personal information in the GymSystem database personal_information table.
	 * Note that this function will attach the UserLevel entity with the underlying entity
	 * manager.
	 * @return List of PersonalInformation entities.
	 * @see PersonalInformation
	 */
	public List<PersonalInformation> getAllPersonalInformation() {
		
		// Issue a NamedQuery found in UserLevel.class
		Query query = this.getEntityManager().createNamedQuery("PersonalInformation.findAll");
		List<PersonalInformation> personalInformation = new ArrayList<PersonalInformation>();
		List<?> results = query.getResultList();
		for(int i=0; i<results.size(); i++) {
			personalInformation.add((PersonalInformation) results.get(i));
		}
		return personalInformation;
	}
}
