package com.jeegroupproject.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.jeegroupproject.database.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.jeegroupproject.bcrypt.*;
import java.sql.Timestamp;

public class User {
	private int id;
	private String name;
	private String surname;
	private String password;
	private boolean is_employee;
	private int client_id;
	private String access_token;
	private String email;
	private String dob;
	private String phoneNumber;
	private Date created_at;
	private Date updated_at;
	private int advisor_id;
	
	
  	

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
	
	
	
	/**
	 * Helper method to get a client by its client id from database
	 * @param client_id
	 * @return the found user, If not found, all members of the user are empty
	 */
	public static User getUserByClient_id(int client_id){
		
		Connection connection = DBConnectionFactory.getConnection(); //returns a prepared connection to the database
		User user = new User();
		try {
			PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM sac_person WHERE `person_external_id` = ?");
			pStatement.setInt(2, client_id);
			ResultSet result = pStatement.executeQuery();
	
			if(result.next()){ //check we have at least one result
				user.setId( result.getInt(1));
				user.setClient_id(result.getInt(2));
				user.setName(result.getString(3));
				user.setSurname(result.getString(4));
				user.setEmail( result.getString(5));
				user.setPassword( result.getString(6));
				user.setDob(result.getString(7));
				user.setAccess_token(result.getString(8));
				user.setPhoneNumber(result.getString(9));
				user.setCreated_at(result.getDate(10));
				user.setUpdated_at(result.getDate(11));
				user.setAdvisor_id(result.getInt(12));
				user.setIs_employee(result.getBoolean(13));

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
				PreparedStatement pStatement1 = (PreparedStatement) connection.prepareStatement("SELECT * FROM sac_person WHERE `person_external_id` = ?");
				pStatement1.setInt(2, clientId);
				ResultSet result1 = pStatement1.executeQuery();
				if(result1.next()){ //check we have at least one result
					userByClientID = new User();
					
					userByClientID.setId( result1.getInt(1));
					userByClientID.setClient_id(result1.getInt(2));
					userByClientID.setName(result1.getString(3));
					userByClientID.setSurname(result1.getString(4));
					userByClientID.setEmail( result1.getString(5));
					userByClientID.setPassword( result1.getString(6));
					userByClientID.setDob(result1.getString(7));
					userByClientID.setAccess_token(result1.getString(8));
					userByClientID.setPhoneNumber(result1.getString(9));
					userByClientID.setCreated_at(result1.getDate(10));
					userByClientID.setUpdated_at(result1.getDate(11));
					userByClientID.setAdvisor_id(result1.getInt(12));
					userByClientID.setIs_employee(result1.getBoolean(13));
					
					if(checkPassword(password, userByClientID.getPassword())){
                        //before returning the client, update its token
                        userByClientID.regenerateToken();
                        userByClientID.persist();//save change to the token
                        return userByClientID;
                    }
				}
			}
			
			if(email != null){
				PreparedStatement pStatement2 = (PreparedStatement) connection.prepareStatement("SELECT * FROM sac_person WHERE `person_email` = ?");
				pStatement2.setString(1, email);
				ResultSet result2 = pStatement2.executeQuery();
		
				
				if(result2.next()){ //check we have at least one result
					userByEmail = new User();
									
					userByEmail.setId( result2.getInt(1));
					userByEmail.setClient_id(result2.getInt(2));
					userByEmail.setName(result2.getString(3));
					userByEmail.setSurname(result2.getString(4));
					userByEmail.setEmail( result2.getString(5));
					userByEmail.setPassword( result2.getString(6));
					userByEmail.setDob(result2.getString(7));
					userByEmail.setAccess_token(result2.getString(8));
					userByEmail.setPhoneNumber(result2.getString(9));
					userByEmail.setCreated_at(result2.getDate(10));
					userByEmail.setUpdated_at(result2.getDate(11));
					userByEmail.setAdvisor_id(result2.getInt(12));
					userByEmail.setIs_employee(result2.getBoolean(13));
					
					if(checkPassword(password, userByEmail.getPassword())){
						userByEmail.regenerateToken();
                        userByEmail.persist();//save change to the token
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
	
	public static User getAuthenticatedUserByToken(Integer userId, String token){
		
		Connection connection = DBConnectionFactory.getConnection(); // returns a prepared connection to the DB
		User user;
		try{
			
			if(userId !=null && token != null){
				PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement("SELECT * FROM sac_person where person_id = ? and person_token = ?");
				pStatement.setInt(1, userId);
				pStatement.setString(2, token);
				ResultSet result = pStatement.executeQuery();
				
				if(result.next()){ // check we have at least one result
					user = new User();
                    
                    user.setId( result.getInt(1));
					user.setClient_id(result.getInt(2));
					user.setName(result.getString(3));
					user.setSurname(result.getString(4));
					user.setEmail( result.getString(5));
					user.setPassword( result.getString(6));
					user.setDob(result.getString(7));
					user.setAccess_token(result.getString(8));
					user.setPhoneNumber(result.getString(9));
					user.setCreated_at(result.getDate(10));
					user.setUpdated_at(result.getDate(11));
					user.setAdvisor_id(result.getInt(12));
					user.setIs_employee(result.getBoolean(13));
                }
            }
            
                
        } catch (SQLException e) {
            System.err.println("problem querying");
            e.printStackTrace();
        }
        
        return null;    
    }
		
	public void regenerateToken(){ 
        //the token is 
            //first contactenate clientID and the number of MS since Epoch
            //apply BCRYPT on it.
		String StringifiedclientIdParameter = ((Integer)this.getClient_id()).toString();
		String StringifiedDate = ((Long)System.currentTimeMillis()).toString();
		String StringifiedParameters = StringifiedclientIdParameter + StringifiedDate;
        this.setAccess_token(hashPassword(StringifiedParameters));	
    }
	
	public void persist() throws SQLException{
        // prepare a connection
        Connection connection = DBConnectionFactory.getConnection(); //returns a prepared connection to the database
        
        //test if the user already has an ID >=0 (id -1 means not yet created in db).
        //if he does, he already exists in database, so we update it
        if(this.getId() > 0){
            //prepare a prepared statement for update
            PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement("UPDATE sac_person SET person_external_id = ?, person_firstname = ?, person_lastname = ?, person_email = ?, person_password = ?, person_dob = ?, person_token = ?, person_phone_number = ?, person_created_At = ?, person_updated_at = ?, person_advisor_id = ?, person_is_advisor = ? WHERE `id` = ?  ");
            pStatement.setInt(1, this.getId());
            pStatement.setInt(2, this.getClient_id());
            pStatement.setString(3, this.getName());
            pStatement.setString(4, this.getSurname());
            pStatement.setString(5, this.getEmail());
            pStatement.setString(6, this.getPassword());
            pStatement.setString(7, this.getDob());
            pStatement.setString(8, this.getAccess_token());
            pStatement.setString(9, this.getPhoneNumber());
            pStatement.setTimestamp(10, new java.sql.Timestamp(this.getCreated_at().getTime()));
            pStatement.setTimestamp(11, new java.sql.Timestamp(this.getUpdated_at().getTime()));
            pStatement.setInt(12, this.getAdvisor_id());
            pStatement.setBoolean(13, this.isIs_employee());

            // execute update SQL statement
            pStatement.executeUpdate();

        }else{//if he does not, one must insert it.
            //prepare a prepared statement for insertion
        	
            PreparedStatement pStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO `sac_person` (`person_external_id`, `person_firstname`, `person_lastname`, `person_email`, `person_password`, `person_dob`, `person_token`, `person_phone_number`, `person_created_At`, `person_updated_at`, `person_advisor_id`, `person_is_advisor`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
            pStatement.setInt(1, this.getId());
            pStatement.setInt(2, this.getClient_id());
            pStatement.setString(3, this.getName());
            pStatement.setString(4, this.getSurname());
            pStatement.setString(5, this.getEmail());
            pStatement.setString(6, this.getPassword());
            pStatement.setString(7, this.getDob());
            pStatement.setString(8, this.getAccess_token());
            pStatement.setString(9, this.getPhoneNumber());
            pStatement.setTimestamp(10, new java.sql.Timestamp(this.getCreated_at().getTime()));
            pStatement.setTimestamp(11, new java.sql.Timestamp(this.getUpdated_at().getTime()));
            pStatement.setInt(12, this.getAdvisor_id());
            pStatement.setBoolean(13, this.isIs_employee());

            // execute update SQL stetement
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
        }


    }

	
	
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

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public int getAdvisor_id() {
		return advisor_id;
	}

	public void setAdvisor_id(int advisor_id) {
		this.advisor_id = advisor_id;
	}





	

}
