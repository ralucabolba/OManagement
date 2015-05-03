package Controller;

import Model.*;
import View.SwingGUI;
import java.sql.*;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) 
	{
		Connection connection = null;
		try
        {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/orders", "root", "");
			SwingGUI gui = new SwingGUI((com.mysql.jdbc.Connection) connection);
			OPDept department = new OPDept((com.mysql.jdbc.Connection)connection);
			Controller controller = new Controller(department, gui, connection);
        }
        catch (Exception e) 
        {
             JOptionPane.showMessageDialog(null,e.getMessage());
        } 
		
	}

}
