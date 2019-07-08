package com.sample.service.demo.books.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sample.service.demo.books.Entity.InstrumentOrder;
import com.sample.service.demo.books.Entity.InstrumentOrderBook;

@Repository
public class ManageInstrumentOrderBookRepository {

	private Map<String, InstrumentOrderBook> books;

	public ManageInstrumentOrderBookRepository() {
		super();
		this.books = new HashMap<String, InstrumentOrderBook>();
	}
	
	
	public InstrumentOrderBook findBookByInstrumentCode(String instrumentCode) {
		return this.books.get(instrumentCode);
	}
	
	public InstrumentOrderBook addNew(InstrumentOrderBook book) {
		return this.books.put(book.getInstrumentCode(), book);
	}
	
	public InstrumentOrderBook addNewInstrumentOrder(InstrumentOrder order, String instrumentCode) {
		 this.books.get(instrumentCode).getOrders().add(order);
		 return this.books.get(instrumentCode);
	}
	
	public InstrumentOrderBook update(InstrumentOrderBook book) {
		return this.books.put(book.getInstrumentCode(), book);
	}
	
}
