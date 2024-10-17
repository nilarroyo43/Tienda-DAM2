package dao;

import java.io.IOException;
import java.util.List;

import model.Employee;
import model.Product;

public class DaoImplXml implements Dao {

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getEmployee(int employeeid, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getInventory() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeInventory(List<Product> inventario) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

}
