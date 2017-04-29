package com.jeegroupproject.beans;

public class Account {
	
	private int id;
	private String customerId;
	private float balance;
	private String type;
	private boolean isDefault;
	
	
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
