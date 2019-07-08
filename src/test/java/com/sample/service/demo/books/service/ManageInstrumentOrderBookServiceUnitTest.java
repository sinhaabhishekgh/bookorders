package com.sample.service.demo.books.service;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.sample.service.demo.books.Entity.BookStatus;
import com.sample.service.demo.books.Entity.ExecutionOrder;
import com.sample.service.demo.books.Entity.InstrumentOrder;
import com.sample.service.demo.books.Entity.InstrumentOrderBook;
import com.sample.service.demo.books.Entity.OrderPrice;
import com.sample.service.demo.books.Entity.OrderType;
import com.sample.service.demo.books.repository.ManageInstrumentOrderBookRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ManageInstrumentOrderBookServiceUnitTest {

	MockMvc mockMvc;
	
	private String testInstrument = "INS4";
	private InstrumentOrderBook  book;
	
	@Autowired
    protected WebApplicationContext wac;
	
    @Autowired
    ManageInstrumentOrderBookService bookService;
    
    @MockBean
    ManageInstrumentOrderBookRepository bookRepository;
    
    @MockBean
    InstrumentService instrumentService;
    
    @Before
    public void init() {
    	book = new InstrumentOrderBook();
    	book.setInstrumentCode(testInstrument);
    	
    	
    	
    }
    
    
    @Test
    public void testOpenBook() throws Exception {
        // Mocking service
    	when(instrumentService.isValidInstrument(any(String.class))).thenReturn(true);
    	when(bookRepository.findBookByInstrumentCode(any(String.class))).thenReturn(null);
    	when(bookRepository.addNew(any(InstrumentOrderBook.class))).thenReturn(book);
    	
    	InstrumentOrderBook testBook = bookService.openBook(testInstrument);
    	assertNotNull(testBook);
    	assertEquals(testInstrument, testBook.getInstrumentCode());
    	assertEquals(BookStatus.OPEN, testBook.getStatus());
    	
    }
    
    @Test
    public void testAddOrdersToBook() throws Exception {
        // Mocking service
    	this.book.setStatus(BookStatus.OPEN);
    	when(instrumentService.isValidInstrument(any(String.class))).thenReturn(true);
    	when(bookRepository.findBookByInstrumentCode(any(String.class))).thenReturn(book);
    	when(bookRepository.update(any(InstrumentOrderBook.class))).thenReturn(book);
    	
    	List<InstrumentOrder> orders = new ArrayList<InstrumentOrder>();
    	orders.add(new InstrumentOrder(10l, OrderPrice.LIMIT));
    	orders.add(new InstrumentOrder(6l, OrderPrice.MARKET));
    	
    	InstrumentOrderBook testBook = bookService.addOrdersToBook(orders, testInstrument);
    	assertNotNull(testBook);
    	assertEquals(testInstrument, testBook.getInstrumentCode());
    	assertThat(!testBook.getOrders().isEmpty());
    	assertEquals(OrderPrice.LIMIT, orders.get(0).getPrice());
    	assertEquals(OrderPrice.MARKET, orders.get(1).getPrice());	
    }

    @Test
    public void testCloseBook() throws Exception {
        // Mocking service
    	this.book.setStatus(BookStatus.OPEN);
    	when(instrumentService.isValidInstrument(any(String.class))).thenReturn(true);
    	when(bookRepository.findBookByInstrumentCode(any(String.class))).thenReturn(book);
    	when(bookRepository.update(any(InstrumentOrderBook.class))).thenReturn(book);
    	
    	InstrumentOrderBook testBook = bookService.closeBook(testInstrument);
    	assertNotNull(testBook);
    	assertEquals(testInstrument, testBook.getInstrumentCode());
    	assertEquals(BookStatus.CLOSE, testBook.getStatus());
    }
    
    @Test
    public void testAddExcecutionToBook() throws Exception {
        // Mocking service
    	this.book.setStatus(BookStatus.CLOSE);
    	when(instrumentService.isValidInstrument(any(String.class))).thenReturn(true);
    	when(bookRepository.findBookByInstrumentCode(any(String.class))).thenReturn(book);
    	when(bookRepository.update(any(InstrumentOrderBook.class))).thenReturn(book);
    	
    	ExecutionOrder executionOrder = new ExecutionOrder();
    	executionOrder.setPrice(OrderPrice.MARKET);
    	executionOrder.getOrders().add(new InstrumentOrder(10l, OrderPrice.LIMIT));
    	executionOrder.getOrders().add(new InstrumentOrder(6l, OrderPrice.MARKET));
    	
    	InstrumentOrderBook testBook = bookService.addExcecutionToBook(executionOrder, testInstrument);
    	assertNotNull(testBook);
    	assertEquals(testInstrument, testBook.getInstrumentCode());
    	assertEquals(BookStatus.CLOSE, testBook.getStatus());
    	assertEquals(OrderPrice.MARKET, testBook.getOrders().stream().filter(p -> p.getType().equals(OrderType.EXECUTION)).findFirst().get().getPrice());
    }
    
    
}
