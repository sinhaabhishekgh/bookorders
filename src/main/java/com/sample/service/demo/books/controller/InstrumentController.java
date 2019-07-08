package com.sample.service.demo.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.service.demo.books.service.InstrumentService;

@RestController
public class InstrumentController {

	@Autowired
	private InstrumentService instrumentService;
	
	
	@GetMapping("/api/v1/instruments")
	public ResponseEntity<List> getAllInstruments(){
		List res = instrumentService.getAll();
		return new ResponseEntity<List>(res, HttpStatus.OK);
		 
	}
}
