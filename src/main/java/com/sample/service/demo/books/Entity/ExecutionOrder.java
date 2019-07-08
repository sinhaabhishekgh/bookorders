package com.sample.service.demo.books.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExecutionOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3705135615119352747L;
	
	private OrderPrice price;
	private List<InstrumentOrder> orders;
	
	
	public OrderPrice getPrice() {
		return price;
	}
	public void setPrice(OrderPrice price) {
		this.price = price;
	}
	public List<InstrumentOrder> getOrders() {
		this.orders  = (this.orders == null)?new ArrayList<InstrumentOrder>():this.orders;
		return orders;
	}
	public void setOrders(List<InstrumentOrder> orders) {
		this.orders = orders;
	}
	
	
}
