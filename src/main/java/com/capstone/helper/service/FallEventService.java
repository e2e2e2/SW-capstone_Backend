package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.FallEvent;
import com.capstone.helper.repository.FallEventRepository;

@Service("fallEventService")
public class FallEventService {
	@Autowired
	private FallEventRepository fallEventRepository;
	
	public FallEvent findOne(Integer id) {
		return fallEventRepository.getOne(id);
	}
	
	public FallEvent save(FallEvent fall) { 
		fallEventRepository.save(fall); 
		 return fall;
	}
	
}
