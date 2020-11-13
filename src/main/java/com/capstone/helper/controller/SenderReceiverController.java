package com.capstone.helper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.SenderAndReceiver;
import com.capstone.helper.service.SendersAndReceiversService;

@RestController 
public class SenderReceiverController {
	
	@Autowired
	SendersAndReceiversService senderReceiverService;

	@RequestMapping(value = "/user/{id}/putConnection", method=RequestMethod.POST)
    public SenderAndReceiver saveSenderReceiver(@PathVariable("id") int id, @RequestBody SenderAndReceiver senderReceiver ) {
		System.out.println("in saveSenderReceiver");
		return senderReceiverService.save(senderReceiver);
	}
	
}
