package com.sample.service.demo.books.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity To keep orders info about specific instrument
 * @author Abhishek
 *
 */
public class InstrumentOrderBook implements Serializable{

	

	private static final long serialVersionUID = -2543237586672769958L;
	
	private String instrumentCode;
	private BookStatus status;
	private List<InstrumentOrder> orders;
	
	
	public InstrumentOrderBook() {
		super();
	}
	
	public InstrumentOrderBook(String instrumentCode, BookStatus status) {
		super();
		this.instrumentCode = instrumentCode;
		this.status = status;
	}
	
	
	public String getInstrumentCode() {
		return instrumentCode;
	}
	public void setInstrumentCode(String instrumentCode) {
		this.instrumentCode = instrumentCode;
	}
	public BookStatus getStatus() {
		return status;
	}
	public void setStatus(BookStatus status) {
		this.status = status;
	}
	public List<InstrumentOrder> getOrders() {
		orders = (this.orders == null)?new ArrayList<InstrumentOrder>():this.orders;
		return orders;
	}
	public void setOrders(List<InstrumentOrder> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "InstrumentOrderBook [instrumentCode=" + instrumentCode + ", status=" + status + ", orders=" + orders
				+ "]";
	}
	
	public List<InstrumentOrder> getOrdersByType(OrderType  orderType){
		return this.orders.stream().filter(p -> p.getType().equals(orderType)).collect(Collectors.toList());
	}

	
}
