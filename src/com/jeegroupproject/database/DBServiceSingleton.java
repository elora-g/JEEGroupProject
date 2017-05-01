package com.jeegroupproject.database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import java.sql.Connection;

public class DBServiceSingleton {
	
	protected static DBServiceSingleton instance = null;
	//TODO, read these from config
	protected static DBConnectorDescriptor[] DBConnectorDescriptors = { new DBConnectorDescriptor("jdbc:mysql://localhost:3306/societe_agricole_test", "root", "rootroot", "com.mysql.jdbc.Driver", null),
			 															new DBConnectorDescriptor("jdbc:hsqldb:mem:societe_agricole_test","SA","", "org.hsqldb.jdbc.JDBCDriver", new String[] {"initDb.sql", "populateDb.sql"})};
	protected static int selectedDBConnetor;
	
	
	protected DBServiceSingleton() {
		//if no external connectors work, fall back to inMemroy HSQLDB and set it up !
		
		//remember, once a connection has been established, one should not be able to change it unless app reboot.
		
		//TODO test each DBConnectorDescriptors until there's one working.
		int i = 0;
		Connection connection = null;
		while( (connection = getConnectionFromDescriptor(DBConnectorDescriptors[i])) == null ){
			i++;
		}
		
		String[] initFile = null;
		if(connection != null){
			selectedDBConnetor = i;
			initFile = DBConnectorDescriptors[i].getInitFile();
			//TODO : load init file and play statement on that connection 
			 System.err.println("found valid connection at index" + selectedDBConnetor);
			 if(initFile != null){

				 for(String file : initFile){
				 
					 InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scripts/"+file.toString());
					 if (stream == null) {
						 System.err.println("File not found "+file.toString());
				    } else {
				   
						Scanner scanner = new Scanner(stream);    
					    String s;
						while( scanner.hasNext()){
							s = scanner.nextLine();
							if(!s.trim().isEmpty()){
							    Statement statement;
								try {
									statement = connection.createStatement();
									statement.executeQuery(s);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									System.err.println("Error when executing statement : "+s);
									e.printStackTrace();
								}
								
							}
					    }
						    
				    }
			
			    }	
				 
			 }
			 
		}
		
	}
	
	public static DBServiceSingleton getInstance(){
		if(instance == null){
			instance = new DBServiceSingleton();
		}
		return instance;
	}
	
	protected static Connection getConnectionFromDescriptor(DBConnectorDescriptor descriptor){
		Connection connection = null;
		try {
			Class.forName(descriptor.driverClassName);

			try {
				connection = (Connection) DriverManager.getConnection(descriptor.url, descriptor.usernameDB, descriptor.passwordDB);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.err.println("couldn't establish link to DB");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("couldn't find JDBC driver");
		}
		
		return connection;
		
	}

	/**
	 * Get a connection to the DB
	 * @return the created connection
	 */
	public Connection getConnection(){
		        
        return getConnectionFromDescriptor(DBConnectorDescriptors[selectedDBConnetor]);
	}
}

class DBConnectorDescriptor{
	protected String url;
	protected String usernameDB;
	protected String passwordDB;
	protected String driverClassName;
	protected String[] initFile;
	
	
	

	public DBConnectorDescriptor(String url, String usernameDB, String passwordDB, String driverClassName,
			String[] initFile) {
		super();
		this.url = url;
		this.usernameDB = usernameDB;
		this.passwordDB = passwordDB;
		this.driverClassName = driverClassName;
		this.initFile = initFile;
	}
	public String[] getInitFile() {
		return initFile;
	}
	public void setInitFile(String[] initFile) {
		this.initFile = initFile;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsernameDB() {
		return usernameDB;
	}
	public void setUsernameDB(String usernameDB) {
		this.usernameDB = usernameDB;
	}
	public String getPasswordDB() {
		return passwordDB;
	}
	public void setPasswordDB(String passwordDB) {
		this.passwordDB = passwordDB;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	
}
