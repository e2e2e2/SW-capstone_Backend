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
import com.capstone.helper.vo.SenderReceiverInfoVo;
import com.google.gson.JsonObject;

@RestController 
public class SenderReceiverController {
	
	@Autowired
	SendersAndReceiversService senderReceiverService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/sender/deleteSR", method=RequestMethod.POST)
    public String senderSeleteSR(HttpServletRequest request, @RequestBody SenderAndReceiver senderReceiver ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int senderID = userService.findIdByuserID(userID);
		int receiverID = senderReceiver.getReceiverId();
		senderReceiverService.deleteBySenderIdAndReceiverId(senderID, receiverID);
		return "success";
	}
	

	@RequestMapping(value = "/user/receiver/deleteSR", method=RequestMethod.POST)
    public String receiverDeleteSR(HttpServletRequest request, @RequestBody SenderAndReceiver senderReceiver ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int receiverID = userService.findIdByuserID(userID);
		int senderID = senderReceiver.getSenderId();
		senderReceiverService.deleteBySenderIdAndReceiverId(senderID, receiverID);
		
		return "success";
	}
	
	
	
	@RequestMapping(value = "/user/make-connection", method=RequestMethod.POST)
    public SenderAndReceiver saveSenderReceiver(HttpServletRequest request, @RequestBody SenderAndReceiver senderReceiver ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		return senderReceiverService.save(senderReceiver);
	}
	
	
	//sender는 세션, receiver의 전화번호와 연결할 서비스들은 body에 보낸다.
	@RequestMapping(value = "/user/set-receiver", method=RequestMethod.POST)
    public String setReceiverByPhoneNumber(HttpServletRequest request, @RequestBody SenderReceiverInfoVo Receiver ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int senderID = userService.findIdByuserID(userID);
		SenderAndReceiver sr = new SenderAndReceiver();
		
		System.out.println("id is " + userID);
		int receiverID = userService.findIdByPhoneNumber(Receiver.getPhone_number());
		if(Receiver.isFall_down()) 
			sr = senderReceiverService.save(new SenderAndReceiver(senderID,receiverID,434));
		
		if(Receiver.isNon_active()) 
			sr = senderReceiverService.save(new SenderAndReceiver(senderID,receiverID,435));
		
		if(Receiver.isGps()) {
			sr = senderReceiverService.save(new SenderAndReceiver(senderID,receiverID,436));
			sr = senderReceiverService.save(new SenderAndReceiver(senderID,receiverID,437));
		}
		

		JsonObject jsonObject = new JsonObject();

    	jsonObject.addProperty("name",userService.findNameByID(receiverID));
        return jsonObject.toString();
	}
	
	
	//sender는 세션, receiver의 전화번호와 연결할 서비스들은 body에 보낸다.
	@RequestMapping(value = "/user/set-sender", method=RequestMethod.POST)
    public SenderAndReceiver setSenderByPhoneNumber(HttpServletRequest request, @RequestBody SenderReceiverInfoVo Sender ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int receiverID = userService.findIdByuserID(userID);
		SenderAndReceiver sr = new SenderAndReceiver();
		
		System.out.println("id is " + userID);
		int senderID = userService.findIdByPhoneNumber(Sender.getPhone_number());
		if(Sender.isFall_down()) 
			sr = senderReceiverService.save(new SenderAndReceiver(senderID,receiverID,434));
		
		if(Sender.isNon_active()) 
			sr = senderReceiverService.save(new SenderAndReceiver(senderID,receiverID,435));
		
		if(Sender.isGps()) {
			sr = senderReceiverService.save(new SenderAndReceiver(senderID,receiverID,436));
			sr = senderReceiverService.save(new SenderAndReceiver(senderID,receiverID,437));
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
	
	
	@RequestMapping(value = "/user/receiver", method=RequestMethod.GET)
	public ResponseEntity<HashSet<User>> getReveiverList(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		List<SenderAndReceiver> senderAndReceiverList = senderReceiverService.findBySenderIdAndAlarmTypeId(numID, 434);
		List<User> userList = new ArrayList<User>();
		
		
		for(SenderAndReceiver senderAndReceiver: senderAndReceiverList) {
			userList.add(userService.findOne(senderAndReceiver.getReceiverId()));
		}
		HashSet<User> userSet = new HashSet<User>(userList);
		
		
		return  new ResponseEntity<HashSet<User>>(userSet,HttpStatus.OK);
	}
	
	
}
