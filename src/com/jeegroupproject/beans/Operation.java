package com.jeegroupproject.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeegroupproject.database.DBConnectionFactory;

public class Operation {
	
	private int id;
	private String type;
	private float amount;
	private String description;
	private int accountId;
	private Date createdAt;
	private Date updatedAt;
	private boolean dispute;
	
	//TODO persist 	
	/**
	 * Method to save all operation data to DB (already existing ==> update or new operation ==> insert in DB)
	 */
	
	
	/**
	 * @param accountid
	 * @return a List of Operations for a given accountid
	 */
	public static List<Operation> getOperationsByAccountId(int accountid){
		String query = "SELECT * FROM sac_operation WHERE `ope_account_id` = ?";
		List<Operation> operationListByAccountId = new ArrayList<Operation>();

		
		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		// Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		try(Connection connection = DBConnectionFactory.getConnection()){ //Try with the resource connection 
			try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(query)){ //try with the preparedStatement
				pStatement.setInt(1, (accountid)); 
				try(ResultSet result = pStatement.executeQuery()){ //try with the resultSet
					while(result.next()){ //check we have at least one result. If any, read data from record
						Operation operation = new Operation();
						operation.setId(result.getInt(1));
						operation.setType(result.getString(2));
						operation.setAmount(result.getFloat(3));
						operation.setDescription(result.getString(4));
						operation.setAccountId(result.getInt(5));
						operation.setCreatedAt(result.getDate(6));
						operation.setUpdatedAt(result.getDate(7));
						operation.setDispute(result.getBoolean(8));
						
						operationListByAccountId.add(operation);

					}
				}catch (SQLException e) {
					System.err.println("getOperationsByAccountId: problem with the result set");
					e.printStackTrace();
				}
			}catch (SQLException e) {
				System.err.println("getOperationsByAccountId: problem with the prepared statement");
				e.printStackTrace();
			}
				
		}catch (SQLException e) {
			System.err.println("getOperationsByAccountId: problem with the connection");
			e.printStackTrace();
		}
		
		return operationListByAccountId;	
	}
	
	//TODO public static List<Operation> getDisputedOperationByAdvisorId(int id) ==> the query will probably need joins with accounts and persons tables
	
	// Getters and Setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public boolean getDispute() {
		return dispute;
	}
	public void setDispute(boolean dispute) {
		this.dispute = dispute;
	}
	
}
