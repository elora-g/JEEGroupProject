package com.jeegroupproject.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jeegroupproject.database.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.jeegroupproject.bcrypt.*;

public class User {
	private int id;
	private String name;
	private String surname;
	private String password;
	private boolean is_employee;
	private boolean is_customer;
	private int client_id;
	private String access_token;
	private String email;
	
	
  	// Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
	// Must be consistent across the app or hashed passwords won't match
	private static int workload = 12;

	/**
	 * This method can be used to generate a string representing an account password
	 * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
	 * hash string of length=60
	 * The bcrypt workload is specified in the above static variable, a value from 10 to 31.
	 * A workload of 12 is a very reasonable safe default as of 2013.
	 * This automatically handles secure 128-bit salt generation and storage within the hash.
	 * @param password_plaintext The account's plaintext password as provided during account creation,
	 *			     or when changing an account's password.
	 * @return String - a string of length 60 that is the bcrypt hashed password in crypt(3) format.
	 */
	public static String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);

		return(hashed_password);
	}

	/**
	 * This method can be used to verify a computed hash from a plaintext (e.g. during a login
	 * request) with that of a stored hash from a database. The password hash from the database
	 * must be passed as the second variable.
	 * @param password_plaintext The account's plaintext password, as provided during a login request
	 * @param stored_hash The account's stored password hash, retrieved from the authorization database
	 * @return boolean - true if the password matches the password of the stored hash, false otherwise
	 */
	public static boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;

		if(null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return(password_verified);
	}
	
	
	/* TODO
	 * prepare a method to insert stuff around
	 * public static void prepareSampledata(){
	 * to prepare a test sample of clients
	 * String iQuery = "INSERT INTO `users` (`id`, `name`, `surname`, `email`, `password`, `is_employee`, `is_customer`, `client_id`, `access_token`) VALUES (2, 'Toto', 'Tata', 'toto.tata@titi.fr', '$2y$10$a00IrSEYIIYI.CYC8pqGeu2G8uYMKhMj19GQRmeAR0YTJMs4RJJzO', 0, 1, 12345678, '');";

			statement.executeUpdate(iQuery));
		}
	 */
	
	
	/**
	 * Helper method to get a client by its client id from database
	 * @param client_id
	 * @return the found user, If not found, all members of the user are empty
	 */
	public static User getUserByClient_id(int client_id){
		
		Connection connection = DBConnectionFactory.getConnection(); //returns a prepared connection to the database
		User user = new User();
		try {
			PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM users WHERE `client_id` = ?");
			pStatement.setInt(1, client_id);
			ResultSet result = pStatement.executeQuery();
	
			if(result.next()){ //check we have at least one result
				user.setId( result.getInt(1));
				user.setName(result.getString(2));
				user.setSurname(result.getString(3));
				user.setEmail( result.getString(4));
				user.setPassword(result.getString(5));
				user.setIs_employee(result.getBoolean(6));
				user.setIs_customer(result.getBoolean(7));
				user.setClient_id(result.getInt(8));
				user.setAccess_token(result.getString(9));
			}
				
		} catch (SQLException e) {
			System.err.println("problem querying");
			e.printStackTrace();
		}
		
		return user;	
	}
	
	/**
	 * Helper method to get a client based on its credentials
	 * @param clientId
	 * @return the found user, If not found, returns null
	 */
	public static User getAuthenticatedUser(Integer clientId, String email, String password){
		
		Connection connection = DBConnectionFactory.getConnection(); //returns a prepared connection to the database
		User userByClientID;
		User userByEmail;
		try {
			if(clientId != null){
				PreparedStatement pStatement1 = (PreparedStatement) connection.prepareStatement("SELECT * FROM users WHERE `client_id` = ?");
				pStatement1.setInt(1, clientId);
				ResultSet result1 = pStatement1.executeQuery();
				if(result1.next()){ //check we have at least one result
					userByClientID = new User();
					userByClientID.setId( result1.getInt(1));
					userByClientID.setName(result1.getString(2));
					userByClientID.setSurname(result1.getString(3));
					userByClientID.setEmail( result1.getString(4));
					userByClientID.setPassword(result1.getString(5));
					userByClientID.setIs_employee(result1.getBoolean(6));
					userByClientID.setIs_customer(result1.getBoolean(7));
					userByClientID.setClient_id(result1.getInt(8));
					userByClientID.setAccess_token(result1.getString(9));
					if(checkPassword(password, userByClientID.getPassword())){
						//TODO : before returning the client, update its token
						// number of milliseconds since epoch
						//generate an hash
						// save in DB (using the function to save in DB to be defined)
						return userByClientID;
					}
				}
			}
			
			if(email != null){
				PreparedStatement pStatement2 = (PreparedStatement) connection.prepareStatement("SELECT * FROM users WHERE `email` = ?");
				pStatement2.setString(1, email);
				ResultSet result2 = pStatement2.executeQuery();
		
				
				if(result2.next()){ //check we have at least one result
					userByEmail = new User();
					userByEmail.setId( result2.getInt(1));
					userByEmail.setName(result2.getString(2));
					userByEmail.setSurname(result2.getString(3));
					userByEmail.setEmail( result2.getString(4));
					userByEmail.setPassword(result2.getString(5));
					userByEmail.setIs_employee(result2.getBoolean(6));
					userByEmail.setIs_customer(result2.getBoolean(7));
					userByEmail.setClient_id(result2.getInt(8));
					userByEmail.setAccess_token(result2.getString(9));
					if(checkPassword(password, userByEmail.getPassword())){
						//TODO : before returning the client, update its token
						return userByEmail;
						
					}
				}
			}
			
				
		} catch (SQLException e) {
			System.err.println("problem querying");
			e.printStackTrace();
		}
		
		return null;	
	}
	
	//TODO : getauthenticateduserbytoken similar to getauthenticated user
	//TODO : methode updatetokenforUser
		//generate a token called each time getauthenticateduser is called (see getauthenticated user)
		//SQL query to update the field in DB
	
	//TODO : Create a non-static method to save a client to database 
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isIs_employee() {
		return is_employee;
	}
	public void setIs_employee(boolean is_employee) {
		this.is_employee = is_employee;
	}
	public boolean isIs_customer() {
		return is_customer;
	}
	public void setIs_customer(boolean is_customer) {
		this.is_customer = is_customer;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}






	public String getEmail() {
		return email;
	}






	public void setEmail(String email) {
		this.email = email;
	}
	

}
