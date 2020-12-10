package com.capstone.helper.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.capstone.helper.service.AlarmTypeService;
import com.capstone.helper.service.SendersAndReceiversQueueService;
import com.capstone.helper.service.SendersAndReceiversService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.SenderReceiverInfoVo;
import com.google.gson.JsonObject;
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
	
	@Autowired
	AlarmTypeService alarmTypeService;
	
	//피보호자가 연결요청
	@RequestMapping(value = "user/sender/requestSR", method = RequestMethod.POST)
    public String senderReqSR(HttpServletRequest request, HttpServletResponse response, @RequestBody SenderReceiverInfoVo Receiver ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int reqID = userService.findIdByuserID(userID);
		int targetID = userService.findIdByPhoneNumber(Receiver.getPhone_number());
		
		JsonObject jsonObject = new JsonObject();

		
		if(senderReceiverService.findBySenderIdAndReceiverId(reqID, targetID).size() > 0) {
        	response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
        	jsonObject.addProperty("result","connection already exist");
        	return jsonObject.toString();
		}
		

		try {
			SenderAndReceiverQueue SRQueue1 = new SenderAndReceiverQueue(reqID, targetID);
			senderReceiverQueueService.save(SRQueue1);
			
		}catch(DataAccessException e) {
        	response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
        	jsonObject.addProperty("result","queue already exist");
        	return jsonObject.toString();
		}
	
		
		
		
		
		

        response.setStatus( HttpServletResponse.SC_OK);
    	jsonObject.addProperty("result","success");
    	return jsonObject.toString();
	}
	

	//보호자가 연결요청
	@RequestMapping(value = "user/receiver/requestSR", method = RequestMethod.POST)
    public String receiverReqSR(HttpServletRequest request, HttpServletResponse response, @RequestBody SenderReceiverInfoVo Receiver ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int reqID = userService.findIdByuserID(userID);
		int targetID = userService.findIdByPhoneNumber(Receiver.getPhone_number());
		
		JsonObject jsonObject = new JsonObject();
		System.out.println("receiverId : " + reqID + "\nsenderId : "+targetID);
		System.out.println("기존 수 = "+ senderReceiverService.findBySenderIdAndReceiverId(targetID, reqID).size());

		if(senderReceiverService.findBySenderIdAndReceiverId(targetID, reqID).size() > 0) {
			
        	response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
        	jsonObject.addProperty("result","connection already exist");
        	return jsonObject.toString();
		}


		try {
			SenderAndReceiverQueue SRQueue1 = new SenderAndReceiverQueue(reqID, targetID);
			senderReceiverQueueService.save(SRQueue1);
			
		}catch(DataAccessException e) {

        	response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
        	jsonObject.addProperty("result","queue already exist");
        	return jsonObject.toString();
		}
	
		
		
		

        response.setStatus( HttpServletResponse.SC_OK);
    	jsonObject.addProperty("result","success");
    	return jsonObject.toString();
	}
	
	//요청목록 불러오기
	@RequestMapping(value = "/user/queue", method=RequestMethod.GET)
	public ResponseEntity<HashSet<SRQueueVo>> getReqList(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		List<SenderAndReceiverQueue> senderAndReceiverQList = senderReceiverQueueService.findByTargetId(numID);
		List<SRQueueVo> SRQList = new ArrayList<SRQueueVo>();
		
		
		for(SenderAndReceiverQueue senderAndReceiverQ: senderAndReceiverQList) {
			
			Integer queueId = senderAndReceiverQ.getQueueId();
			
			User reqUser = userService.findOne(senderAndReceiverQ.getReqId());
			
			String name = reqUser.getName();
			Integer auth = reqUser.getAuth();
			String phone_number = reqUser.getPhone_number();
			String address = reqUser.getAddress();
			
			
			SRQList.add(new SRQueueVo(queueId, name,  auth, phone_number, address));
		}
		
		HashSet<SRQueueVo> SRQSet = new HashSet<SRQueueVo>(SRQList);
		
		
		return  new ResponseEntity<HashSet<SRQueueVo>>(SRQSet,HttpStatus.OK);
	}
	
	
	
	//연결수락
	@RequestMapping(value = "/user/receiver/acceptSR", method=RequestMethod.POST)
    public String receiverAcceptSR(HttpServletRequest request, HttpServletResponse response, @RequestBody SRQueueVo SRQueue ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int targetID = userService.findIdByuserID(userID);
		JsonObject jsonObject = new JsonObject();
		
		SenderAndReceiverQueue srQ = senderReceiverQueueService.findById(SRQueue.getQueueId());
		
		if(srQ == null) {        	
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
    		jsonObject.addProperty("error","there's no such request");
    		return jsonObject.toString();
		}
		if(srQ.getTargetId() != targetID) {        	
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
    		jsonObject.addProperty("error","wrong target id");
    		return jsonObject.toString();
		}
		
		List<Integer> alarmId = alarmTypeService.findAllId();

		alarmId.forEach((id)->{

			
			Integer senderId = srQ.getReqId();
			Integer receiverId = srQ.getTargetId();
			Integer alarmTypeId = id;
			SenderAndReceiver sr = new SenderAndReceiver(senderId, receiverId, alarmTypeId);
			senderReceiverService.save(sr);
		});
		
		

		senderReceiverQueueService.delete(SRQueue.getQueueId());


        response.setStatus( HttpServletResponse.SC_OK);
    	jsonObject.addProperty("result","success");
    	return jsonObject.toString();
	}


	
	//연결수락
	@RequestMapping(value = "/user/sender/acceptSR", method=RequestMethod.POST)
    public String senderAcceptSR(HttpServletRequest request, HttpServletResponse response, @RequestBody SRQueueVo SRQueue ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int targetID = userService.findIdByuserID(userID);
		JsonObject jsonObject = new JsonObject();
		
		SenderAndReceiverQueue srQ =  senderReceiverQueueService.findById(SRQueue.getQueueId());
		
		
		if(srQ == null) {        	
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
    		jsonObject.addProperty("error","there's no such request");
    		return jsonObject.toString();
		}
		if(srQ.getTargetId() != targetID) {        	
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
    		jsonObject.addProperty("error","wrong target id");
    		return jsonObject.toString();
		}
		
		List<Integer> alarmId = alarmTypeService.findAllId();

		alarmId.forEach((id)->{

			
		
		
		Integer senderId = srQ.getTargetId();
		Integer receiverId = srQ.getReqId();
		Integer alarmTypeId = id;
		SenderAndReceiver sr = new SenderAndReceiver(senderId, receiverId, alarmTypeId);
		senderReceiverService.save(sr);
	});
	
		senderReceiverQueueService.delete(SRQueue.getQueueId());

        response.setStatus( HttpServletResponse.SC_OK);
    	jsonObject.addProperty("result","success");
    	return jsonObject.toString();
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
