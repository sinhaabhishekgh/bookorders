package com.sample.service.demo.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.service.demo.books.BusinessException.BookException;
import com.sample.service.demo.books.Entity.Ack;
import com.sample.service.demo.books.Entity.ExecutionOrder;
import com.sample.service.demo.books.Entity.InstrumentOrder;
import com.sample.service.demo.books.Entity.InstrumentOrderBook;
import com.sample.service.demo.books.service.ManageInstrumentOrderBookService;

@RestController
public class ManageBooksController {

	@Autowired
	private ManageInstrumentOrderBookService bookService;
	
	
	@RequestMapping( value ="/api/v1/book/open/{instruction}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ack> openBook(@PathVariable(name = "instruction") String instruction){
		try {
			bookService.openBook(instruction);
			return new ResponseEntity<Ack>(new Ack("Open Successfully"), HttpStatus.OK);
		} catch (BookException e) {
			return new ResponseEntity<Ack>(new Ack(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value ="/api/v1/book/{instruction}" , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getBook(@PathVariable(name="instruction") String instruction){
		try {
			return new ResponseEntity<InstrumentOrderBook>(bookService.getBook(instruction), HttpStatus.OK);
		} catch (BookException e) {
			return new ResponseEntity<Ack>(new Ack(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value ="/api/v1/book/{instruction}/orders/add" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ack> addOrders(@PathVariable(name="instruction") String instruction, @RequestBody List<InstrumentOrder> orders){
		try {
			bookService.addOrdersToBook(orders, instruction);
			return new ResponseEntity<Ack>(new Ack("Added Successfully"), HttpStatus.OK);
		} catch (BookException e) {
			return new ResponseEntity<Ack>(new Ack(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value ="/api/v1/book/close/{instruction}" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ack> closeBook(@PathVariable(name="instruction") String insCode){
		try {
			bookService.closeBook(insCode);
			return new ResponseEntity<Ack>(new Ack("Closed Successfully"), HttpStatus.OK);
		} catch (BookException e) {
			return new ResponseEntity<Ack>(new Ack(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value ="/api/v1/book/{instruction}/executions/add" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ack> addExecutionOrder(@PathVariable(name="instruction") String insCode, @RequestBody ExecutionOrder ex){
		try {
			bookService.addExcecutionToBook(ex, insCode);
			return new ResponseEntity<Ack>(new Ack("Added Successfully"), HttpStatus.OK);
		} catch (BookException e) {
			return new ResponseEntity<Ack>(new Ack(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
