package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.Home;
import com.capstone.helper.repository.HomeRepository;

@Service("homeService")
public class HomeService {
	@Autowired
	HomeRepository homeRepository;
	
	public Home findByUserId(int userId) {
		return homeRepository.findByUserId(userId);
	}
}
