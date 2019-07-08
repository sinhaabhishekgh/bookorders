package com.sample.service.demo.books.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.service.demo.books.repository.InstrumentRepository;

@Service
public class InstrumentService {

	
	private static final Logger log = LoggerFactory.getLogger(InstrumentService.class);
	
	@Autowired
	private InstrumentRepository instrumentRepository;
	
	public List<String> getAll(){
		return instrumentRepository.getAll();
	}
	
	public boolean isValidInstrument(String instrumentCode) {
		return instrumentRepository.isInstrumentExist(instrumentCode);
	}
}
