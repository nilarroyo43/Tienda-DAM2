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
	
	public boolean writeInventory(List<Product> inventario);
	
	public void disconnect();	
}

