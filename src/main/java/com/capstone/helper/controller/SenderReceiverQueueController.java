package com.capstone.helper.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.SenderAndReceiver;
import com.capstone.helper.model.SenderAndReceiverQueue;
import com.capstone.helper.model.User;
import com.capstone.helper.repository.SenderAndReceiverRepository;
import com.capstone.helper.service.SendersAndReceiversQueueService;
import com.capstone.helper.service.SendersAndReceiversService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.SenderReceiverInfoVo;
import com.capstone.helper.vo.SRQueueVo;


@RestController 
public class SenderReceiverQueueController {
	
	@Autowired
	SendersAndReceiversQueueService senderReceiverQueueService;
	
	@Autowired
	SenderAndReceiverRepository senderReceiverRepository;
	
	@Autowired
	SendersAndReceiversService senderReceiverService;
	
	@Autowired
	UserService userService;

	//연결요청
	@RequestMapping(value = "/user/queue", method = RequestMethod.POST)
    public SenderAndReceiverQueue saveSenderReceiver(HttpServletRequest request, @RequestBody SenderReceiverInfoVo Receiver ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int reqID = userService.findIdByuserID(userID);
		
		
		int targetID = userService.findIdByPhoneNumber(Receiver.getPhone_number());


		try {
			if(Receiver.isFall_down()){
				SenderAndReceiverQueue SRQueue1 = new SenderAndReceiverQueue(reqID, targetID,434);
				senderReceiverQueueService.save(SRQueue1);
			
			}
		}catch(DataAccessException e) {}
	
		
		finally {
			try {

				if(Receiver.isNon_active())	{
					SenderAndReceiverQueue SRQueue2 = new SenderAndReceiverQueue(reqID, targetID,435);
					senderReceiverQueueService.save(SRQueue2);
					}

			}catch(DataAccessException e) {}
		
			
			finally {
				
				if(Receiver.isGps()){


					try {
						SenderAndReceiverQueue SRQueue3 = new SenderAndReceiverQueue(reqID, targetID,436);
						senderReceiverQueueService.save(SRQueue3);
					}catch(DataAccessException e) {}

					finally {

						try {
						SenderAndReceiverQueue SRQueue4 = new SenderAndReceiverQueue(reqID, targetID,437);
						senderReceiverQueueService.save(SRQueue4);
						}catch(DataAccessException e) {}
					}
					
				}
			
			}
			
		}
		
		
		
		SenderAndReceiverQueue SRQueue = new SenderAndReceiverQueue();
		SRQueue = new SenderAndReceiverQueue();
		SRQueue.setTargetId(targetID);
		SRQueue.setReqId(reqID);
		
		
		

		return SRQueue;
	}
	
	//요청목록 불러오기
	@RequestMapping(value = "/user/queue", method=RequestMethod.GET)
	public ResponseEntity<HashSet<SRQueueVo>> getReqList(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		List<SenderAndReceiverQueue> senderAndReceiverQList = senderReceiverQueueService.findByReceiverId(numID);
		List<SRQueueVo> SRQList = new ArrayList<SRQueueVo>();
		
		
		for(SenderAndReceiverQueue senderAndReceiverQ: senderAndReceiverQList) {
			
			Integer queueId = senderAndReceiverQ.getQueueId();
			
			User reqUser = userService.findOne(senderAndReceiverQ.getReqId());
			
			String name = reqUser.getName();
			Integer auth = reqUser.getAuth();
			String phone_number = reqUser.getPhone_number();
			String address = reqUser.getAddress();
			Integer alarmType =senderAndReceiverQ.getAlarmTypeId();
			
			
			SRQList.add(new SRQueueVo(queueId, name,  auth, phone_number, address, alarmType));
		}
		
		HashSet<SRQueueVo> SRQSet = new HashSet<SRQueueVo>(SRQList);
		
		
		return  new ResponseEntity<HashSet<SRQueueVo>>(SRQSet,HttpStatus.OK);
	}
	
	
	
	//연결수락
	@RequestMapping(value = "/user/receiver/acceptSR", method=RequestMethod.POST)
    public SenderAndReceiver receiverAcceptSR(HttpServletRequest request, @RequestBody SRQueueVo SRQueue ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int targetID = userService.findIdByuserID(userID);
		
		SenderAndReceiverQueue srQ =  senderReceiverQueueService.findById(SRQueue.getQueueId());
		
		if(srQ == null) return null;
		
		Integer senderId = srQ.getReqId();
		Integer receiverId = srQ.getTargetId();
		Integer alarmTypeId = srQ.getAlarmTypeId();
		
		
		SenderAndReceiver sr = new SenderAndReceiver(senderId, receiverId, alarmTypeId);

		senderReceiverService.save(sr);
		senderReceiverQueueService.delete(SRQueue.getQueueId());
		return sr;
	}


	
	//연결수락
	@RequestMapping(value = "/user/sender/acceptSR", method=RequestMethod.POST)
    public SenderAndReceiver senderAcceptSR(HttpServletRequest request, @RequestBody SRQueueVo SRQueue ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int targetID = userService.findIdByuserID(userID);
		
		SenderAndReceiverQueue srQ =  senderReceiverQueueService.findById(SRQueue.getQueueId());
		
		if(srQ == null) return null;
		
		Integer senderId = srQ.getTargetId();
		Integer receiverId = srQ.getReqId();
		Integer alarmTypeId = srQ.getAlarmTypeId();
		
		
		SenderAndReceiver sr = new SenderAndReceiver(senderId, receiverId, alarmTypeId);
		
		senderReceiverQueueService.delete(SRQueue.getQueueId());
		senderReceiverService.save(sr);
		return sr;
	}
	
	
	//연결거부
	@RequestMapping(value = "/user/rejectSR", method=RequestMethod.POST)
    public Integer rejectSRQ(HttpServletRequest request, @RequestBody SRQueueVo SRQueue ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int targetID = userService.findIdByuserID(userID);
		
		return senderReceiverQueueService.delete(SRQueue.getQueueId());
	}
}
