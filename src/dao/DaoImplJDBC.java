package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Amount;
import model.Employee;
import model.Person;
import model.Product;

public class DaoImplJDBC implements Dao {
	private Connection connection;

	@Override
	public void connect() {
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
	public static final String INSERT_HISTORY_PRODUCT = "INSERT INTO historical_inventory (id_producto, name, wholesalerPrice, available, stock, created_at) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	public static final String INSERT_PRODUCT = "INSERT INTO inventory (id, name, wholesalerPrice, available, stock) "
			+ "VALUES (?, ?, ?, ?, ?)";
	public static final String DELETE_PRODUCT = "DELETE FROM inventory WHERE name = ?";
	public static final String UPDATE_PRODUCT = "UPDATE inventory SET stock = ? WHERE name = ?";;

	@Override
	public Employee getEmployee(int employeeid, String password) {
		Employee employee = null;
		// prepare query
		String query = "select * from employee where employeeid = ? and password = ? ";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			// set id to search for

			ps.setInt(1, employeeid);
			ps.setString(2, password);
			// System.out.println(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3));

				}
			}
		} catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public void disconnect() {
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
					Product producto = new Product(rs.getString(2), wholesalerPrice, rs.getInt(5));
					producto.setId(rs.getInt(1));
					product.add(producto);
					
					
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
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_HISTORY_PRODUCT)) {
			for (Product product : inventario) {
				LocalDateTime currentDateTime = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 																				
				String formattedDateTime = currentDateTime.format(formatter);
				
				preparedStatement.setInt(1, product.getId());
				preparedStatement.setString(2, product.getName());
				preparedStatement.setDouble(3, product.getWholesalerPrice().getValue());
				preparedStatement.setBoolean(4, product.getAvailable());
				preparedStatement.setInt(5, product.getStock());
				preparedStatement.setString(6, formattedDateTime);
				preparedStatement.executeUpdate();
			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteProduct(Product producto) throws IOException {
		try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_PRODUCT)) {
			deleteStatement.setString(1, producto.getName()); 
			deleteStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addProduct(Product producto) throws IOException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT)) {
			preparedStatement.setInt(1, producto.getId());			
			preparedStatement.setString(2, producto.getName());
			preparedStatement.setDouble(3, producto.getWholesalerPrice().getValue());
			preparedStatement.setBoolean(4, producto.getAvailable());
			preparedStatement.setInt(5, producto.getStock());
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public boolean updateProduct(String name, int stock) throws IOException {
		try (PreparedStatement updateStatement = connection.prepareStatement(UPDATE_PRODUCT)) {
			updateStatement.setInt(1,stock);
			updateStatement.setString(2,name);
			updateStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
