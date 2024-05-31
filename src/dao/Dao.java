package dao;

import model.Employee;

public interface Dao {
	public void connect();
	public Employee getEmployee(int employeeid, String password);
	public void disconnect();	
}

