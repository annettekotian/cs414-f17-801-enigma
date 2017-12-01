package edu.colostate.cs.cs414.enigma.handler.builder;

import javax.mail.internet.AddressException;

import edu.colostate.cs.cs414.enigma.entity.Manager;

public class ManagerBuilder extends GymSystemUserBuilder {

	public Manager createManager() throws AddressException {
		this.setUserLevel("MANAGER");
		Manager manager = new Manager(this.createPersonalInformation(), this.createUser());
		getDao().persist(manager);
		return manager;
	}
}
