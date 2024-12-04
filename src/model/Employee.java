package model;

import java.sql.SQLException;

import dao.Dao;
import dao.DaoImplJDBC;
import main.Logable;

public class Employee extends Person implements Logable {
	private int employeeId;
	private boolean logged = false;
	public Dao dao = new DaoImplJDBC();

	public Employee(int employeeId, String name, String password) {
		super();
		this.employeeId = employeeId;
		this.logged = logged;

	}

	public Employee() {
		// TODO Auto-generated constructor stub
	}

//	final static int USER = 123;
//	final static String PASSWORD = "test";

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/*
	 * public static int getUSER() { return USER; }
	 * 
	 * public static String getPASSWORD() { return PASSWORD; }
	 */

	@Override
	public boolean logEmployee(int user, String pass) {
		boolean success = false;
		dao.connect();

		if (dao.getEmployee(user, pass) != null) {
			success = true;
		}
		dao.disconnect();
		return success;
	}
	// TODO Auto-generated catch block

}
