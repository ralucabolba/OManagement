package Model;


import java.util.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import static org.testng.Assert.*;
public class ProductTest 
{
	Product p1, p2, p3;
	@BeforeTest
	public void init()
	{
		p1 = new Product(1, "Rimmel Stay Matte", 10, 15);
		p2 = new Product(1, "Avon Lipstick", 20, 10);
		p3 = new Product(2, "Matte powder", 30, 12);
	}
	  @Test
	  public void testEquals() 
	  {
		  assertTrue(p1.equals(p2));
	  }
	  @Test
	  public void testCompare()
	  {
		  assertEquals(p1.compareTo(p3), -1);
		  assertEquals(p3.compareTo(p2), 1);
	  }
}
