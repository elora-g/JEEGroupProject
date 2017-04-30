package com.jeegroupproject.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBServiceSingleton {
	
	protected static DBServiceSingleton instance = null;
	//TODO, read these from config
	protected static DBConnectorDescriptor[] DBConnectorDescriptors = { new DBConnectorDescriptor("jdbc:mysql://localhost:3306/societe_agricole_test", "root", "rootroot", "com.mysql.jdbc.Driver", null)};
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
		selectedDBConnetor = i;
		String initFile = null;
		if(connection != null && (initFile = DBConnectorDescriptors[i].getInitFile()) != null){
			//TODO : load init file and play statement on that connection 
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
				e.printStackTrace();
				System.err.println("couldn't establish link to DB");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	protected String initFile;
	
	
	

	public DBConnectorDescriptor(String url, String usernameDB, String passwordDB, String driverClassName,
			String initFile) {
		super();
		this.url = url;
		this.usernameDB = usernameDB;
		this.passwordDB = passwordDB;
		this.driverClassName = driverClassName;
		this.initFile = initFile;
	}
	public String getInitFile() {
		return initFile;
	}
	public void setInitFile(String initFile) {
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
