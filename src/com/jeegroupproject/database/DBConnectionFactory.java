package com.jeegroupproject.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBConnectionFactory {

	public static Connection getConnection(){
		
		//TODO check that the connection details are correct when porting to another machine
		// THAT CONFIGURATION WORKS FOR ELORA ON HER LAPTOP WITH MAMP
        String url = "jdbc:mysql://localhost:8889/societe_agricole_test";
        String usernameDB = "root";
        String passwordDB = "root";
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("couldn't find JDBC driver");
		}
        Connection connection = null;
		try {
			connection = (Connection) DriverManager.getConnection(url, usernameDB, passwordDB);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("couldn't establish link to DB");
		}
        
        return connection;
	}
}
