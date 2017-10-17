package edu.colostate.cs.cs414.enigma.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.colostate.cs.cs414.enigma.entity.Membership;
import edu.colostate.cs.cs414.enigma.listener.EntityManagerFactoryListener;

public class MembershipDaoTest {
	
	private static EntityManagerFactoryListener emfl;
	private List<Membership> addedMemberships;
	private MembershipDao membershipDao;

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

	@Before
	public void setUp() throws Exception {
		addedMemberships = new ArrayList<Membership>();
		membershipDao = new MembershipDao();
	}

	@After
	public void tearDown() throws Exception {
		for(int i=0; i<addedMemberships.size(); i++) {
			membershipDao.remove(addedMemberships.get(i));
		}
		membershipDao.close();
		addedMemberships = null;
	}

	@Test
	public void addNewMembershipType() {
		Membership newMembership = new Membership("Life time");
		membershipDao.persist(newMembership);
		membershipDao.commit();
		assertEquals("Failed to add new membership type to the db", membershipDao.findByMembershipId(newMembership.getId()), newMembership);
		addedMemberships.add(newMembership);
	}

}
