package com.sample.service.demo.books.Entity;

import java.io.Serializable;

public class InstrumentOrder implements Serializable{

	private static final long serialVersionUID = 9109923390018211698L;

	private Long orderQuantity;
	private OrderPrice price;
	private OrderType type;
	
	public InstrumentOrder() {
		super();
	}
	
	public InstrumentOrder(Long orderQuantity, OrderPrice price, OrderType type) {
		super();
		this.orderQuantity = orderQuantity;
		this.price = price;
		this.type = type;
	}
	
	public InstrumentOrder(Long orderQuantity, OrderPrice price) {
		super();
		this.orderQuantity = orderQuantity;
		this.price = price;
	}
	
	
	public Long getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(Long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public OrderPrice getPrice() {
		return price;
	}
	public void setPrice(OrderPrice price) {
		this.price = price;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "InstrumentOrder [orderQuantity=" + orderQuantity + ", price=" + price + ", type=" + type + "]";
	}

	
	
	
}
