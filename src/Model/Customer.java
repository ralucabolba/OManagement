package Model;
import java.util.*;

public class Customer 
{
	private int id;
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private String cnp;
	private String username;
	private String password;
	
	/**
	 * Constructor of the class
	 * @param id : id's name
	 * @param name : customer's name
	 * @param address : customer's address
	 * @param phoneNumber : customer's phoneNumber
	 * @param email : customer's email
	 * @param cnp : customer's unique identifier
	 * @param username : customer's user name
	 * @param password : customer's password
	 */
	public Customer(int id, String name, String address, String phoneNumber, String email, String cnp, String username, String password)
	{
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.cnp = cnp;
		this.username = username;
		this.password = password;
	}
	/**
	 * Returns the customer's id
	 * @return id
	 */
	public int getId()
	{
		return this.id;
	}
	/**
	 * Returns the customer's name
	 * @return name
	 */
	public String getName()
	{
		return this.name;
	}
	/**
	 * Returns the customer's address
	 * @return address
	 */
	public String getAddress()
	{
		return this.address;
	}
	/**
	 * Returns the customer's phone number
	 * @return phoneNumber
	 */
	public String getPhoneNumber()
	{
		return this.phoneNumber;
	}
	/**
	 * Returns the customer's email
	 * @return email
	 */
	public String getEmail()
	{
		return this.email;
	}
	/**
	 * Return the customer's unique identifier also called cnp
	 * @return cnp
	 */
	public String getCnp()
	{
		return this.cnp;
	}
	/**
	 * Returns the customer's user name
	 * @return username
	 */
	public String getUsername()
	{
		return this.username;
	}
	/**
	 * Returns the customer's password
	 * @return password
	 */
	public String getPassword()
	{
		return this.password;
	}
	/**
	 * Sets the phone number with a new one
	 * @param newPhoneNumber the new phone number
	 */
	public void setPhoneNumber(String newPhoneNumber)
	{
		assert newPhoneNumber.length() == 10;
		this.phoneNumber = newPhoneNumber;
	}
	/**
	 * Sets the customer's email with a new one
	 * @param newEmail the new email
	 */
	public void setEmail(String newEmail)
	{
		this.email = newEmail;
	}
	/**
	 * Sets the customer's password with a new one
	 * @param newPassword the new password
	 */
	public void setPassword(String newPassword)
	{
		this.password = newPassword;
	}
	
	public void setName(String newName)
	{
		this.name = newName;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	
	public void placeOrder(Vector<Product> products, Vector<Integer> noProducts)
	{
		OPDept.addOrder(this, products, noProducts);
	}
	
	public void receiveOrder(Order order)
	{
		if(order.getStatus().equals("InProgress"))
		{
			order.setStatus("Completed");
		}
	}
}
