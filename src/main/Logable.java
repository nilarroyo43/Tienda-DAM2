package main;

import java.sql.SQLException;

public interface Logable {
	public boolean logEmployee(int user, String pass) throws SQLException;

}
