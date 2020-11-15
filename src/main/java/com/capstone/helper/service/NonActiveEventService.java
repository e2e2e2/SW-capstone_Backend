package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.NonActiveEvent;
import com.capstone.helper.repository.NonActiveEventRepository;

@Service("nonActiveEventService")
public class NonActiveEventService {
	@Autowired
	private NonActiveEventRepository nonActiveEventRepository;
	
	
	public NonActiveEvent save(NonActiveEvent nonActiveEvent) { 
		nonActiveEventRepository.save(nonActiveEvent); 
		 return nonActiveEvent;
	}
}
