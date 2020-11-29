package com.capstone.helper.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.FallEvent;
import com.capstone.helper.model.NonActiveEvent;
import com.capstone.helper.service.FallEventService;
import com.capstone.helper.service.NonActiveEventService;
import com.capstone.helper.service.UserService;

@RestController
public class EventController {
	
	@Autowired
	FallEventService fallEventService;
	@Autowired
	NonActiveEventService nonActiveEventService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/fall/event/{event_id}", method=RequestMethod.GET)
	public FallEvent getFallEventByEventId(@PathVariable("event_id") int eventId) {
		return fallEventService.findOne(eventId);
	}
	
	@RequestMapping(value="/non-active/event/{event_id}", method=RequestMethod.GET)
	public NonActiveEvent getNonActiveEventByEventId(@PathVariable("event_id") int eventId) {
		return nonActiveEventService.findOne(eventId);
	}

	
}
