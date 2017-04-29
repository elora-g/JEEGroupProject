package com.jeegroupproject.beans;

import java.util.Date;

public class Message {

	private int id;
	private String content;
	private int from;
	private int to;
	private Date createdAt;
	
	
	//TODO persist 	
	/**
	 * Method to save all message data to DB (already existing ==> no need to update (there is not updatedAt field in DB or new message ==> insert in DB)
	 */
	
	
	//TODO public static List<Message> getMessagesforClientAndAdvisor(int clientid, int advisorid) // Look up messages where clientid is in `from` or in `to`and where advisorid is in `from` or in `to`
	
	
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
