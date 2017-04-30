package com.jeegroupproject.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public void persist(){
        
		String queryInsert = "UPDATE sac_accounts SET account_customer_id = ?, account_balance = ?, account_type = ?, account_is_default = ? WHERE account_id = ?  ;";
		String queryUpdate = "INSERT INTO sac_accounts (`account_customer_id`, `account_balance`, `account_type`, `account_is_default`) VALUES (?,?,?,?);";
		String queryRemoveOtherDefault = "UPDATE sac_accounts SET account_is_default = 0 WHERE account_customer_id = ?";
		
		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		//Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		//https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try(Connection connection = DBConnectionFactory.getConnection()){
        	if(this.isDefault){ // to remove the default flag of an account if the advisor sets it to another account while creating it.
        		
        		try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(queryRemoveOtherDefault)){
        			pStatement.setString(1, this.getCustomerId());
        			// execute update SQL statement
		            pStatement.executeUpdate();
		            
				}catch (SQLException e) {
					System.err.println("persist: problem with the prepared statement when changing defaults");
					e.printStackTrace();
				}
        	}
	        //test if the person already has an id >=0 (id -1 means not yet created in db).
	        //if he does, he already exists in database, so we update it
	        if(this.getId() > 0){
	            //prepare a prepared statement for update
	            try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(queryInsert)){
	            	
		            pStatement.setString(1, this.getCustomerId());
		            pStatement.setFloat(2, this.getBalance());
		            pStatement.setString(3, this.getType());
		            pStatement.setBoolean(4, this.getIsDefault());
		            pStatement.setInt(5, this.getId());
		
		            // execute update SQL statement
		            pStatement.executeUpdate();
				}catch (SQLException e) {
					System.err.println("persist: problem with the prepared statement at insert");
					e.printStackTrace();
				}

	        }else{//if he does not, one must insert it.
	            //prepare a prepared statement for insertion
	        	try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(queryUpdate, Statement.RETURN_GENERATED_KEYS)){
	
		            pStatement.setString(1, this.getCustomerId());
		            pStatement.setFloat(2, this.getBalance());
		            pStatement.setString(3, this.getType());
		            pStatement.setBoolean(4, this.getIsDefault());
		
		            // execute update SQL statement
		
		            int affectedRows = pStatement.executeUpdate();
		
		            if (affectedRows == 0) {
		                throw new SQLException("Creating account failed, no rows affected.");
		            }
		
		            try (ResultSet generatedKeys = pStatement.getGeneratedKeys()) {
		                if (generatedKeys.next()) {
		                    this.setId(generatedKeys.getInt(1));
		                }
		                else {
		                    throw new SQLException("Creating account failed, no ID obtained.");
		                }
		            }
	            
				}catch (SQLException e) {
					System.err.println("persist: problem with the prepared statement at update");
					e.printStackTrace();
				}
			}
		}catch (SQLException e) {
			System.err.println("persist: problem with the connection");
			e.printStackTrace();
		}
    }
	
	
	
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
