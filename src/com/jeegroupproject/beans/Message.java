package com.jeegroupproject.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.jeegroupproject.database.DBServiceSingleton;

public class Message {

	private int id;
	private String content;
	private int from;
	private int to;
	private Date createdAt;
	

	/**
	 * Method to save all message data to DB (already existing ==> no need to update (there is not updatedAt field in DB or new message ==> insert in DB)
	 */
public void persist() {
        
		String queryUpdate = "UPDATE sac_messages SET msg_content=?,msg_from=?,msg_to=?,msg_created_at=? WHERE msg_id = ?";
		String queryInsert = "INSERT INTO sac_messages( msg_content, msg_from, msg_to, msg_created_at) VALUES (?,?,?,?)";

		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		//Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		//https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try(Connection connection = DBServiceSingleton.getInstance().getConnection()){
        	
	        //test if the person already has an id >=0 (id -1 means not yet created in db).
	        //if he does, he already exists in database, so we update it
	        if(this.getId() > 0){
	            //prepare a prepared statement for update
	            try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(queryUpdate)){
	            	
	            	pStatement.setString(1, this.getContent());
		            pStatement.setInt(2, this.getFrom());
		            pStatement.setInt(3, this.getTo());
		            pStatement.setTimestamp(4, new java.sql.Timestamp(this.getCreatedAt().getTime()));
		            pStatement.setInt(5,this.getId());
		            // execute update SQL statement
		            pStatement.executeUpdate();
				}catch (SQLException e) {
					System.err.println("persist: problem with the prepared statement at insert");
					e.printStackTrace();
				}

	        }else{//if he does not, one must insert it.
	            //prepare a prepared statement for insertion
	        	try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS)){
	        		
	        		pStatement.setString(1, this.getContent());
		            pStatement.setInt(2, this.getFrom());
		            pStatement.setInt(3, this.getTo());
		            pStatement.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
		
		            // execute update SQL statement
		
		            int affectedRows = pStatement.executeUpdate();
		
		            if (affectedRows == 0) {
		                throw new SQLException("Creating message failed, no rows affected.");
		            }
		
		            try (ResultSet generatedKeys = pStatement.getGeneratedKeys()) {
		                if (generatedKeys.next()) {
		                    this.setId(generatedKeys.getInt(1));
		                }
		                else {
		                    throw new SQLException("Creating message failed, no ID obtained.");
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
	 * Method to get all messages between a client and his advisor
	 * @param clientid
	 * @param advisorid
	 * @return list of messages
	 */
	public static List<Message> getMessagesforClientAndAdvisor(int clientid, int advisorid) {
		String query = "SELECT * FROM  sac_messages WHERE (msg_from= ? OR  msg_from = ?) AND (msg_to = ? OR msg_to = ?) ORDER BY msg_id DESC;"; //get messages where both the client and the advisor appear in any of from and to
		
		List<Message> messageList = new ArrayList<Message>();
		
		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		// Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		try(Connection connection = DBServiceSingleton.getInstance().getConnection()){ //Try with the resource connection 
			try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(query)){ //try with the preparedStatement
				pStatement.setInt(1, (clientid)); 
				pStatement.setInt(2, (advisorid)); 
				pStatement.setInt(3, (clientid)); 
				pStatement.setInt(4, (advisorid)); 
				try(ResultSet result = pStatement.executeQuery()){ //try with the resultSet
					while(result.next()){ //check we have at least one result. If any, read data from record
						Message message = new Message();
						message.setId(result.getInt(1));
						message.setContent(result.getString(2));
						message.setFrom(result.getInt(3));
						message.setTo(result.getInt(4));
						message.setCreatedAt(result.getTimestamp(5));
						
						messageList.add(message);

					}
				}catch (SQLException e) {
					System.err.println("getMessagesforClientAndAdvisor: problem with the result set");
					e.printStackTrace();
				}
			}catch (SQLException e) {
				System.err.println("getMessagesforClientAndAdvisor: problem with the prepared statement");
				e.printStackTrace();
			}
				
		}catch (SQLException e) {
			System.err.println("getMessagesforClientAndAdvisor: problem with the connection");
			e.printStackTrace();
		}
		
		
		return messageList;
	}
	
	// Getters and Setters
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
