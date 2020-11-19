package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.ReceiverEnvironment;
import com.capstone.helper.repository.ReceiverEnvironmentRepository;

@Service("receiverEnvironmentService")
public class ReceiverEnvironmentService {
	@Autowired
	ReceiverEnvironmentRepository receiverEnvironmentRepository;
	
	public ReceiverEnvironment findByUserId(int userId){
		ReceiverEnvironment receiverEnvironment = receiverEnvironmentRepository.findByUserId(userId);
		return  receiverEnvironment;
	}
}
