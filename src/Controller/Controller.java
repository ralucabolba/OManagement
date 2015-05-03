package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.StringUtils;

import java.util.*;

import View.SwingGUI;
import Model.*;

public class Controller 
{
	private SwingGUI gui;
	private OPDept department;
    private ResultSet rs = null, rs2 = null;
    private PreparedStatement pst = null, pst2 = null;
    private Connection connection = null;
    private int idCustomer = -1;
    private Customer actualCustomer;
    private Vector<Product> cart = new Vector<Product>();
    private Vector<Integer> noProductsCart = new Vector<Integer>();

	public Controller(OPDept department, SwingGUI gui, Connection connection)
	{
		this.department = department;
		this.gui = gui;
		this.connection = connection;
		gui.addListenerNewCustomer(new NewCustomerListener());
		gui.addListenerEnterAccount(new EnterAccountListener());
		gui.addListenerSaveChanges(new SaveChangesListener());
		gui.addListenerAddToCart(new AddToCartListener());
		gui.addListenerFinalizeOrder(new FinalizeOrderListener());
		gui.addListenerGoBack(new GoBackListener());
		gui.addListenerAddProduct(new AddProductListener());
		gui.addListenerRemoveProduct(new RemoveProductListener());
		gui.addListenerProcessOrder(new ProcessOrderListener());
		gui.addListenerGoBackAdmin(new GoBackAdminListener());
		gui.addListenerConfirm(new ConfirmListener());
	}
	
	public void addOrderToModel(DefaultTableModel model, int idCustomer)
	{
		int idC = 0;
		String id = null, status = null, date = null, product, quantity;
        Vector<String> prod = new Vector<String>();
        Vector<String> noProd = new Vector<String>();
        
        
        try{
        	if(idCustomer == -1)
        	{
        		pst = connection.prepareStatement("select idOrder, statusOrder, dateOrder, idCustomer from orders order by dateOrder asc");
        	}
        	else
        	{
        		pst = connection.prepareStatement("select idOrder, statusOrder, dateOrder from orders where idCustomer = " + idCustomer + " order by dateOrder asc");
        	}
        	rs = pst.executeQuery();
        	while(rs.next())
        	{
        		id = rs.getString("idOrder");
        		status = rs.getString("statusOrder");
        		date = rs.getString("dateOrder");
        		
        		if(idCustomer == -1)
        		{
        			idC = rs.getInt("idCustomer");
        		}
        		
        		pst2 = connection.prepareStatement("select product.nameProduct, product_order.noProducts from product, product_order where"
            			+ " product.idProduct = product_order.idProduct and product_order.idOrder = " + id);
            	
            	rs2 = pst2.executeQuery();
            	
            	while(rs2.next())
            	{
            		product = rs2.getString("product.nameProduct");
            		prod.add(product);
            		quantity = rs2.getString("product_order.noProducts");
            		noProd.add(quantity);
            	}
            	
            	if(idCustomer != -1)
            	{
            		model.addRow(new Object[]{id, status, date, String.join(", ", new Vector<String>(prod)), String.join(", ", new Vector<String>(noProd))});
            	}
            	else
            	{
            		model.addRow(new Object[]{id, status, date, idC, String.join(", ", new Vector<String>(prod)), String.join(", ", new Vector<String>(noProd))});
            	}
            	prod.removeAllElements();
                noProd.removeAllElements();
        	}
        	
        }
        catch(Exception e)
        {
        	JOptionPane.showMessageDialog(null, "Error" + e);
        }
        
	}
	
	public void addUModel(DefaultTableModel model)
	{
		Vector<Product> info = department.getWarehouse().getUnderstockProducts();
		
		for(Product p : info)
		{
			model.addRow(new Object[]{p.getId(), p.getName(), p.getQuantity(), p.getPrice()});
		}
	}
	
	public void addOModel(DefaultTableModel model)
	{
		Vector<Product> info = department.getWarehouse().getOverStockProducts();
		
		for(Product p : info)
		{
			model.addRow(new Object[]{p.getId(), p.getName(), p.getQuantity(), p.getPrice()});
		}
	}
	
	public void refreshAdminModels()
	{
		DefaultTableModel productModel , customerModel, orderModel, modelU, modelO;
		JComboBox box;
		
		productModel = gui.getAdminProdModel();
		customerModel = gui.getAdminCustomerModel();
		orderModel = gui.getAdminOrderModel();
		modelU = gui.getUModel();
		modelO = gui.getOModel();
		
		box = gui.getListProductsComboBox();
		box.removeAllItems();
		productModel.setRowCount(0);
		customerModel.setRowCount(0);
		orderModel.setRowCount(0);
		modelU.setRowCount(0);
		modelO.setRowCount(0);
		
		addProductModel(productModel, box);
		addOrderToModel(orderModel, -1);
		addCustomerModel(customerModel);
		addUModel(modelU);
		addOModel(modelO);
		
		gui.setAdminProdModel(productModel);
		gui.setAdminCustomerModel(customerModel);
		gui.setAdminOrderModel(orderModel);
		gui.setListProductsComboBox(box);
		gui.setUModel(modelU);
		gui.setOModel(modelO);
		gui.setNoProducts("The total number of products is " + department.getWarehouse().numberProducts());
	}
	
	public void refreshModels(int idCustomer)
	{
		DefaultTableModel model, productModel;
		JComboBox box;
		
		model = gui.getOrderModel();
		productModel = gui.getProductsModel();
		box = gui.getListProductsComboBox();
		box.removeAllItems();
		model.setRowCount(0);
		productModel.setRowCount(0);
		
		addOrderToModel(model, idCustomer);
    	addProductModel(productModel, box);
		gui.setOrderModel(model);
		gui.setProductsModel(productModel);
		gui.setListProductsComboBox(box);
	}
	
	public void addProductModel(DefaultTableModel modelProduct, JComboBox listProducts)
	{
		 String idProd, nameProduct, quantityProduct;
		 float priceProduct;
	       
	     try
	     {
	        pst = connection.prepareStatement("select idProduct, nameProduct, quantity, price from product");		
	        rs = pst.executeQuery();
	        while(rs.next())
	        {
	        	idProd = rs.getString("idProduct");
	        	nameProduct = rs.getString("nameProduct");
	        	quantityProduct = rs.getString("quantity");
	        	priceProduct = rs.getFloat("price"); 
	        		
	        	modelProduct.addRow(new Object[]{idProd, nameProduct, quantityProduct, priceProduct + " lei"});
	        	listProducts.addItem(nameProduct);
	       	}
	     }
	     catch(Exception e)
	     {
	        	JOptionPane.showMessageDialog(null, "Error" + e);
	     }
	}
	
	public void addCustomerModel(DefaultTableModel model)
	{
		String name, address, email, phone, cnp, username, password;
		int id;
		
		try
		{
			pst = connection.prepareStatement("select * from customer");
			rs = pst.executeQuery();
			
			while(rs.next())
			{
				id = rs.getInt("idCustomer");
				name = rs.getString("nameCustomer");
				address = rs.getString("address");
				cnp = rs.getString("cnp");
				email = rs.getString("email");
				phone = rs.getString("phoneNumber");
				username = rs.getString("username");
				password = rs.getString("passwordCustomer");
				
				model.addRow(new Object[]{id, name, address, email, phone, cnp, username, password});
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error showing customers : " + e);
		}
	}
	class NewCustomerListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			String[] info = gui.getCustomerInfo();
			
			try
			{
				pst = connection.prepareStatement("select max(idCustomer) from customer");
				rs = pst.executeQuery();
				
				if(rs.next())
				{
					int max = rs.getInt("max(idCustomer)") + 1;
					Customer customer = new Customer(max, info[0], info[1], info[2], info[3], info[4], info[5], info[6]);
					if(OPDept.addCustomer(customer))
					{
						JOptionPane.showMessageDialog(null, "Your account was created!");
					}
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Error " + e);
			}
		}
	}
	class EnterAccountListener implements ActionListener
	{
		String[] info = new String[2];
		
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			DefaultTableModel modelCart;
			
			info = gui.getLoginInfo();
			
			if(info[0].equals("admin") && info[1].equals("admin"))
			{
				gui.adminWindow();
				refreshAdminModels();
			}
			else
			{
				String sql = "SELECT * FROM customer WHERE username='"+ info[0] +"' AND passwordCustomer='"+info[1]+"'";
				String nameC = null, addressC = null, emailC = null, phoneC = null, cnpC = null;
		        try
		        {
		            pst = connection.prepareStatement(sql);
		            rs = pst.executeQuery();
		            while(rs.next())
		            {
		                idCustomer = rs.getInt("idCustomer");
		                nameC = rs.getString("nameCustomer");
		                addressC = rs.getString("address");
		                emailC = rs.getString("email");
		                phoneC = rs.getString("phoneNumber");
		                cnpC = rs.getString("cnp");
		            }
		            if(idCustomer != -1)
			        {
		            	actualCustomer = new Customer(idCustomer, nameC, addressC, phoneC, emailC, cnpC, info[0], info[1]);
		            	
			        	gui.myAccountWindow(idCustomer);
			        	refreshModels(idCustomer);
			        	
			        	modelCart = gui.getCartModel();
						modelCart.setRowCount(0);
						gui.setCartModel(modelCart);
						
			        	
			        }
		            else
		            	throw new IllegalArgumentException();
		            
		        }
		        catch(Exception exception)
		        {
		            JOptionPane.showMessageDialog(null, "Invalid username or password ! " + e);
		        }
			}
		}
	}
	class SaveChangesListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(idCustomer != -1)
			{
				String info[] = new String[6];
				info = gui.getChanges(); //Name, address, phone, email, password
				try
				{
					if(!info[0].equals(""))
					{
						pst = connection.prepareStatement("update customer set nameCustomer = '" + info[0] + "' where idCustomer = " + idCustomer);
						pst.executeUpdate();
						
						actualCustomer.setName(info[0]);
					}
					if(!info[1].equals(""))
					{
						pst = connection.prepareStatement("update customer set address = '" + info[1] + "' where idCustomer = " + idCustomer);
						pst.executeUpdate();
						
						actualCustomer.setAddress(info[1]);
					}
					if(!info[2].equals(""))
					{
						if(info[2].length() == 10)
						{
							pst = connection.prepareStatement("update customer set phoneNumber = '" + info[2] + "' where idCustomer = " + idCustomer);
							pst.executeUpdate();
							
							actualCustomer.setPhoneNumber(info[2]);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "The phone number is invalid.");
						}
					}
					
					if(!info[3].equals(""))
					{
						if( info[3].contains("@") && info[3].contains("."))
						{
							pst = connection.prepareStatement("update customer set email = '" + info[3] + "' where idCustomer = " + idCustomer);
							pst.executeUpdate();
							
							actualCustomer.setEmail(info[3]);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "The email is incorrect.");
						}
					}
					
					if(!info[4].equals(""))
					{
						if(info[4].length() >= 6)
						{
							pst = connection.prepareStatement("update customer set passwordCustomer = '" + info[4] + "' where idCustomer = " + idCustomer);
							pst.executeUpdate();
							
							actualCustomer.setPassword(info[4]);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "The password must contain at least 6 characters.");
						}
					}
	
					JOptionPane.showMessageDialog(null, "The changes were made.");
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Error making the changes " + ex);
				}
			}
			
		}
		
	}
	
	class AddToCartListener implements ActionListener
	{
		public void addProductToCart(DefaultTableModel modelCart, int idProduct, int quantity)
		{
			String nameProduct, priceProduct;
	        int oldQ;
	        boolean updatedRow = false;
	        
	        try{
	        	pst = connection.prepareStatement("select nameProduct, price from product where idProduct = " + idProduct);		
	        	rs = pst.executeQuery();
	        	while(rs.next())
	        	{
	        		nameProduct = rs.getString("nameProduct");
	        		priceProduct = rs.getString("price");
	        	
	        		for(int i = 0 ; i<modelCart.getRowCount() ; i++)
	        		{
	        			if(modelCart.getValueAt(i, 0).equals(nameProduct))
	        			{
	        				oldQ = Integer.parseInt(String.valueOf(modelCart.getValueAt(i, 2)));
	        				Integer newQ = new Integer(oldQ + quantity);
	        				modelCart.setValueAt( newQ.toString() , i, 2);
	        				modelCart.setValueAt(newQ * Float.parseFloat(priceProduct), i, 3);
	        				updatedRow = true;
	        			}
	        		}
	        		if(!updatedRow)
	        		{
	        			modelCart.addRow(new Object[]{nameProduct, priceProduct + " lei", quantity, String.valueOf(quantity * Float.parseFloat(priceProduct))});
	        		}
	        	}
	        }
	        catch(Exception e)
	        {
	        	JOptionPane.showMessageDialog(null, "Error" + e);
	        }
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			DefaultTableModel modelCart = new DefaultTableModel();
			int idProduct = 0 , q, quantity = 0, index;
			boolean cont = false;
			String nameProduct = null, productChosenName = gui.getProductChosenName();
			q = gui.getProductChosenQuantity();
			float price = 0;
			
			//get the product chosen's id
			try
			{
				pst = connection.prepareStatement("select idProduct from product where nameProduct = '" + productChosenName +"'");
				rs = pst.executeQuery();
				
				while(rs.next())
				{
					idProduct = rs.getInt("idProduct");
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Error getting the product chosen's id " + e);
			}
			
			if(q > 0)
			{
				Product chosenProduct;
				try
				{
					pst = connection.prepareStatement("select nameProduct, quantity, price from product where idProduct = " + idProduct);
					rs = pst.executeQuery();
					while(rs.next())
					{
						nameProduct = rs.getString("nameProduct");
						quantity = rs.getInt("quantity");
						price = rs.getFloat("price");
					}
					chosenProduct = new Product(idProduct, nameProduct, quantity, price);
					index = cart.indexOf(chosenProduct);
					if(index != -1)
					{
						noProductsCart.setElementAt(new Integer(q + noProductsCart.get(index)), index);
					}
					else
					{
						cart.add(chosenProduct);
						noProductsCart.add(new Integer(q));
					}
					
					modelCart = gui.getCartModel();
					
					this.addProductToCart(modelCart, idProduct, q);
					gui.setCartModel(modelCart);
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Error adding to cart " + e);
				}
			}
		}
	}
	class FinalizeOrderListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			DefaultTableModel modelCart;
			JComboBox box;
			
			actualCustomer.placeOrder(new Vector<Product>(cart), new Vector<Integer>(noProductsCart));
			JOptionPane.showMessageDialog(null, "Your order was finalized.");
			refreshModels(idCustomer);
			
			modelCart = gui.getCartModel();
			modelCart.setRowCount(0);
			gui.setCartModel(modelCart);
			
			cart.removeAllElements();
			noProductsCart.removeAllElements();
		}
	}
	class GoBackListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			gui.goBack();
		}	
	}
	
	class AddProductListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String[] info = new String[4];
			info = gui.getProductToAdd();
			
			int id = 0, quantity;
			float price;
			
			try
			{
				quantity = Integer.parseInt(info[1]);
				price = Float.parseFloat(info[2]);
				
				pst = connection.prepareStatement("select max(idProduct) from product");
				rs = pst.executeQuery();
				
				if(rs.next())
				{
					id = rs.getInt("max(idProduct)") + 1;
				}
				department.getWarehouse().addProduct(new Product(id, info[0], quantity, price));
				
				refreshAdminModels();
			}
			catch(NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "The parameters introduced are invalid!");
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex);
			}
		}
	}
	
	class RemoveProductListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			String name = gui.getProductChosenName();
			int id = 0, quantity = 0;
			float price = 0;
			
			try
			{
				pst = connection.prepareStatement("select * from product where nameProduct = '" + name + "'");
				rs = pst.executeQuery();
				
				while(rs.next())
				{
					id = rs.getInt("idProduct");
					quantity = rs.getInt("quantity");
					price = rs.getFloat("price");
				}
				
				Product product = new Product(id, name, quantity, price);
				if(!department.getWarehouse().removeProduct(product))
				{
					JOptionPane.showMessageDialog(null, "The product could not be removed.");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The product was removed.");
					refreshAdminModels();
					
				}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "The parameters are invalid");
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Error : " + e);
			}
		}
		
	}
	class ProcessOrderListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			DefaultTableModel modelOrder = new DefaultTableModel(), modelProduct = new DefaultTableModel();
			JComboBox box = new JComboBox();
			
			int id = gui.getIdOrderProcess();
			String status = "";
			
			if(id != -1)
			{
				try
				{
					pst = connection.prepareStatement("select * from orders where idOrder = " + id);
					rs = pst.executeQuery();
					
					while(rs.next())
					{
						status = rs.getString("statusOrder");
					}
					
					if(status.equals("InProgress"))
					{
						JOptionPane.showMessageDialog(null, "The order is already processed.");
					}
					else if(status.equals("Completed"))
					{
						JOptionPane.showMessageDialog(null, "The order was received by the customer, you can not process it.");
					}
					else if(status.equals("Created"))
					{
						Vector<Order> orders = department.getOrders();
						for(Order o : orders)
						{
							
							if(o.getId() == id)
							{
								if(!department.processOrder(o))
								{
									JOptionPane.showMessageDialog(null, "The order could not be processed.");
								}
								else
								{
									JOptionPane.showMessageDialog(null, "The order was processed.");
								}
								break;
							}
						}
						
						refreshAdminModels();
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The order id is invalid.");
					}	
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Error getting the order from database " + e);
				}
			}
		}
	}
	
	class GoBackAdminListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			gui.goBackAdmin();
			
		}
	}
	
	class ConfirmListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			int id = gui.getReceivedOrder();
			if(id != -1)
			{
				Vector<Order> orders = department.getOrders();
				for(Order o : orders)
				{
					
					if(o.getId() == id)
					{
						if(o.getStatus().equals("InProgress"))
						{
							actualCustomer.receiveOrder(o);
							try
							{
								pst = connection.prepareStatement("update orders set statusOrder = 'Completed' where idOrder = " + id);
								pst.executeUpdate();
								

								JOptionPane.showMessageDialog(null, "The status of the order has changed.");
								refreshModels(idCustomer);
							}
							catch(Exception e)
							{
								JOptionPane.showMessageDialog(null, "The status of the order could not be set.");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "The order is not in progress. You cannot receive a product that is not in progress.");
						}
						break;
					}
				}
			}
		}
		
	}
}
