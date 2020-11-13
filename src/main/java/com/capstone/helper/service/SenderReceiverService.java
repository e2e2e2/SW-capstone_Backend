package com.capstone.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.SendersAndReceivers;
import com.capstone.helper.repository.SenderReceiverRepository;

@Service("senderReceiverService")
public class SenderReceiverService {
	@Autowired
	private SenderReceiverRepository senderReceiverRepository;
	
	public List<SendersAndReceivers> findBySenderId(int senderId) {
		List<SendersAndReceivers> senderReceivers = new ArrayList<>();
		senderReceiverRepository.findBySenderId(senderId).forEach(e -> senderReceivers.add(e));
		return senderReceivers;
	}
	
	public SendersAndReceivers save(SendersAndReceivers senderReceiver) { 
		senderReceiverRepository.save(senderReceiver); 
		 return senderReceiver; 
	 }
	
}
