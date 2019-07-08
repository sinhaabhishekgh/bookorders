package com.sample.service.demo.books.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.service.demo.books.BusinessException.BookException;
import com.sample.service.demo.books.BusinessException.ErrorConstants;
import com.sample.service.demo.books.Entity.BookStatus;
import com.sample.service.demo.books.Entity.ExecutionOrder;
import com.sample.service.demo.books.Entity.InstrumentOrder;
import com.sample.service.demo.books.Entity.InstrumentOrderBook;
import com.sample.service.demo.books.Entity.OrderPrice;
import com.sample.service.demo.books.Entity.OrderType;
import com.sample.service.demo.books.repository.ManageInstrumentOrderBookRepository;

@Service
public class ManageInstrumentOrderBookService {

	@Autowired
	private ManageInstrumentOrderBookRepository bookRepository;
	
	@Autowired
	private InstrumentService instrumentService;
	
	private static final Logger log = LoggerFactory.getLogger(ManageInstrumentOrderBookService.class);
	
	
	private void validateInstrument(String instrumentCode) throws BookException{
		if(!instrumentService.isValidInstrument(instrumentCode)) {
			throw new BookException(ErrorConstants.ERR_INVALID_INSTRUMENT);
		}
	}
	

	public InstrumentOrderBook openBook(String instrumentCode) throws BookException{
		
		validateInstrument(instrumentCode);
		
		
		InstrumentOrderBook book = this.bookRepository.findBookByInstrumentCode(instrumentCode);
		if(book == null) {
			book = new InstrumentOrderBook(instrumentCode, BookStatus.OPEN);
			this.bookRepository.addNew(book);
		}
		return book;
	}
	
	
	public InstrumentOrderBook addOrdersToBook(List<InstrumentOrder> instrumentOrders, String instrumentCode) throws BookException {
		
		validateInstrument(instrumentCode);	
		
		InstrumentOrderBook book = this.bookRepository.findBookByInstrumentCode(instrumentCode);
		if(book == null || (!book.getStatus().equals(BookStatus.OPEN))) {
			throw new BookException(ErrorConstants.ERR_BOOK_NOT_OPEN);
		}
		
		
		if(book.getStatus().equals(BookStatus.OPEN)) {
			for (InstrumentOrder instrumentOrder : instrumentOrders) {
				instrumentOrder.setType(OrderType.NORMAL);
				book.getOrders().add(instrumentOrder);
			}
			this.bookRepository.update(book);
		}
		return book;
	}
	
	public InstrumentOrderBook closeBook(String instrumentCode) throws BookException{
		
		validateInstrument(instrumentCode);
		
		InstrumentOrderBook book = this.bookRepository.findBookByInstrumentCode(instrumentCode);
		
		if(book == null) {
			throw new BookException(ErrorConstants.ERR_BOOK_NOT_OPEN);
		}
		if(book.getStatus().equals(BookStatus.OPEN)) {
			book.setStatus(BookStatus.CLOSE);
			this.bookRepository.update(book);
		}
		return book;
	}
	
	public InstrumentOrderBook addExcecutionToBook(ExecutionOrder executionOrder,  String instrumentCode) throws BookException {
		
		validateInstrument(instrumentCode);
		
		InstrumentOrderBook book = this.bookRepository.findBookByInstrumentCode(instrumentCode);
		if(book == null || book.getStatus().equals(BookStatus.OPEN)) {
			throw new BookException(ErrorConstants.ERR_BOOK_ADD_EX);
		}
		
		
		if(book.getStatus().equals(BookStatus.CLOSE)) {
			
			Optional<InstrumentOrder> currPrice = book.getOrders().stream().filter(p -> p.getType().equals(OrderType.EXECUTION)).findFirst();
			
			//Take the Existing Price for Excecution orders if any
			OrderPrice newPrice = currPrice.isPresent()?currPrice.get().getPrice():executionOrder.getPrice();
			
			for (InstrumentOrder exOrder : executionOrder.getOrders()) {
				exOrder.setPrice(newPrice);
				exOrder.setType(OrderType.EXECUTION);
				book.getOrders().add(exOrder);
			}
			
			this.bookRepository.update(book);
		}
		return book;
	}
	
	public InstrumentOrderBook getBook(String insCode) throws BookException{
		
		validateInstrument(insCode);
		return this.bookRepository.findBookByInstrumentCode(insCode);
	}
	
	
	
}
