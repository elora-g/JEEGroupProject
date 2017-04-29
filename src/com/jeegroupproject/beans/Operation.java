package com.jeegroupproject.beans;

import java.util.Date;

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
	
	//TODO public static List<Operation> getOperationsByAccountId(int id)
	
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
