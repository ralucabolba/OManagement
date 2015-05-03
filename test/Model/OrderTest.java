package Model;

import java.util.*;


import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import static org.testng.Assert.*;

public class OrderTest 
{
	Customer c;
	Order order;
	@BeforeTest
	public void init()
	{
		Vector<Product> products = new Vector<Product>();
		Vector<Integer> noProd = new Vector<Integer>();
		
		noProd.add(1);
		noProd.add(2);
		noProd.add(1);
		
		Product p1, p2, p3;
		p1 = new Product(1, "Rimmel Stay Matte", 20, 12);
		p2 = new Product(2, "Avon Lipstick", 10, 7);
		p3 = new Product(3, "BB Cream Garnier", 45, 10);
		
		products.add(p1);
		products.add(p2);
		products.add(p3);
		
		c = new Customer(1, "Marc Mariana", "str. Doinei, nr. 5, Cluj-Napoca", "0723456432", "mary@gmail.com", "2930413253456", "mary21", "mariana");
		
		order = new Order(1, new Date(), c, products, noProd);
	}
	@Test
	public void testStatus()
	{
		order.setStatus("Created");
		assertTrue(order.getStatus().equals("Created"));
	}
	@Test
	public void testReceivedOrderStatus()
	{
		order.setStatus("InProgress");
		c.receiveOrder(order);
		assertTrue(order.getStatus().equals("Completed"));
	}
	
}
