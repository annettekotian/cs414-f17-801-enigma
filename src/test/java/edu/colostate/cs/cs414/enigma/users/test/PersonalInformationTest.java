package edu.colostate.cs.cs414.enigma.users.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.listeners.EntityManagerFactoryListener;
import edu.colostate.cs.cs414.enigma.users.PersonalInformation;
import edu.colostate.cs.cs414.enigma.users.PersonalInformationPK;

public class PersonalInformationTest {

	private static EntityManagerFactoryListener emfl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emfl = new EntityManagerFactoryListener();
		emfl.contextInitialized(null);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		emfl.contextDestroyed(null);
		emfl = null;
	}

	@Test
	public void commitRemovePersonalInformation() {
		String email = "johndoe@gmail.com";
		String firstName = "John";
		String lastName = "Doe";
		String phoneNumber = "555-123-4567";
		int healthInsuranceId = 1;
		
		PersonalInformationPK id = new PersonalInformationPK();
		id.setHealthInsuranceId(healthInsuranceId);
		
		PersonalInformation info = new PersonalInformation();
		info.setId(id);
		info.setEmail(email);
		info.setFirstName(firstName);
		info.setLastName(lastName);
		info.setPhoneNumber(phoneNumber);
		
		PersonalInformation.commitPersonalInformation(info);
		List<PersonalInformation> infoList = PersonalInformation.getAllPersonalInformation();
		assertEquals("Failed to get personal information from the database", 1, infoList.size());
		assertEquals("Health insurance ID not set correctly", healthInsuranceId, infoList.get(0).getHealthInsurance().getId());
		
		PersonalInformation.removePersonalInformation(infoList.get(0));
		infoList = PersonalInformation.getAllPersonalInformation();
		assertEquals("Failed to remove personal information from database", 0, infoList.size());
	}

}
