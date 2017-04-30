package com.jeegroupproject.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jeegroupproject.database.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.jeegroupproject.bcrypt.*;
import java.sql.Timestamp;

public class Person {
	private int id;
	private int externalId;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String dob;
	private String token;
	private String phoneNumber;
	private Date createdAt;
	private Date updatedAt;
	private int advisorId;
	private boolean isAdvisor;

	

	/**Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
	 * Must be consistent across the app or hashed passwords won't match
	 */
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
	
	
	
	/**
	 * Helper method to get a person by its externalId from database
	 * @param externalId
	 * @return the found user, If not found, all members of the user are empty
	 */
	public static Person getPersonByExternalId(int externalId){
		String query = "SELECT * FROM sac_person WHERE `person_external_id` = ?";
		Person person = new Person();
		
		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		// Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		try(Connection connection = DBConnectionFactory.getConnection()){ //Try with the resource connection 
			try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(query)){ //try with the preparedStatement
				pStatement.setInt(1, externalId);
				try(ResultSet result = pStatement.executeQuery()){ //try with the resultSet
					if(result.next()){ //check we have at least one result. If any, read data from record
						person.setId( result.getInt(1));
						person.setExternalId(result.getInt(2));
						person.setFirstname(result.getString(3));
						person.setLastname(result.getString(4));
						person.setEmail( result.getString(5));
						person.setPassword( result.getString(6));
						person.setDob(result.getString(7));
						person.setToken(result.getString(8));
						person.setPhoneNumber(result.getString(9));
						person.setCreatedAt(result.getDate(10));
						person.setUpdatedAt(result.getDate(11));
						person.setAdvisorId(result.getInt(12));
						person.setIsAdvisor(result.getBoolean(13));
					}
				}catch (SQLException e) {
					System.err.println("getPersonByExternalId: problem with the result set");
					e.printStackTrace();
				}
			}catch (SQLException e) {
				System.err.println("getPersonByExternalId: problem with the prepared statement");
				e.printStackTrace();
			}
				
		}catch (SQLException e) {
			System.err.println("getPersonByExternalId: problem with the connection");
			e.printStackTrace();
		}
		
		return person;	
	}
	
	
	
	/**
	 * Helper method to get a person based on its credentials
	 * @param externalId
	 * @return the found person, If not found, returns null
	 */
	public static Person getAuthenticatedPerson(Integer externalId, String email, String password){
		
		String queryExternalId = "SELECT * FROM sac_person WHERE `person_external_id` = ?";
		String queryEmail = "SELECT * FROM sac_person WHERE `person_email` = ?";
		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		//Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		//https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		
		Person personByExternalId;
		Person personByEmail;
		
		try(Connection connection = DBConnectionFactory.getConnection()){

			if(externalId != null){
				try(PreparedStatement pStatement1 = (PreparedStatement) connection.prepareStatement(queryExternalId)){
		
					pStatement1.setInt(1, externalId);
					try(ResultSet result1 = pStatement1.executeQuery()){
						
						if(result1.next()){ //check we have at least one result. If any, read data from record
							personByExternalId = new Person();
							
							personByExternalId.setId( result1.getInt(1));
							personByExternalId.setExternalId(result1.getInt(2));
							personByExternalId.setFirstname(result1.getString(3));
							personByExternalId.setLastname(result1.getString(4));
							personByExternalId.setEmail( result1.getString(5));
							personByExternalId.setPassword( result1.getString(6));
							personByExternalId.setDob(result1.getString(7));
							personByExternalId.setToken(result1.getString(8));
							personByExternalId.setPhoneNumber(result1.getString(9));
							personByExternalId.setCreatedAt(result1.getDate(10));
							personByExternalId.setUpdatedAt(result1.getDate(11));
							personByExternalId.setAdvisorId(result1.getInt(12));
							personByExternalId.setIsAdvisor(result1.getBoolean(13));
							
							if(checkPassword(password, personByExternalId.getPassword())){
		                        //before returning the person, update its token
		                        personByExternalId.regenerateToken();
		                        personByExternalId.persist();//save change to the token
		                        return personByExternalId;
		                    }
						}
					}catch (SQLException e) {
						System.err.println("getAuthenticatedPerson: problem with the result set");
						e.printStackTrace();
					}
				}catch (SQLException e) {
					System.err.println("getAuthenticatedPerson: problem with the prepared statement");
					e.printStackTrace();
				}
			}
		
			if(email != null){
				try(PreparedStatement pStatement2 = (PreparedStatement) connection.prepareStatement(queryEmail)){
					pStatement2.setString(1, email);
					try(ResultSet result2 = pStatement2.executeQuery()){
			
						
						if(result2.next()){ //check we have at least one result. If any, read data from record
							personByEmail = new Person();
											
							personByEmail.setId( result2.getInt(1));
							personByEmail.setExternalId(result2.getInt(2));
							personByEmail.setFirstname(result2.getString(3));
							personByEmail.setLastname(result2.getString(4));
							personByEmail.setEmail( result2.getString(5));
							personByEmail.setPassword( result2.getString(6));
							personByEmail.setDob(result2.getString(7));
							personByEmail.setToken(result2.getString(8));
							personByEmail.setPhoneNumber(result2.getString(9));
							personByEmail.setCreatedAt(result2.getDate(10));
							personByEmail.setUpdatedAt(result2.getDate(11));
							personByEmail.setAdvisorId(result2.getInt(12));
							personByEmail.setIsAdvisor(result2.getBoolean(13));
							
							if(checkPassword(password, personByEmail.getPassword())){
								personByEmail.regenerateToken();
		                        personByEmail.persist();//save change to the token
								return personByEmail;
								
							}
						}
					}catch (SQLException e) {
						System.err.println("getAuthenticatedPerson: problem with the result set");
						e.printStackTrace();
					}
				}catch (SQLException e) {
					System.err.println("getAuthenticatedPerson: problem with the prepared statement");
					e.printStackTrace();
				}
			}
		
		}catch (SQLException e) {
			System.err.println("getAuthenticatedPerson: problem with the connection");
			e.printStackTrace();
		}
		
		return null;	
	}
	
	
	
	/**
	 * Helper method to get a person based on both id and token
	 * for security reasons (harder to brute force token for a specific user)
	 * @param id
	 * @param token
	 * @return
	 */
	public static Person getAuthenticatedPersonByToken(Integer id, String token){
		String query ="SELECT * FROM sac_person where `person_id` = ? and `person_token` = ?";
		
		Person person = null;
		
		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		// Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		
		try(Connection connection = DBConnectionFactory.getConnection()){
			
			if(id !=null && token != null){
				try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(query)){
					pStatement.setInt(1, id);
					pStatement.setString(2, token);
					try(ResultSet result = pStatement.executeQuery()){
						if(result.next()){ // check we have at least one result. If any, read data from record
							person = new Person();
		                    
		                    person.setId( result.getInt(1));
							person.setExternalId(result.getInt(2));
							person.setFirstname(result.getString(3));
							person.setLastname(result.getString(4));
							person.setEmail( result.getString(5));
							person.setPassword( result.getString(6));
							person.setDob(result.getString(7));
							person.setToken(result.getString(8));
							person.setPhoneNumber(result.getString(9));
							person.setCreatedAt(result.getDate(10));
							person.setUpdatedAt(result.getDate(11));
							person.setAdvisorId(result.getInt(12));
							
							person.setIsAdvisor(result.getBoolean(13));
		                }
	            
					}catch (SQLException e) {
						System.err.println("getAuthenticatedPerson: problem with the result set");
						e.printStackTrace();
					}
				}catch (SQLException e) {
					System.err.println("getAuthenticatedPerson: problem with the prepared statement");
					e.printStackTrace();
				}
			}
		}catch (SQLException e) {
			System.err.println("getAuthenticatedPerson: problem with the connection");
			e.printStackTrace();
		}
	        
        return person;    
    }
		
	
	
	/**
	 * Method to generate a Token and set it for a person
	 * Remember to persist the person after generating the token
	 */
	public void regenerateToken(){ 
        //method to generate token
            //first concatenate externalID and the number of MS since Epoch
            //apply BCRYPT on it.
		String StringifiedclientIdParameter = ((Integer)this.getExternalId()).toString();
		String StringifiedDate = ((Long)System.currentTimeMillis()).toString();
		String StringifiedParameters = StringifiedclientIdParameter + StringifiedDate;
        this.setToken(hashPassword(StringifiedParameters));	
    }
	
	
	
	/**
	 * Method to save all person data to DB (already existing ==> update or new person ==> insert in DB)
	 */
	public void persist(){
        
		String queryInsert = "UPDATE sac_person SET person_external_id = ?, person_firstname = ?, person_lastname = ?, person_email = ?, person_password = ?, person_dob = ?, person_token = ?, person_phone_number = ?, person_created_At = ?, person_updated_at = ?, person_advisor_id = ?, person_is_advisor = ? WHERE person_id = ?  ;";
		String queryUpdate = "INSERT INTO `sac_person` (`person_external_id`, `person_firstname`, `person_lastname`, `person_email`, `person_password`, `person_dob`, `person_token`, `person_phone_number`, `person_created_At`, `person_updated_at`, `person_advisor_id`, `person_is_advisor`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
		
		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		//Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		//https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try(Connection connection = DBConnectionFactory.getConnection()){

	        //test if the person already has an id >=0 (id -1 means not yet created in db).
	        //if he does, he already exists in database, so we update it
	        if(this.getId() > 0){
	            //prepare a prepared statement for update
	            try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(queryInsert)){
		            pStatement.setInt(1, this.getExternalId());
		            pStatement.setString(2, this.getFirstname());
		            pStatement.setString(3, this.getLastname());
		            pStatement.setString(4, this.getEmail());
		            pStatement.setString(5, this.getPassword());
		            pStatement.setString(6, this.getDob());
		            pStatement.setString(7, this.getToken());
		            pStatement.setString(8, this.getPhoneNumber());
		            pStatement.setTimestamp(9, new java.sql.Timestamp(this.getCreatedAt().getTime()));
		            pStatement.setTimestamp(10, new java.sql.Timestamp(new Date().getTime()));
		            pStatement.setInt(11, this.getAdvisorId());
		            pStatement.setBoolean(12, this.getIsAdvisor());
		            pStatement.setInt(13, this.getId());
		
		            // execute update SQL statement
		            pStatement.executeUpdate();
				}catch (SQLException e) {
					System.err.println("persist: problem with the prepared statement at insert");
					e.printStackTrace();
				}

	        }else{//if he does not, one must insert it.
	            //prepare a prepared statement for insertion
	        	try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(queryUpdate, Statement.RETURN_GENERATED_KEYS)){
	
		            pStatement.setInt(1, this.getExternalId());
		            pStatement.setString(2, this.getFirstname());
		            pStatement.setString(3, this.getLastname());
		            pStatement.setString(4, this.getEmail());
		            pStatement.setString(5, this.getPassword());
		            pStatement.setString(6, this.getDob());
		            pStatement.setString(7, this.getToken());
		            pStatement.setString(8, this.getPhoneNumber());
		            pStatement.setTimestamp(9, new Timestamp(new Date().getTime()));
		            pStatement.setTimestamp(10, new Timestamp(new Date().getTime()));
		            pStatement.setInt(11, this.getAdvisorId());
		            pStatement.setBoolean(12, this.getIsAdvisor());
		
		            // execute update SQL statement
		            
		            int affectedRows = pStatement.executeUpdate();
		
		            if (affectedRows == 0) {
		                throw new SQLException("Creating person failed, no rows affected.");
		            }
		
		            try (ResultSet generatedKeys = pStatement.getGeneratedKeys()) {
		                if (generatedKeys.next()) {
		                    this.setId(generatedKeys.getInt(1));
		                }
		                else {
		                    throw new SQLException("Creating person failed, no ID obtained.");
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
	
	
	//TODO public List<Message> getMessages()
	
	
	/**
	 * @return the Account List for this Person
	 */
	public List<Account> getAccounts() {
		return Account.getAccountsByPersonId(this.id);
	}
	

	public List<Operation> getDisputedOperations() {
		List<Operation> disputedOperations = new ArrayList<Operation>();
		
		for(Account account : getAccounts()){
			disputedOperations.addAll(account.getDisputedOperations());
		}
		
		return disputedOperations;
		
		
	}
	
	/**
	 * 
	 * @param advisorId
	 * @return List of CLients By Advisor Id
	 */
	public static List<Person> getClientsByAdvisorId(int advisorId){
		String query = "SELECT * FROM sac_person WHERE `person_advisor_id` = ?";
		List<Person> personListByAdvisorId = new ArrayList<Person>();

		
		//Connection, PreparedStatement and Resultset have to be closed when finished being used
		// Since Java 7, these objects implement autocloseable so if there are given as parameters to a try clause they will be closed at the end
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		try(Connection connection = DBConnectionFactory.getConnection()){ //Try with the resource connection 
			try(PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement(query)){ //try with the preparedStatement
				pStatement.setInt(1, advisorId); 
				try(ResultSet result = pStatement.executeQuery()){ //try with the resultSet
					while(result.next()){ //check we have at least one result. If any, read data from record
						Person person = new Person();
	                    person.setId( result.getInt(1));
						person.setExternalId(result.getInt(2));
						person.setFirstname(result.getString(3));
						person.setLastname(result.getString(4));
						person.setEmail( result.getString(5));
						person.setPassword( result.getString(6));
						person.setDob(result.getString(7));
						person.setToken(result.getString(8));
						person.setPhoneNumber(result.getString(9));
						person.setCreatedAt(result.getDate(10));
						person.setUpdatedAt(result.getDate(11));
						person.setAdvisorId(result.getInt(12));
						
						personListByAdvisorId.add(person);

					}
				}catch (SQLException e) {
					System.err.println("getClientsByAdvisorId: problem with the result set");
					e.printStackTrace();
				}
			}catch (SQLException e) {
				System.err.println("getClientsByAdvisorId: problem with the prepared statement");
				e.printStackTrace();
			}
				
		}catch (SQLException e) {
			System.err.println("getClientsByAdvisorId: problem with the connection");
			e.printStackTrace();
		}
		
		return personListByAdvisorId;	
	}
	
	/**
	 * 
	 * @return List of Clients for an Advisor
	 * @throws Exception is called for not an advisor
	 */
	public List<Person> getClients() throws Exception{
		if(!this.getIsAdvisor()) {
			throw new Exception("This user is not an advisor");
		}else{
			return getClientsByAdvisorId(this.id);
		}	
	}
	

	
	// Getters and Setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getIsAdvisor() {
		return isAdvisor;
	}
	public void setIsAdvisor(boolean isAdvisor) {
		this.isAdvisor = isAdvisor;
	}

	public int getExternalId() {
		return externalId;
	}
	public void setExternalId(int externalId) {
		this.externalId = externalId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public int getAdvisorId() {
		return advisorId;
	}

	public void setAdvisorId(int advisorId) {
		this.advisorId = advisorId;
	}

}
