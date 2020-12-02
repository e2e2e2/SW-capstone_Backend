package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.NonActiveEvent;
import com.capstone.helper.repository.NonActiveEventRepository;

@Service("nonActiveEventService")
public class NonActiveEventService {
	@Autowired
	private NonActiveEventRepository nonActiveEventRepository;
	
	public NonActiveEvent findOne(Integer id) {
		return nonActiveEventRepository.getOne(id);
	}
	
	public NonActiveEvent save(NonActiveEvent nonActiveEvent) { 
		nonActiveEventRepository.save(nonActiveEvent); 
		 return nonActiveEvent;
	}

	public Integer toFalse(Integer id) { 
		NonActiveEvent nonActiveEvent = nonActiveEventRepository.findById(id).get();
		nonActiveEvent.toFalse();
		nonActiveEventRepository.save(nonActiveEvent);
		
		return id;
	}

	public boolean findTrueById(Integer eventId) {
		NonActiveEvent nonActiveEvent = nonActiveEventRepository.findById(eventId).get();
		
		return nonActiveEvent.getIsTrue();
	}
}
