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

@RestController 
public class SenderReceiverController {
	
	@Autowired
	SendersAndReceiversService senderReceiverService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/makeConnection", method=RequestMethod.POST)
    public SenderAndReceiver saveSenderReceiver(HttpServletRequest request, @RequestBody SenderAndReceiver senderReceiver ) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		return senderReceiverService.save(senderReceiver);
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
