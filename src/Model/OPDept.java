package Model;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;


public class OPDept 
{
	private static ResultSet rs;
	private static PreparedStatement pst;
	private static Connection connection;
	private static int indexOrder = 0;
	private static Vector<Order> orders;
	private static Warehouse warehouse;
	
	public OPDept(Connection connection)
	{
		this.connection = connection;
		orders = new Vector<Order>();
		warehouse = new Warehouse(connection);
		getOrdersFromDatabase();
	}
	
	public void getOrdersFromDatabase()
	{
		ResultSet rs2, rs3;
		PreparedStatement pst2, pst3;
		try
		{
			Customer c = null;
			Vector<Product> prod = new Vector<Product>();
			Vector<Integer> noProd = new Vector<Integer>();
			pst = connection.prepareStatement("select * from orders");
			rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("IdOrder");
				String status = rs.getString("statusOrder");
				Date date = rs.getDate("dateOrder");
				int idCustomer = rs.getInt("idCustomer");
				
				pst2 = connection.prepareStatement("select product.nameProduct, product.idProduct, product.quantity, product.price, "
						+ "product_order.noProducts from product, product_order where product.idProduct = product_order.idProduct and"
						+ " product_order.idOrder = " + id);
				rs2 = pst2.executeQuery();
				
				prod.removeAllElements();
				noProd.removeAllElements();
				
				while(rs2.next())
				{
					String nameP = rs2.getString("product.nameProduct");
					int idP = rs2.getInt("product.idProduct");
					int quantityP = rs2.getInt("product.quantity");
					float priceP = rs2.getFloat("product.price");
					int no = rs2.getInt("product_order.noProducts");
					
					prod.add(new Product(idP, nameP, quantityP, priceP));
					noProd.add(new Integer(no));
				}
				
				pst3 = connection.prepareStatement("select * from customer where idCustomer = " + idCustomer);
				rs3 = pst3.executeQuery();
				
				while(rs3.next())
				{
					int idC = rs3.getInt("idCustomer");
					String nameC = rs3.getString("nameCustomer");
					String address = rs3.getString("address");
					String email = rs3.getString("email");
					String phone = rs3.getString("phoneNumber");
					String cnp = rs3.getString("cnp");
					String username = rs3.getString("username");
					String password = rs3.getString("passwordCustomer");
					
					c = new Customer(idC, nameC, address, phone, email, cnp, username, password);
				}
				Order o = new Order(id, date, c, new Vector<Product>(prod), new Vector<Integer>(noProd));
				o.setStatus(status);
				orders.add(o);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error getting the orders from database " + e);
		}
	}
	/**
	 * Method that returns the warehouse of the OP Department
	 * @return warehouse
	 */
	
	public Warehouse getWarehouse()
	{
		return this.warehouse;
	}
	/**
	 * Method that returns the orders of the OP Department
	 * @return orders
	 */
	public Vector<Order> getOrders()
	{
		return this.orders;
	}
	/**
	 * Method that adds a customer to the database
	 * @param customer - the customer to be added
	 */
	public static boolean addCustomer(Customer customer)
	{
		try
		{
			pst = connection.prepareStatement("select idCustomer from customer where username = '" + customer.getUsername() + "'");
			rs = pst.executeQuery();
			
			if(rs.next())
			{
				int id = rs.getInt("idCustomer");
				if(id>0)
				{
					JOptionPane.showMessageDialog(null, "Please choose another username. This one is taken");
					return false;
				}
			}
			
			pst = connection.prepareStatement("insert into customer values (" + customer.getId() + ", '" + customer.getName() + "', '" + 
												customer.getAddress() + "', '" + customer.getPhoneNumber() + "', '"+ customer.getEmail() + "', '"
												+customer.getCnp() + "', '" + customer.getUsername() + "', '" + customer.getPassword() + "')");
			pst.executeUpdate();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error adding a new customer" + e);
			return false;
		}
		return true;
		
	}
	public static void addOrder(Customer customer, Vector<Product> products, Vector<Integer> noProducts)
	{
		int max = 0;
		Random rnd = new Random();
		indexOrder += rnd.nextInt(10) + 1;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		//date = dateFormat.format(date);
		
		Order newOrder = new Order(indexOrder, date, customer, products, noProducts);
		orders.add(newOrder);
		
		Iterator<Product> prod = products.iterator();
		Iterator<Integer> noProd = noProducts.iterator();
		
		try
		{
			pst = connection.prepareStatement("insert into orders values (" + indexOrder + ", '"
					+ newOrder.getStatus() + "', '" + dateFormat.format(date) + "', " + customer.getId() + ")");
			pst.executeUpdate();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error creating an order " + e);
		}
		
		while(prod.hasNext() && noProd.hasNext())
		{
			try
			{
				
				pst = connection.prepareStatement("select max(id) from product_order");
				rs = pst.executeQuery();
				
				if(rs.next())
				{
					max = rs.getInt("max(id)") + 1;
				}
				pst = connection.prepareStatement("insert into product_order values (" + max + ", "+ prod.next().getId() + ", " + noProd.next() + ", " + indexOrder + ")");
				pst.executeUpdate();
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "error adding an order" + e);
			}
		}	
	}
	/**
	 * Method that processes an order. First it seeks the products in 
	 * the warehouse, and if they are on stock, then the number of them is decreased.
	 */
	public boolean processOrder(Order order)
	{
		boolean found;
		int index, oldQuantity, i, indexOrder;
		indexOrder = orders.indexOf(order);
		
		if(indexOrder != -1 && order.getStatus().equals("Created"))
		{
			Vector<Product> products = order.getProducts();
			Vector<Integer> noProducts = order.getNoProducts();
	
			for(Product p : products)
			{
				i = products.indexOf(p);
				
						index = warehouse.getProducts().indexOf(p);
						if (index != -1)
						{
							oldQuantity = warehouse.getProducts().get(index).getQuantity();
							if(oldQuantity >= noProducts.get(i))
							{
								oldQuantity-= noProducts.get(i);
							
								warehouse.getProducts().get(index).setQuantity( oldQuantity );
								
								try
								{
									pst = connection.prepareStatement("update product set quantity = " + oldQuantity + " where idProduct = " + p.getId());
									pst.executeUpdate();
								}
								catch(Exception e)
								{
									JOptionPane.showMessageDialog(null, "error processing " + e);
								}
							}
							else
							{
								return false;
							}
						}
						else
						{
							return false;
						}
			}
			
		}
		else
		{
			return false;
		}
		order.setStatus("InProgress");
		
		try
		{
			pst = connection.prepareStatement("update orders set statusOrder = 'InProgress' where idOrder = " + order.getId());
			pst.executeUpdate();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error updating the status of the order " + e);
		}
		
		orders.set(indexOrder, order);
		
		return true;
	}
}
