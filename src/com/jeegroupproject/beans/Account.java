package com.jeegroupproject.beans;

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
	
	//TODO public static List<Account> getAccountsByPersonId(int id) 
	
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
