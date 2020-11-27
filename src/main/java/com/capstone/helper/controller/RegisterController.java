package com.capstone.helper.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.User;
import com.capstone.helper.service.RegisterServiece;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.UserVo;


@RestController 
public class RegisterController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RegisterServiece registerService;
	
	@RequestMapping(value="/api/register/local", method=RequestMethod.POST)
	public User userRegister(@RequestBody UserVo tempuser) {
		
		tempuser.setPassword(EncryptionUtils.encryptMD5(tempuser.getPassword()));
		
		return registerService.register(tempuser);

	}
	
	@RequestMapping(value="/api/checkUserID/{id}", method=RequestMethod.POST)
	public boolean checkUserID(@PathVariable("id") String userID, @RequestBody UserVo tempuser) {
		
		if(userService.countUserID(userID) == 0)
			return true;
		else
			return false;

	}
	
}
