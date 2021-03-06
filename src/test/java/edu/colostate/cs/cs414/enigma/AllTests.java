package edu.colostate.cs.cs414.enigma;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.colostate.cs.cs414.enigma.builder.*;
import edu.colostate.cs.cs414.enigma.dao.EntityManagerDaoTest;
import edu.colostate.cs.cs414.enigma.handler.CustomerHandlerTest;
import edu.colostate.cs.cs414.enigma.handler.LoginHandlerTest;
import edu.colostate.cs.cs414.enigma.handler.ManagerHandlerTest;
import edu.colostate.cs.cs414.enigma.handler.SystemHandlerTest;
import edu.colostate.cs.cs414.enigma.handler.TrainerHandlerTest;

@RunWith(Suite.class)
@SuiteClasses({ManagerHandlerTest.class, EntityManagerDaoTest.class, CustomerHandlerTest.class, 
	LoginHandlerTest.class, SystemHandlerTest.class, TrainerHandlerTest.class, 
	ManagerBuilderTest.class, TrainerBuilderTest.class, CustomerBuilderTest.class})
public class AllTests {

}
