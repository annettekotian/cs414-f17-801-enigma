package edu.colostate.cs.cs414.enigma.handler;

import java.util.List;

import edu.colostate.cs.cs414.enigma.dao.MembershipDao;
import edu.colostate.cs.cs414.enigma.entity.Membership;

public class MembershipHandler {
	
	private MembershipDao membershipDao;
	
	public MembershipHandler() {
		membershipDao = new MembershipDao();
	}
	
	public void close() {
		membershipDao.close();
	}

	public List<Membership> getMembershipStatus() {
		return membershipDao.getMembershipStatus();
	}
}
