package com.jeegroupproject.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public void persist() {
        
		String queryUpdate = "UPDATE `sac_operation` SET `ope_type`=?,`ope_amount`=?,`ope_description`=?,`ope_account_id`=?,`ope_created_at`=?,`ope_updated_at`=?,`ope_dispute`=? WHERE `ope_id` = ?";
		String queryInsert = "INSERT INTO `sac_operation`( `ope_type`, `ope_amount`, `ope_description`, `ope_account_id`, `ope_created_at`, `ope_updated_at`, `ope_dispute`) VALUES (?,?,?,?,?,?,?)";

		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		//Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		//https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try(Connection connection = DBConnectionFactory.getConnection()){
        	
	        //test if the person already has an id >=0 (id -1 means not yet created in db).
	        //if he does, he already exists in database, so we update it
	        if(this.getId() > 0){
	            //prepare a prepared statement for update
	            try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(queryUpdate)){
	            	
	            	pStatement.setString(1, this.getType());
		            pStatement.setFloat(2, this.getAmount());
		            pStatement.setString(3, this.getDescription());
		            pStatement.setInt(4, this.getAccountId());
		            pStatement.setTimestamp(5, new java.sql.Timestamp(this.getCreatedAt().getTime()));
		            pStatement.setTimestamp(6, new java.sql.Timestamp(new Date().getTime())); //change updated at at current time.
		            pStatement.setBoolean(7, this.getDispute());
		            pStatement.setInt(8,this.getId());
		            // execute update SQL statement
		            pStatement.executeUpdate();
				}catch (SQLException e) {
					System.err.println("persist: problem with the prepared statement at insert");
					e.printStackTrace();
				}

	        }else{//if he does not, one must insert it.
	            //prepare a prepared statement for insertion
	        	try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS)){
	        		
	        		pStatement.setString(1, this.getType());
		            pStatement.setFloat(2, this.getAmount());
		            pStatement.setString(3, this.getDescription());
		            pStatement.setInt(4, this.getAccountId());
		            pStatement.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
		            pStatement.setTimestamp(6, new java.sql.Timestamp(new Date().getTime())); //change updated at at current time.
		            pStatement.setBoolean(7, this.getDispute());
		
		
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
	
	/**
	 * 
	 * @param operationId
	 * @return the operation by its id
	 */
	public static Operation getOperationById(int operationId) {
		
		String query = "SELECT * FROM sac_operation WHERE `ope_id` = ?";
		Operation operation = new Operation();

		
		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		// Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		try(Connection connection = DBConnectionFactory.getConnection()){ //Try with the resource connection 
			try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(query)){ //try with the preparedStatement
				pStatement.setInt(1, (operationId)); 
				try(ResultSet result = pStatement.executeQuery()){ //try with the resultSet
					if(result.next()){ //check we have at least one result. If any, read data from record

						operation.setId(result.getInt(1));
						operation.setType(result.getString(2));
						operation.setAmount(result.getFloat(3));
						operation.setDescription(result.getString(4));
						operation.setAccountId(result.getInt(5));
						operation.setCreatedAt(result.getDate(6));
						operation.setUpdatedAt(result.getDate(7));
						operation.setDispute(result.getBoolean(8));

					}
				}catch (SQLException e) {
					System.err.println("getOperationById: problem with the result set");
					e.printStackTrace();
				}
			}catch (SQLException e) {
				System.err.println("getOperationById: problem with the prepared statement");
				e.printStackTrace();
			}
				
		}catch (SQLException e) {
			System.err.println("getOperationById: problem with the connection");
			e.printStackTrace();
		}
		
		return operation;	
	}
	
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
