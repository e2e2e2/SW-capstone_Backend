package com.capstone.helper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.FallEvent;
import com.capstone.helper.model.NonActiveEvent;
import com.capstone.helper.service.FallEventService;
import com.capstone.helper.service.NonActiveEventService;

@RestController
public class EventController {
	
	@Autowired
	FallEventService fallEventService;
	@Autowired
	NonActiveEventService nonActiveEventService;
	
	
	@RequestMapping(value="/fall/event/{id}", method=RequestMethod.GET)
	public FallEvent getFallEventByEventId(@PathVariable("id") int eventId) {
		return fallEventService.findOne(eventId);
	}
	
	@RequestMapping(value="/non-active/event/{id}", method=RequestMethod.GET)
	public NonActiveEvent getNonActiveEventByEventId(@PathVariable("id") int eventId) {
		return nonActiveEventService.findOne(eventId);
	}

}
