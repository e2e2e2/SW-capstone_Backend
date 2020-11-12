package com.capstone.helper.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		return "Hello World!";
	}
	
}
