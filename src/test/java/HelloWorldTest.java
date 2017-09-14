import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HelloWorldTest {

	HelloWorld helloWorld;
	
	@Before
	public void setUp() throws Exception {
		helloWorld = new HelloWorld();
	}

	@After
	public void tearDown() throws Exception {
		helloWorld = null;
	}

	@Test
	public void testGetGreetings() {
		assertNotNull("Hello world should return a greeting", helloWorld.getGreeting());
	}
}
