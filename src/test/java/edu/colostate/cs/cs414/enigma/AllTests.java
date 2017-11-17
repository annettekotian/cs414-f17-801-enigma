package edu.colostate.cs.cs414.enigma;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.colostate.cs.cs414.enigma.dao.EntityManagerDaoTest;
import edu.colostate.cs.cs414.enigma.handler.CustomerHandlerTest;
import edu.colostate.cs.cs414.enigma.handler.LoginHandlerTest;
import edu.colostate.cs.cs414.enigma.handler.ManagerHandlerTest;
import edu.colostate.cs.cs414.enigma.handler.SystemHandlerTest;

@RunWith(Suite.class)
@SuiteClasses({ManagerHandlerTest.class, EntityManagerDaoTest.class, CustomerHandlerTest.class, 
	LoginHandlerTest.class, SystemHandlerTest.class})
public class AllTests {

}
