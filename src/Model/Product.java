package Model;
/*
 * Author : Bolba Raluca
 */
public class Product implements Comparable
{
	private int id;
	private String name;
	private int quantity;
	private float price;
	
	public Product(int id, String name, int quantity, float price)
	{
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	/**
	 * Returns the id of the product
	 * @return id
	 */
	public int getId()
	{
		return this.id;
	}
	/**
	 * Returns the name of the product
	 * @return product's name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Returns the quantity of the product
	 * @return product's quantity 
	 */
	public int getQuantity()
	{
		return quantity;
	}
	/**
	 * Returns the price of product
	 * @return product's price
	 */
	public float getPrice()
	{
		return price;
	}
	
	public void setName(String newName)
	{
		this.name = newName;
	}
	/**
	 * Sets the quantity value
	 * @param newCantity the new quantity for product
	 */
	public void setQuantity(int newQuantity)
	{
		this.quantity = newQuantity;
	}
	
	public void setPrice(float newPrice)
	{
		this.price = newPrice;
	}
	/**
	 *Returns the product in a string form
	 *@return product as a string
	 */
	public String toString()
	{
		return this.name + " pret: " + this.price + " lei" + " cantitate: " + this.quantity;
	}
	@Override
	public int compareTo(Object arg0) 
	{
		if (this.getId() < ((Product)arg0).getId())
		{
			return -1;
		}
		if (this.getId() > ((Product)arg0).getId())
		{
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object arg0) 
	{
		return this.getId() == ((Product)arg0).getId();
	}
	
}
