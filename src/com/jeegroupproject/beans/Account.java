package com.jeegroupproject.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jeegroupproject.database.DBConnectionFactory;

public class Account {
	
	private int id;
	private String customerId;
	private float balance;
	private String type;
	private boolean isDefault;
	
	//TODO persist 	
		//warning : remember max Accounts per person is 5
	/**
	 * Method to save all account data to DB (already existing ==> update or new account ==> insert in DB) 
	 */
	
	
	
	/**
	 * @param personId
	 * @return a List of accounts for the customer id passed as parameter
	 */
	
	public static List<Account> getAccountsByPersonId(int personId){
		String query = "SELECT * FROM sac_accounts WHERE `account_customer_id` = ?";
		List<Account> accountListByPersonId = new ArrayList<Account>();

		
		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		// Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		try(Connection connection = DBConnectionFactory.getConnection()){ //Try with the resource connection 
			try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(query)){ //try with the preparedStatement
				pStatement.setString(1, ((Integer)personId).toString()); // In the accounts table the id of a person is a string but in the person table it's an int so we have to cast. 
				try(ResultSet result = pStatement.executeQuery()){ //try with the resultSet
					while(result.next()){ //check we have at least one result. If any, read data from record
						Account account = new Account();
						account.setId( result.getInt(1));
						account.setCustomerId(result.getString(2));
						account.setBalance(result.getFloat(3));
						account.setType(result.getString(4));
						account.setIsDefault( result.getBoolean(5));
						
						accountListByPersonId.add(account);

					}
				}catch (SQLException e) {
					System.err.println("getAccountsByPersonId: problem with the result set");
					e.printStackTrace();
				}
			}catch (SQLException e) {
				System.err.println("getAccountsByPersonId: problem with the prepared statement");
				e.printStackTrace();
			}
				
		}catch (SQLException e) {
			System.err.println("getAccountsByPersonId: problem with the connection");
			e.printStackTrace();
		}
		
		return accountListByPersonId;	
	}
	
	//TODO public List<Operation> getOperations()
	
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

}
