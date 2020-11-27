package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.HomeInEvent;
import com.capstone.helper.repository.HomeInEventRepository;

@Service("homeInEventService")
public class HomeInEventService {
	@Autowired
	HomeInEventRepository homeInEventRepository;
	
	public HomeInEvent save( HomeInEvent homeInEvent) {
		homeInEventRepository.save(homeInEvent);
		return homeInEvent;
	}
}
