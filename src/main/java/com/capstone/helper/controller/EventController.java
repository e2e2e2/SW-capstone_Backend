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
	
	//피보호자는 항상 세션연결이 유지되어야함
	@RequestMapping(value="/fall/event", method=RequestMethod.GET)
	public FallEvent getFallEventByEventId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		return fallEventService.findOne(numID);
	}

	//피보호자는 항상 세션연결이 유지되어야함
	@RequestMapping(value="/non-active/event", method=RequestMethod.GET)
	public NonActiveEvent getNonActiveEventByEventId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		return nonActiveEventService.findOne(numID);
	}

	
}
