package com.sample.service.demo.books.repository;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class InstrumentRepository {

	
	private List<String> instruments;
	
	@PostConstruct
	private void init() {
		instruments = Arrays.asList("INS1", "INS2", "INS3", "INS4", "INS5", "INS6", "INS7", "INS8", "INS9");
	}
	
	public List<String> getAll() {
		return this.instruments;
	}
	
	public boolean isInstrumentExist(String instrumentCode) {
		return this.instruments.indexOf(instrumentCode.toUpperCase()) != -1;
	}
}
