package com.capstone.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.SenderAndReceiver;
import com.capstone.helper.repository.SenderAndReceiverQueueRepository;
import com.capstone.helper.repository.SenderAndReceiverRepository;

@Service("senderReceiverService")
public class SendersAndReceiversService {
	@Autowired
	private SenderAndReceiverRepository senderReceiverRepository;
	
	public List<SenderAndReceiver> findByReceiverId(int receiverId) {
		List<SenderAndReceiver> senderReceivers = new ArrayList<>();
		senderReceiverRepository.findByReceiverId(receiverId).forEach(e -> senderReceivers.add(e));
		return senderReceivers;
	}
	
	public List<SenderAndReceiver> findBySenderIdAndAlarmTypeId(int senderId,int alarmTypeId) {
		List<SenderAndReceiver> senderReceivers = new ArrayList<>();
		senderReceiverRepository.findBySenderIdAndAlarmTypeId(senderId, alarmTypeId).forEach(e -> senderReceivers.add(e));
		return senderReceivers;
	}
	
	public SenderAndReceiver save(SenderAndReceiver senderReceiver) { 
		senderReceiverRepository.save(senderReceiver); 
		 return senderReceiver; 
	 }
	
}
