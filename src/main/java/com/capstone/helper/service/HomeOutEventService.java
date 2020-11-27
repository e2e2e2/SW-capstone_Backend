package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.HomeOutEvent;
import com.capstone.helper.repository.HomeOutEventRepository;

@Service("homeOutEventService")
public class HomeOutEventService {
	@Autowired
	HomeOutEventRepository homeOutEventRepository;
	
	public HomeOutEvent save(HomeOutEvent homeOutEvent) {
		homeOutEventRepository.save(homeOutEvent);
		return homeOutEvent;
	}
	
}
