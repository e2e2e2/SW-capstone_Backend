package com.capstone.helper.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

	@RequestMapping(value = "/user/{id}/connection", method=RequestMethod.POST)
    public SenderAndReceiver saveSenderReceiver(@PathVariable("id") int id, @RequestBody SenderAndReceiver senderReceiver ) {
		return senderReceiverService.save(senderReceiver);
	}
	
	@RequestMapping(value = "/user/{id}/sender", method=RequestMethod.GET)
	public ResponseEntity<HashSet<User>> getSenderList(@PathVariable("id") int id){
		List<SenderAndReceiver> senderAndReceiverList = senderReceiverService.findByReceiverId(id);
		List<User> userList = new ArrayList<User>();
		
		
		for(SenderAndReceiver senderAndReceiver: senderAndReceiverList) {
			userList.add(userService.findOne(senderAndReceiver.getSenderId()));
		}
		HashSet<User> userSet = new HashSet<User>(userList);
		
		
		return  new ResponseEntity<HashSet<User>>(userSet,HttpStatus.OK);
	}
	
}
