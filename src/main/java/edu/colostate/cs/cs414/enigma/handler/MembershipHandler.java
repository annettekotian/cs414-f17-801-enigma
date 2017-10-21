package edu.colostate.cs.cs414.enigma.handler;

import java.util.ArrayList;
import java.util.List;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDao;
import edu.colostate.cs.cs414.enigma.entity.Customer;
import edu.colostate.cs.cs414.enigma.entity.Membership;

public class MembershipHandler {
	
	private EntityManagerDao dao;
	
	public MembershipHandler() {
		dao = new EntityManagerDao();
	}
	
	public void close() {
		dao.close();
	}

	public List<Membership> getMembershipStatus() {
		List rawMemberships = dao.query("Membership.findAll", null);
		List<Membership> memberships = new ArrayList<Membership>();
		for(int i=0; i<rawMemberships.size(); i++) {
			memberships.add((Membership) rawMemberships.get(i));
		}
		close();
		return memberships;
	}
}
