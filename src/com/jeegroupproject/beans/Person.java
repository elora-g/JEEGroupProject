package com.jeegroupproject.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jeegroupproject.database.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
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
		
		Connection connection = DBConnectionFactory.getConnection(); //returns a prepared connection to the database
		Person person = new Person();
		try {
			PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM sac_person WHERE `person_external_id` = ?");
			pStatement.setInt(2, externalId);
			ResultSet result = pStatement.executeQuery();
	
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
				person.setAdvisor(result.getBoolean(13));

			}
				
		} catch (SQLException e) {
			System.err.println("problem querying");
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
		
		Connection connection = DBConnectionFactory.getConnection(); //returns a prepared connection to the DB
		Person personByExternalId;
		Person personByEmail;
		try {
			if(externalId != null){
				PreparedStatement pStatement1 = (PreparedStatement) connection.prepareStatement("SELECT * FROM sac_person WHERE `person_external_id` = ?");
				pStatement1.setInt(2, externalId);
				ResultSet result1 = pStatement1.executeQuery();
				
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
					personByExternalId.setAdvisor(result1.getBoolean(13));
					
					if(checkPassword(password, personByExternalId.getPassword())){
                        //before returning the person, update its token
                        personByExternalId.regenerateToken();
                        personByExternalId.persist();//save change to the token
                        return personByExternalId;
                    }
				}
			}
			
			if(email != null){
				PreparedStatement pStatement2 = (PreparedStatement) connection.prepareStatement("SELECT * FROM sac_person WHERE `person_email` = ?");
				pStatement2.setString(1, email);
				ResultSet result2 = pStatement2.executeQuery();
		
				
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
					personByEmail.setAdvisor(result2.getBoolean(13));
					
					if(checkPassword(password, personByEmail.getPassword())){
						personByEmail.regenerateToken();
                        personByEmail.persist();//save change to the token
						return personByEmail;
						
					}
				}
			}
			
				
		} catch (SQLException e) {
			System.err.println("problem querying");
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
		
		Connection connection = DBConnectionFactory.getConnection(); // returns a prepared connection to the DB
		Person person = null;
		try{
			
			if(id !=null && token != null){
				PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM sac_person where `person_id` = ? and `person_token` = ?");
				pStatement.setInt(1, id);
				pStatement.setString(2, token);
				ResultSet result = pStatement.executeQuery();
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
					
					person.setAdvisor(result.getBoolean(13));
                }
            }
            
                
        } catch (SQLException e) {
            System.err.println("problem querying");
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
	 * Method to save all person data to DB
	 */
	public void persist(){
        
        Connection connection = DBConnectionFactory.getConnection(); //returns a prepared connection to the database
        
        PreparedStatement pStatement = null;
        //test if the person already has an id >=0 (id -1 means not yet created in db).
        //if he does, he already exists in database, so we update it
        if(this.getId() > 0){
            //prepare a prepared statement for update
            System.err.println("trying to update a record !");
			try {
				pStatement = (PreparedStatement) connection.prepareStatement("UPDATE sac_person SET person_external_id = ?, person_firstname = ?, person_lastname = ?, person_email = ?, person_password = ?, person_dob = ?, person_token = ?, person_phone_number = ?, person_created_At = ?, person_updated_at = ?, person_advisor_id = ?, person_is_advisor = ? WHERE person_id = ?  ;");
			
	            pStatement.setInt(1, this.getExternalId());
	            pStatement.setString(2, this.getFirstname());
	            pStatement.setString(3, this.getLastname());
	            pStatement.setString(4, this.getEmail());
	            pStatement.setString(5, this.getPassword());
	            pStatement.setString(6, this.getDob());
	            pStatement.setString(7, this.getToken());
	            pStatement.setString(8, this.getPhoneNumber());
	            pStatement.setTimestamp(9, new java.sql.Timestamp(this.getCreatedAt().getTime()));
	            pStatement.setTimestamp(10, new java.sql.Timestamp(this.getUpdatedAt().getTime()));
	            pStatement.setInt(11, this.getAdvisorId());
	            pStatement.setBoolean(12, this.isAdvisor());
	            pStatement.setInt(13, this.getId());
	
	            // execute update SQL statement
	            pStatement.executeUpdate();
			} catch (SQLException e) {
			
	            System.err.println("Update of a record failed !");
				e.printStackTrace();
			}

        }else{//if he does not, one must insert it.
            //prepare a prepared statement for insertion
        	try {
	            pStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO `sac_person` (`person_external_id`, `person_firstname`, `person_lastname`, `person_email`, `person_password`, `person_dob`, `person_token`, `person_phone_number`, `person_created_At`, `person_updated_at`, `person_advisor_id`, `person_is_advisor`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");

	            pStatement.setInt(1, this.getExternalId());
	            pStatement.setString(2, this.getFirstname());
	            pStatement.setString(3, this.getLastname());
	            pStatement.setString(4, this.getEmail());
	            pStatement.setString(5, this.getPassword());
	            pStatement.setString(6, this.getDob());
	            pStatement.setString(7, this.getToken());
	            pStatement.setString(8, this.getPhoneNumber());
	            pStatement.setTimestamp(9, new Timestamp(this.getCreatedAt().getTime()));
	            pStatement.setTimestamp(10, new Timestamp(this.getUpdatedAt().getTime()));
	            pStatement.setInt(11, this.getAdvisorId());
	            pStatement.setBoolean(12, this.isAdvisor());
	
	            // execute update SQL statement
	            pStatement.executeUpdate();
	
	            int affectedRows = pStatement.executeUpdate();
	
	            if (affectedRows == 0) {
	                throw new SQLException("Creating user failed, no rows affected.");
	            }
	
	            try (ResultSet generatedKeys = pStatement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    this.setId(generatedKeys.getInt(1));
	                }
	                else {
	                    throw new SQLException("Creating user failed, no ID obtained.");
	                }
	            }
	            
	        } catch (SQLException e) {

				e.printStackTrace();
			}
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
	public boolean isAdvisor() {
		return isAdvisor;
	}
	public void setAdvisor(boolean isAdvisor) {
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
