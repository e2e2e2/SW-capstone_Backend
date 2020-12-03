package com.capstone.helper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.SenderAndReceiver;
import com.capstone.helper.model.SenderAndReceiverQueue;
import com.capstone.helper.repository.SenderAndReceiverQueueRepository;
import com.capstone.helper.repository.SenderAndReceiverRepository;

@Service("senderReceiverQueueService")
public class SendersAndReceiversQueueService {

	@Autowired
	private SenderAndReceiverQueueRepository senderReceiverQueueRepository;
	
	public List<SenderAndReceiverQueue> findByReceiverId(int targetId) {
		List<SenderAndReceiverQueue> senderReceiversQueue = new ArrayList<>();
		senderReceiverQueueRepository.findByTargetId(targetId).forEach(e -> senderReceiversQueue.add(e));
		return senderReceiversQueue;
	}
	

	public SenderAndReceiverQueue findById(int id) {		
		SenderAndReceiverQueue senderReceiversQueue = senderReceiverQueueRepository.getOne(id);
		return senderReceiversQueue;
	}
	
	public SenderAndReceiverQueue save(SenderAndReceiverQueue senderReceiverQueue) { 
		senderReceiverQueueRepository.save(senderReceiverQueue); 
		 return senderReceiverQueue; 
	 }
	

	public Integer delete(int id) { 
		senderReceiverQueueRepository.deleteById(id);
		 return id; 
	 }
}
