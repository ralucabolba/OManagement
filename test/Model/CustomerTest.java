package Model;

import java.util.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import static org.testng.Assert.*;

public class CustomerTest 
{
	Customer c;
	  @Test
	  public void testSetName() 
	  {
		  c.setName("Marc Ioana");
		  assertEquals(c.getName(), "Marc Ioana");
	  }
	  @Test
	  public void testSetPassword()
	  {
		  c.setPassword("abracadabra");
		  assertEquals(c.getPassword(), "abracadabra");
	  }
	  @Test
	  public void testSetPhone()
	  {
		  c.setPhoneNumber("0789234817");
		  assertEquals(c.getPhoneNumber(), "0789234817");
	  }
	  @BeforeTest
	  public void beforeTest() 
	  {
		  c = new Customer(1, "Marc Mariana", "str. Doinei, nr. 5, Cluj-Napoca", "0723456432", "mary@gmail.com", "2930413253456", "mary21", "mariana");
	  }

}
