package com.sample.service.demo.books.Entity;

import java.io.Serializable;

public class Ack implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2633568744206821907L;
	
	private String ackMessage;
	
	public Ack(String ackMessage) {
		super();
		this.ackMessage = ackMessage;
	}

	public String getAckMessage() {
		return ackMessage;
	}

	public void setAckMessage(String ackMessage) {
		this.ackMessage = ackMessage;
	}

	
	
}
