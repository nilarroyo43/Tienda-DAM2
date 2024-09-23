package dao;

import java.util.List;

import main.Shop;
import model.Employee;
import model.Product;

public interface Dao {
	public void connect();
	public Employee getEmployee(int employeeid, String password);
	public default void getInventory(){};
	public void disconnect();	
}

