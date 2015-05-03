package View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.mysql.jdbc.Connection;

public class SwingGUI extends JFrame
{
	private JDesktopPane firstPane;
	private Connection connection;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private JFrame firstWindow, myAccount, adminAccount;
	private JLabel createAccount, loginLabel, nameLabel, addressLabel, usernameLabel1, usernameLabel2, cnpLabel, emailLabel, phoneLabel, passwordLabel1, passwordLabel2;
	private JPasswordField password1Field, password2Field;
	private JTextField nameField, addressField, cnpField, emailField, phoneField, username1Field, username2Field, noProductChosen;
	private JButton login, register, save, addToCart, finalize, back, backAdmin, addProduct, removeProduct, process, confirm;
	private JComboBox listProducts;
	
	private JTextField newNameField, newAddressField, newPhoneField, newEmailField, receivedOrder;
	private JPasswordField newPasswordField;
	
	private DefaultTableModel model, modelCart, modelProduct, adminModelProducts, adminModelOrders, adminModelCustomers, modelU, modelO;
	private JTable table, tableCart, tableProducts, adminTableProd, adminTableOrd, adminTableCust, tableU, tableO;
	private JScrollPane scroll, scrollCart, scrollProduct, adminScrollProd, adminScrollOrd, adminScrollCust, scrollU, scrollO;
	
	private JLabel newName, newAddress, newPhone, newEmail, newPassword, noProducts;
	private JDesktopPane panel1, panel3, panel4;
	private JDesktopPane panel2;
	
	private JTextField nameProductField, quantityProductField, priceProductField, orderProcessField;
	
	public SwingGUI(Connection connection)
	{
		super("Order Management");
		this.connection = connection;
		
		firstPane = new JDesktopPane();
		firstPane.setBackground(new Color(227, 216, 230));
		
		firstWindow = this;
		noProducts = new JLabel("Total number of products is ");
		noProducts.setFont(new Font("Georgia", 2, 14));
		noProducts.setBounds(0, 330, 300, 25);
		
		createAccount = new JLabel("Create a new account");
		createAccount.setFont(new Font("Georgia", 2, 14));
		createAccount.setBounds(100, 20, 160, 25);
		loginLabel = new JLabel("Login");
		loginLabel.setFont(new Font("Georgia", 2, 14));
		loginLabel.setBounds(500, 20, 80, 25);
		nameLabel = new JLabel("Name :");
		nameLabel.setFont(new Font("Georgia", 2, 13));
		nameLabel.setBounds(20, 60, 80, 25);
		addressLabel = new JLabel("Address :");
		addressLabel.setFont(new Font("Georgia", 2, 13));
		addressLabel.setBounds(20, 90, 80, 25);
		cnpLabel = new JLabel("Cnp :");
		cnpLabel.setFont(new Font("Georgia", 2, 13));
		cnpLabel.setBounds(20, 120, 80, 25);
		emailLabel = new JLabel("Email :");
		emailLabel.setFont(new Font("Georgia", 2, 13));
		emailLabel.setBounds(20, 150, 80, 25);
		phoneLabel = new JLabel("Phone number :");
		phoneLabel.setFont(new Font("Georgia", 2, 13));
		phoneLabel.setBounds(20, 180, 100, 25);
		usernameLabel1 = new JLabel("Username :");
		usernameLabel1.setFont(new Font("Georgia", 2, 13));
		usernameLabel1.setBounds(20, 210, 80, 25);
		usernameLabel2 = new JLabel("Username :");
		usernameLabel2.setFont(new Font("Georgia", 2, 13));
		usernameLabel2.setBounds(400, 60, 80, 25);
		passwordLabel1 = new JLabel("Password :");
		passwordLabel1.setFont(new Font("Georgia", 2, 13));
		passwordLabel1.setBounds(20, 240, 80, 25);
		passwordLabel2 = new JLabel("Password :");
		passwordLabel2.setFont(new Font("Georgia", 2, 13));
		passwordLabel2.setBounds(400, 90, 80, 25);
		
		nameField = new JTextField("", 20);
		nameField.setBounds(120, 60, 160, 25);
		addressField = new JTextField("", 20);
		addressField.setBounds(120, 90, 160, 25);
		cnpField = new JTextField("", 20);
		cnpField.setBounds(120, 120, 160, 25);
		emailField = new JTextField("", 20);
		emailField.setBounds(120, 150, 160, 25);
		phoneField = new JTextField("", 20);
		phoneField.setBounds(120, 180, 160, 25);
		username1Field = new JTextField("", 20);
		username1Field.setBounds(120, 210, 160, 25);
		username2Field = new JTextField("", 20);
		username2Field.setBounds(500, 60, 160, 25);
		password1Field = new JPasswordField("", 20);
		password1Field.setBounds(120, 240, 160, 25);
		password2Field = new JPasswordField("", 20);
		password2Field.setBounds(500, 90, 160, 25);
		
		login = new JButton("Login");
		login.setBounds(500, 150, 100, 25);
		register = new JButton("Register");
		register.setBounds(100, 300, 100, 25);
		save = new JButton("Save changes");
		save.setBounds(120, 250, 160, 25);
		addToCart = new JButton("Add product to cart");
		addToCart.setBounds(300, 160, 200, 25);
		finalize = new JButton("Finalize");
		finalize.setBounds(0, 400, 100, 25);
		back  = new JButton("Back");
		back.setBounds(150, 300, 100, 25);
		backAdmin = new JButton("Back");
		backAdmin.setBounds(70, 450, 80, 25);
		listProducts = new JComboBox();
		
		//listProducts.setSize(160, 25);
		
		addProduct = new JButton("Add product");
		addProduct.setBounds(70, 320, 120, 25);
		
		removeProduct = new JButton("Remove product");
		removeProduct.setBounds(450, 260, 150, 25);
		
		process = new JButton("Process order");
		process.setBounds(80, 410, 150, 25);
		
		confirm = new JButton("Confirm");
		confirm.setBounds(550, 200, 100, 25);
		
		firstPane.add(createAccount);
		firstPane.add(loginLabel);
		firstPane.add(nameLabel);
		firstPane.add(nameField);
		firstPane.add(addressLabel);
		firstPane.add(addressField);
		firstPane.add(cnpLabel);
		firstPane.add(cnpField);
		firstPane.add(emailLabel);
		firstPane.add(emailField);
		firstPane.add(phoneLabel);
		firstPane.add(phoneField);
		firstPane.add(usernameLabel1);
		firstPane.add(usernameLabel2);
		firstPane.add(username1Field);
		firstPane.add(username2Field);
		firstPane.add(passwordLabel1);
		firstPane.add(passwordLabel2);
		firstPane.add(password1Field);
		firstPane.add(password2Field);
		firstPane.add(register);
		firstPane.add(login);
		
		this.add(firstPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(720, 400);
		this.setVisible(true);
	}
	public void adminWindow()
	{
		firstWindow.setVisible(false);
		
		JTabbedPane adminPane = new JTabbedPane();
		JDesktopPane panel1, panel2, panel3; //report for products, report for orders
		
		panel1 = new JDesktopPane();
		panel1.setBackground(new Color(227, 216, 230));
		panel2 = new JDesktopPane();
		panel2.setBackground(new Color(227, 216, 230));
		panel3 = new JDesktopPane();
		panel3.setBackground(new Color(227, 216, 230));
		panel4 = new JDesktopPane();
		panel4.setBackground(new Color(227, 216, 230));
		
		adminModelProducts = new DefaultTableModel();
		adminModelProducts.setColumnIdentifiers(new String[]{"Id", "Name", "Quantity", "Price"});
		
		adminTableProd = new JTable();
		adminTableProd.setModel(adminModelProducts);
		adminTableProd.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		adminTableProd.setFillsViewportHeight(true);
		adminTableProd.setPreferredScrollableViewportSize(new Dimension(800, 150));
		
	    adminScrollProd = new JScrollPane(adminTableProd);
	    adminScrollProd.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    adminScrollProd.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    adminScrollProd.setBounds(0, 0, 800, 150);
	    
        JLabel addNewProduct, name, quantity, price, removeProductLabel;
        
        addNewProduct = new JLabel("Add new product:");
        addNewProduct.setFont(new Font("Georgia", 2, 14));
        addNewProduct.setBounds(80, 180, 200, 25);
        panel1.add(addNewProduct);
        
        removeProductLabel = new JLabel("Remove product :");
        removeProductLabel.setFont(new Font("Georgia", 2, 14));
        removeProductLabel.setBounds(450, 180, 200, 25);
        panel1.add(removeProductLabel);
        
        listProducts.setBounds(400, 220, 250, 25);
        listProducts.setToolTipText("Select the product you want to remove.");
        panel1.add(listProducts);
        
        name = new JLabel("Name : ");
        name.setFont(new Font("Georgia", 2, 13));
        name.setBounds(20, 220, 80, 25);
        panel1.add(name);
        
        nameProductField = new JTextField();
        nameProductField.setBounds(120, 220, 100, 25);
        panel1.add(nameProductField);
        
        quantity = new JLabel("Quantity : ");
        quantity.setFont(new Font("Georgia", 2, 13));
        quantity.setBounds(20, 250, 80, 25);
        panel1.add(quantity);
        
        quantityProductField = new JTextField();
        quantityProductField.setBounds(120, 250, 100, 25);
        panel1.add(quantityProductField);
        
        price = new JLabel("Price : ");
        price.setFont(new Font("Georgia", 2, 13));
        price.setBounds(20, 280, 80, 25);
        panel1.add(price);
        
        priceProductField = new JTextField();
        priceProductField.setBounds(120, 280, 100, 25);
        panel1.add(priceProductField);
        
        
		panel1.add(addProduct);
		panel1.add(removeProduct);
		panel1.add(backAdmin);
		
		panel1.add(adminScrollProd);
		
		adminPane.addTab("Report products", panel1);
		
		
		// Panel 2 - report of customers
		
		adminModelCustomers = new DefaultTableModel();
		adminModelCustomers.setColumnIdentifiers(new String[]{"Id", "Name", "Address", "Email", "Phone Number", "Cnp", "Username", "Password"});
		
		adminTableCust = new JTable();
		adminTableCust.setModel(adminModelCustomers);
		adminTableCust.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		adminTableCust.setFillsViewportHeight(true);
		adminTableCust.setPreferredScrollableViewportSize(new Dimension(1000, 300));
		
	    adminScrollCust = new JScrollPane(adminTableCust);
	    adminScrollCust.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    adminScrollCust.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    adminScrollCust.setBounds(0, 0, 1000, 300);
	    
	    panel2.add(adminScrollCust);
	    adminPane.addTab("Report customers", panel2);
	    
	    
	    // Panel 3 - report of orders
		
	 	adminModelOrders = new DefaultTableModel();
	 	adminModelOrders.setColumnIdentifiers(new String[]{"Id", "Status", "Date", "Id Customer", "Product", "Quantity"});
	 		
	 	adminTableOrd = new JTable();
	 	adminTableOrd.setModel(adminModelOrders);
	 	adminTableOrd.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	 	adminTableOrd.setFillsViewportHeight(true);
	 	adminTableOrd.setPreferredScrollableViewportSize(new Dimension(800, 300));
	 		
	 	adminScrollOrd = new JScrollPane(adminTableOrd);
	 	adminScrollOrd.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	 	adminScrollOrd.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	 	adminScrollOrd.setBounds(0, 0, 800, 300);
	 
	 	JLabel processOrder, choseIdOrder;
	 	
	 	processOrder = new JLabel("Process order : ");
	 	processOrder.setFont(new Font("Georgia", 2, 14));
	 	processOrder.setBounds(100, 340, 120, 25);
	 	panel3.add(processOrder);
	 	
	 	choseIdOrder = new JLabel("Type the id of the order you want to process : ");
	 	choseIdOrder.setFont(new Font("Georgia", 2, 13));
	 	choseIdOrder.setBounds(20, 370, 300, 25);
	 	panel3.add(choseIdOrder);
	 	
	 	orderProcessField = new JTextField();
	 	orderProcessField.setBounds(350, 370, 80, 25);
	 	panel3.add(orderProcessField);
	 	
	 	panel3.add(process);
	 	
	 	panel3.add(adminScrollOrd);
	 	adminPane.addTab("Report orders", panel3);
	 	    
	 	//panel 4  - filters
	 	
	 	JLabel uLabel, oLabel;
	 	
	 	uLabel = new JLabel("Understock products : ");
	 	uLabel.setFont(new Font("Georgia", 2, 14));
	 	uLabel.setBounds(0, 0, 150, 25);
	 	panel4.add(uLabel);
	 	
	 	oLabel = new JLabel("Overstock products : ");
	 	oLabel.setFont(new Font("Georgia", 2, 14));
	 	oLabel.setBounds(0, 170, 150, 25);
	 	panel4.add(oLabel);
	 	
	 	modelU = new DefaultTableModel();
	 	modelU.setColumnIdentifiers(new String[]{"Id", "Name", "Quantity", "Price"});
	 	
	 	modelO = new DefaultTableModel();
	 	modelO.setColumnIdentifiers(new String[]{"Id", "Name", "Quantity", "Price"});
		
		tableU = new JTable();
		tableU.setModel(modelU);
		tableU.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableU.setFillsViewportHeight(true);
		tableU.setPreferredScrollableViewportSize(new Dimension(800, 100));
		
		tableO = new JTable();
		tableO.setModel(modelO);
		tableO.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableO.setFillsViewportHeight(true);
		tableO.setPreferredScrollableViewportSize(new Dimension(800, 100));
		
	    scrollU = new JScrollPane(tableU);
	    scrollU.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollU.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollU.setBounds(0, 40, 800, 100);
	    panel4.add(scrollU);
	   
	    scrollO = new JScrollPane(tableO);
	    scrollO.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollO.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollO.setBounds(0, 200, 800, 100);
	    panel4.add(scrollO);
	    panel4.add(noProducts);
	    
	    adminPane.addTab("Filters", panel4);
	    
		adminAccount = new JFrame("Administrator account");
		adminAccount.add(adminPane);
		adminAccount.setSize(800, 600);
		adminAccount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminAccount.setVisible(true);
	}
	public void myAccountWindow(int idCustomer)
	{
		firstWindow.setVisible(false);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		newName = new JLabel("Name :");
		newName.setFont(new Font("Georgia", 2, 13));
		newName.setBounds(20, 60, 80, 25);
		newAddress = new JLabel("Address :");
		newAddress.setFont(new Font("Georgia", 2, 13));
		newAddress.setBounds(20, 90, 80, 25);
		newEmail = new JLabel("Email :");
		newEmail.setFont(new Font("Georgia", 2, 13));
		newEmail.setBounds(20, 120, 80, 25);
		newPhone = new JLabel("Phone number :");
		newPhone.setFont(new Font("Georgia", 2, 13));
		newPhone.setBounds(20, 150, 100, 25);
		newPassword= new JLabel("Password :");
		newPassword.setFont(new Font("Georgia", 2, 13));
		newPassword.setBounds(20, 180, 80, 25);
		
		newNameField = new JTextField("", 20);
		newNameField.setBounds(120, 60, 160, 25);
		newAddressField = new JTextField("", 20);
		newAddressField.setBounds(120, 90, 160, 25);
		newEmailField = new JTextField("", 20);
		newEmailField.setBounds(120, 120, 160, 25);
		newPhoneField = new JTextField("", 20);
		newPhoneField.setBounds(120, 150, 160, 25);
		newPasswordField = new JPasswordField("", 20);
		newPasswordField.setBounds(120, 180, 160, 25);
		noProductChosen = new JTextField("", 20);
		noProductChosen.setBounds(220, 160, 50, 25);
		
		//table for 'my orders'
		model = new DefaultTableModel();
	    model.setColumnIdentifiers(new String[]{"Id", "Status", "Date", "Product", "Quantity"});
	    table = new JTable();
	    table.setModel(model);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    table.setFillsViewportHeight(true);
	    scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(0, 0, 600, 150);
        
	    //table for products is section new order
        
        modelProduct = new DefaultTableModel();
	    modelProduct.setColumnIdentifiers(new String[]{"Id", "Name", "Quantity", "Price"});
	    tableProducts = new JTable();
	    tableProducts.setModel(modelProduct);
	    tableProducts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    tableProducts.setFillsViewportHeight(true);
	    tableProducts.setPreferredScrollableViewportSize(new Dimension(800, 150));
	    
	    
	    scrollProduct = new JScrollPane(tableProducts);
        scrollProduct.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollProduct.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollProduct.setBounds(0, 0, 800, 150);
        
       
        //table for cart
        modelCart = new DefaultTableModel();
        modelCart.setColumnIdentifiers(new String[]{"Name", "Price", "Quantity", "Total"});
        tableCart = new JTable();
        tableCart.setModel(modelCart);
        tableCart.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	    tableCart.setFillsViewportHeight(true);
	    tableCart.setPreferredScrollableViewportSize(new Dimension(600, 150));
	    
	    
	    scrollCart = new JScrollPane(tableCart);
        scrollCart.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollCart.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCart.setBounds(0, 200, 600, 150);
        
		panel1 = new JDesktopPane();
		panel1.setBackground(new Color(227, 216, 230));
		panel2 = new JDesktopPane();
		panel2.setBackground(new Color(227, 216, 230));
		panel3 = new JDesktopPane();
		panel3.setBackground(new Color(227, 216, 230));
		
		panel1.add(newName);
		panel1.add(newNameField);
		panel1.add(newAddress);
		panel1.add(newAddressField);
		panel1.add(newEmail);
		panel1.add(newEmailField);
		panel1.add(newPhone);
		panel1.add(newPhoneField);
		panel1.add(newPassword);
		panel1.add(newPasswordField);
		panel1.add(save);
		panel1.add(back);
		
		panel2.add(scrollProduct);
		listProducts.setBounds(0, 160, 200, 25);
		panel2.add(listProducts);
		panel2.add(addToCart);
		panel2.add(noProductChosen);
		panel2.add(scrollCart);
		panel2.add(finalize);
		panel2.setVisible(true);
		
		
		JLabel confirmLabel = new JLabel("Please introduce the id of the order you received : ");
		confirmLabel.setFont(new Font("Georgia", 2, 14));
		confirmLabel.setBounds(20, 200, 400, 25);
		
		receivedOrder = new JTextField("");
		receivedOrder.setBounds(450, 200, 80, 25);
		
		panel3.add(scroll);
		panel3.add(confirmLabel);
		panel3.add(receivedOrder);
		panel3.add(confirm);
		
		panel3.setVisible(true);
		
		tabbedPane.addTab("Profile", panel1);
		tabbedPane.addTab("New Order", panel2);
		tabbedPane.addTab("My orders", panel3);
		
		myAccount = new JFrame("My Account");
		myAccount.add(tabbedPane);
		myAccount.setSize(800, 600);
		myAccount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myAccount.setVisible(true);
		
	}
	
	public String[] getCustomerInfo()
	{
		String[] info = new String[7];
		info[0] = nameField.getText();
		info[1] = addressField.getText();
		info[2] = phoneField.getText();
		info[3] = emailField.getText();
		info[4] = cnpField.getText();
		info[5] = username1Field.getText();
		info[6]= password1Field.getText();
		
		return info;
	}
	
	public String[] getLoginInfo()
	{
		String[] info = new String[2];
		info[0] = username2Field.getText();
		info[1] = password2Field.getText();
		
		return info;
	}
	
	public String[] getChanges()
	{
		String info[] = new String[]{"", "", "", "", ""};
		info[0] = newNameField.getText();
		info[1] = newAddressField.getText();
		info[2] = newPhoneField.getText();
		info[3] = newEmailField.getText();
		info[4] = newPasswordField.getText();
		return info;
	}
	
	
	public String getProductChosenName()
	{
		return (String)listProducts.getSelectedItem();
	}
	public int getProductChosenQuantity()
	{
		String q = noProductChosen.getText();
		int quantity  = -1;
		try
		{
			quantity = Integer.parseInt(q);
			if(quantity < 0)
				throw new NumberFormatException("");
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "The quantity is not valid.");
		}
		return quantity;
	}
	
	public String[] getProductToAdd()
	{
		String[] info = new String[4];
		info[0] = nameProductField.getText();
		info[1] = quantityProductField.getText();
		info[2] = priceProductField.getText();
		
		return info;
	}
	
	public void setNoProducts(String info)
	{
		noProducts.setText(info);
	}
	public DefaultTableModel getUModel()
	{
		return this.modelU;
	}
	
	public void setUModel(DefaultTableModel model)
	{
		this.modelU = model;
	}
	
	public DefaultTableModel getOModel()
	{
		return this.modelO;
	}
	
	public void setOModel(DefaultTableModel model)
	{
		this.modelO = model;
	}
	
	public DefaultTableModel getAdminProdModel()
	{
		return this.adminModelProducts;
	}
	
	public void setAdminProdModel(DefaultTableModel model)
	{
		this.adminModelProducts = model;
	}
	
	public DefaultTableModel getCartModel()
	{
		return this.modelCart;
	}
	
	public void setCartModel(DefaultTableModel modelCart)
	{
		this.modelCart = modelCart;
	}
	
	public DefaultTableModel getProductsModel()
	{
		return this.modelProduct;
	}
	
	public void setProductsModel(DefaultTableModel model)
	{
		this.modelProduct = model;
	}
	
	public DefaultTableModel getOrderModel()
	{
		return this.model;
	}
	
	public void setOrderModel(DefaultTableModel model)
	{
		this.model = model;
	}
	
	public DefaultTableModel getAdminCustomerModel()
	{
		return this.adminModelCustomers;
	}
	
	public void setAdminCustomerModel(DefaultTableModel model)
	{
		this.adminModelCustomers = model; 
	}
	
	public DefaultTableModel getAdminOrderModel()
	{
		return this.adminModelOrders;
	}
	
	public void setAdminOrderModel(DefaultTableModel model)
	{
		this.adminModelOrders = model;
	}
	
	public JComboBox getListProductsComboBox()
	{
		return this.listProducts;
	}
	
	public void setListProductsComboBox(JComboBox list)
	{
		this.listProducts = list;
	}
	public void goBack()
	{
		myAccount.setVisible(false);
		firstWindow.setVisible(true);
	}
	public void goBackAdmin()
	{
		adminAccount.setVisible(false);
		firstWindow.setVisible(true);
	}
	public int getIdOrderProcess()
	{
		int id = -1;
		try
		{
			id = Integer.parseInt(this.orderProcessField.getText());
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "The order you want to process does not exists.");
		}
		return id;
	}
	public int getReceivedOrder()
	{
		int id = -1;
		
		try
		{
			id = Integer.parseInt(receivedOrder.getText());
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "The value introduces is invalid.");
		}
		return id;
	}
	public void addListenerNewCustomer(ActionListener act)
	{
		register.addActionListener(act);
	}
	public void addListenerEnterAccount(ActionListener act)
	{
		login.addActionListener(act);
	}
	public void addListenerSaveChanges(ActionListener act)
	{
		save.addActionListener(act);
	}
	public void addListenerAddToCart(ActionListener act)
	{
		addToCart.addActionListener(act);
	}
	public void addListenerFinalizeOrder(ActionListener act)
	{
		finalize.addActionListener(act);
	}
	public void addListenerGoBack(ActionListener act)
	{
		back.addActionListener(act);
	}
	public void addListenerAddProduct(ActionListener act)
	{
		addProduct.addActionListener(act);
	}
	public void addListenerRemoveProduct(ActionListener act)
	{
		removeProduct.addActionListener(act);
	}
	public void addListenerProcessOrder(ActionListener act)
	{
		process.addActionListener(act);
	}
	public void addListenerGoBackAdmin(ActionListener act)
	{
		backAdmin.addActionListener(act);
	}
	public void addListenerConfirm(ActionListener act)
	{
		confirm.addActionListener(act);
	}
}

