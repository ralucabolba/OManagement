package Model;

import java.util.Date;

import java.util.Vector;

public class Order 
{
	private int id;
	private String status;
	private Date date;
	private Customer customer;
	private Vector<Product> products;
	private Vector<Integer> noProducts;
	
	/**
	 * Class' constructor
	 * @param id		-	order's unique identifier
	 * @param date		-	the date when the order was placed
	 * @param customer	-	the customer who placed the order
	 * @param products	-	the products which were ordered
	 * @param noProducts-	the number of each product that were ordered
	 */
	public Order(int id, Date date, Customer customer, Vector<Product> products, Vector<Integer> noProducts)
	{
		this.id = id;
		this.date = date;
		this.customer = customer;
		this.products = products;
		this.noProducts = noProducts;
		this.status = "Created";
	}
	/**
	 * Returns the order's id
	 * @return id
	 */
	public int getId()
	{
		return this.id;
	}
	/**
	 * Return the order's date
	 * @return date
	 */
	public Date getDate()
	{
		return this.date;
	}
	
	/**
	 * Returns the customer who placed the order
	 * @return customer
	 */
	public Customer getCustomer()
	{
		return this.customer;
	}
	/**
	 * Returns the products which were ordered
	 * @return products
	 */
	public Vector<Product> getProducts()
	{
		return this.products;
	}
	/**
	 * Returns the number of products which were ordered
	 * @return noProducts
	 */
	public Vector<Integer> getNoProducts()
	{
		return this.noProducts;
	}
	/**
	 * Returns the status of the order
	 * @return status
	 */
	public String getStatus()
	{
		return this.status;
	}
	/**
	 * Sets the status of the order with a new one.
	 * @param newStatus - the new status of the order (InProgress, Completed)
	 */
	public void setStatus(String newStatus)
	{
		this.status = newStatus;
		
	}
}
