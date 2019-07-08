package com.sample.service.demo.books.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.service.demo.books.Entity.BookStatus;
import com.sample.service.demo.books.Entity.ExecutionOrder;
import com.sample.service.demo.books.Entity.InstrumentOrder;
import com.sample.service.demo.books.Entity.InstrumentOrderBook;
import com.sample.service.demo.books.Entity.OrderPrice;
import com.sample.service.demo.books.Entity.OrderType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageInstrumentOrderBookServiceIntegrationTest {

	
	
	
	 @Autowired
	 private ManageInstrumentOrderBookService bookService;
	
	 
	 @Test
	 public void testOpenBook() throws Exception{
		 
		 String instrumentCode = "INS2";
		 InstrumentOrderBook testBook = bookService.openBook(instrumentCode);
		 assertNotNull(testBook);
		 assertEquals(instrumentCode, testBook.getInstrumentCode());
		 assertEquals(BookStatus.OPEN, testBook.getStatus());
		 
	 }
	 
	 @Test
	 public void testAddOrders() throws Exception{
		 String instrumentCode = "INS3";
		 List<InstrumentOrder> orders = new ArrayList<InstrumentOrder>();
		 orders.add(new InstrumentOrder(10l, OrderPrice.LIMIT));
		 orders.add(new InstrumentOrder(6l, OrderPrice.MARKET));
		 
		 InstrumentOrderBook testBook = bookService.openBook(instrumentCode);
		 testBook = bookService.addOrdersToBook(orders, instrumentCode);
		 
		 assertNotNull(testBook);
		 assertEquals(instrumentCode, testBook.getInstrumentCode());
		 assertEquals(BookStatus.OPEN, testBook.getStatus());
	     assertThat(!testBook.getOrders().isEmpty());
	     assertEquals(OrderPrice.LIMIT, orders.get(0).getPrice());
	     assertEquals(OrderPrice.MARKET, orders.get(1).getPrice());
	 }
	 
	 @Test
	 public void testCloseBook() throws Exception{
		 String instrumentCode = "INS4";
		 
		 InstrumentOrderBook testBook = bookService.openBook(instrumentCode);
		 testBook = bookService.closeBook(instrumentCode);
		 
		 assertNotNull(testBook);
	     assertEquals(instrumentCode, testBook.getInstrumentCode());
	     assertEquals(BookStatus.CLOSE, testBook.getStatus());
	 }
	 
	 @Test
	 public void testAddExcecutionToBook() throws Exception {
		 
		 	String instrumentCode = "INS5";
		 	InstrumentOrderBook testBook = bookService.openBook(instrumentCode);
			testBook = bookService.closeBook(instrumentCode);
			 
	    	ExecutionOrder executionOrder = new ExecutionOrder();
	    	executionOrder.setPrice(OrderPrice.MARKET);
	    	executionOrder.getOrders().add(new InstrumentOrder(10l, OrderPrice.LIMIT));
	    	executionOrder.getOrders().add(new InstrumentOrder(6l, OrderPrice.MARKET));
	    	
	    	testBook = bookService.addExcecutionToBook(executionOrder, instrumentCode);
	    	assertNotNull(testBook);
	    	assertEquals(instrumentCode, testBook.getInstrumentCode());
	    	assertEquals(BookStatus.CLOSE, testBook.getStatus());
	    	assertEquals(OrderPrice.MARKET, testBook.getOrders().stream().filter(p -> p.getType().equals(OrderType.EXECUTION)).findFirst().get().getPrice());
	 }
	 
}
