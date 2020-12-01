package com.capstone.helper.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.SenderAndReceiver;
import com.capstone.helper.model.User;
import com.capstone.helper.service.SendersAndReceiversService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.ReceiverInfoVo;

@RestController 
public class SenderReceiverController {
	
	@Autowired
	SendersAndReceiversService senderReceiverService;
	
	@Autowired
	UserService userService;

	//*****************************URL 소문자와 - 로 바꾸어야 할 것 같다
	@RequestMapping(value = "/user/makeConnection", method=RequestMethod.POST)
    public SenderAndReceiver saveSenderReceiver(HttpServletRequest request, @RequestBody SenderAndReceiver senderReceiver ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		return senderReceiverService.save(senderReceiver);
	}
	
	
	//sender는 세션, receiver의 전화번호와 연결할 서비스들은 body에 보낸다.
	@RequestMapping(value = "/user/set-recever", method=RequestMethod.POST)
    public SenderAndReceiver saveSenderReceiver(HttpServletRequest request, @RequestBody ReceiverInfoVo Receiver ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int senderID = userService.findIdByuserID(userID);
		SenderAndReceiver sr = new SenderAndReceiver();
		
		int receiverID = userService.findIdByPhoneNum(Receiver.getPhone_number());
		if(Receiver.isFall_down()) 
			sr = senderReceiverService.save(new SenderAndReceiver(0,senderID,receiverID,434));
		
		if(Receiver.isNon_active()) 
			sr = senderReceiverService.save(new SenderAndReceiver(0,senderID,receiverID,435));
		
		if(Receiver.isGps()) {
			sr = senderReceiverService.save(new SenderAndReceiver(0,senderID,receiverID,436));
			sr = senderReceiverService.save(new SenderAndReceiver(0,senderID,receiverID,437));
		}
		return sr;
	}
	
	
	@RequestMapping(value = "/user/sender", method=RequestMethod.GET)
	public ResponseEntity<HashSet<User>> getSenderList(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		List<SenderAndReceiver> senderAndReceiverList = senderReceiverService.findByReceiverId(numID);
		List<User> userList = new ArrayList<User>();
		
		
		for(SenderAndReceiver senderAndReceiver: senderAndReceiverList) {
			userList.add(userService.findOne(senderAndReceiver.getSenderId()));
		}
		HashSet<User> userSet = new HashSet<User>(userList);
		
		
		return  new ResponseEntity<HashSet<User>>(userSet,HttpStatus.OK);
	}
	
}
