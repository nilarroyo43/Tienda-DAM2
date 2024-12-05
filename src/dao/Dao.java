package dao;

import java.io.IOException;
import java.util.List;

import main.Shop;
import model.Employee;
import model.Product;

public interface Dao {
	public void connect();
	
	public Employee getEmployee(int employeeid, String password);
	
	public List<Product> getInventory() throws IOException;

	public void disconnect();

	boolean writeInventory(List<Product> inventario) throws IOException;
	
	boolean addProduct(Product producto) throws IOException;
	
	boolean updateProduct(String name, int stock ) throws IOException;
	
	boolean deleteProduct(Product producto) throws IOException;
	
	

}

