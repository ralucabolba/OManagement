package Model;
import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;


public class Warehouse 
{
	private Connection connection;
	private PreparedStatement pst;
	private ResultSet rs;
	
	private Vector<Product> products;
	
	public Warehouse(Connection connection)
	{
		this.connection = connection;
		products = new Vector<Product>();
		
		getProductsFromDatabase();
	}
	
	public void getProductsFromDatabase()
	{
		//get the products from database and store them in the warehouse
		
				try
				{
					pst = connection.prepareStatement("select * from product");
					rs = pst.executeQuery();
					
					while(rs.next())
					{
						int id = rs.getInt("idProduct");
						String name = rs.getString("nameProduct");
						int quantity = rs.getInt("quantity");
						float price = rs.getFloat("price");
						
						Product prod = new Product(id, name, quantity, price);
						products.add(prod);
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null , "Error warehouse : " + e);
				}
	}
	public Vector<Product> getProducts()
	{
		return this.products;
	}
	public void addProduct(Product product)
	{
		this.products.add(product);
		
		try
		{
			pst = connection.prepareStatement("insert into product values (" + product.getId() + ", '" + product.getName()+ "', " + product.getQuantity() + ", " + product.getPrice() + ")");
			pst.executeUpdate();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "The product could not be added to warehouse. " + e);
		}
	}
	
	public boolean removeProduct(Product product)
	{	
		try
		{
			pst = connection.prepareStatement("delete from product where idProduct = " + product.getId());
			pst.executeUpdate();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error removing a product : " + e);
			return false;
		}
		return this.products.removeElement(product);
	}
	
	public int numberProducts()
	{
		return this.products.size();
	}
	
	public Vector<Product> getUnderstockProducts()
	{
		int limit = 5;
		Vector<Product> info = new Vector<Product>();
		
		for(Product p : this.products)
		{
			if(p.getQuantity() < limit)
			{
				info.add(p);
			}
		}
		
		return info;
	}
	
	public Vector<Product> getOverStockProducts()
	{
		int limit = 50;
		Vector<Product> info = new Vector<Product>();
		
		for(Product p : this.products)
		{
			if(p.getQuantity() > limit)
			{
				info.add(p);
			}
		}
		return info;
	}
	
	
	
}
