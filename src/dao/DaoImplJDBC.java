package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Amount;
import model.Employee;
import model.Person;
import model.Product;

public class DaoImplJDBC implements Dao{
	private Connection connection;
	@Override
	public void connect()  {
		// Define connection parameters
		String url = "jdbc:mysql://localhost:8889/shop";
		String user = "root";
		String pass = "root";
		
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static final String GET_PRODUCTS = "select * from inventory";


	@Override
	public Employee getEmployee(int employeeid, String password)  {
		Employee employee = null;
		// prepare query
		String query = "select * from employee where employeeid = ? and password = ? ";
		
		try (PreparedStatement ps = connection.prepareStatement(query)) { 
			// set id to search for
			
			ps.setInt(1,employeeid);
			ps.setString(2, password);
		  	//System.out.println(ps.toString());
	        try (ResultSet rs = ps.executeQuery()) {
	        	if (rs.next()) {
	        		employee =  new Employee(rs.getInt(1), rs.getString(2), rs.getString(3));            		            				
          		            				
	        	}
	        }
	    } catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public void disconnect()  {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public List<Product> getInventory() throws IOException {
		List<Product> product = new ArrayList<>();
		Amount wholesalerPrice = new Amount();
		try (Statement ps = connection.createStatement()) {

			try (ResultSet rs = ps.executeQuery(GET_PRODUCTS)) {
				// for each result add to list
				while (rs.next()) {
					// get data for each person from column
					wholesalerPrice = new Amount(rs.getInt(3), "€");
					product.add(
							new Product(rs.getString(2), wholesalerPrice, rs.getInt(5)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;

	}
	


	@Override
	public boolean writeInventory(List<Product> inventario) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
}