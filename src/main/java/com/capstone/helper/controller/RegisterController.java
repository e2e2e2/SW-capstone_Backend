package com.capstone.helper.controller;




import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.User;
import com.capstone.helper.security.EncryptionUtils;
import com.capstone.helper.service.RegisterServiece;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.UserVo;
import com.google.gson.JsonObject;


@RestController 
public class RegisterController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RegisterServiece registerService;
	
	@RequestMapping(value="/register/local", method=RequestMethod.POST)
	public User userRegister(@RequestBody UserVo tempuser) {
		
		tempuser.setPassword(EncryptionUtils.encryptMD5(tempuser.getPassword()));
		
		return registerService.register(tempuser);

	}
	
	
	//회원 정보 조회가 아니라 ID중복 체크라서 url param로 처리해도 이상없음
	@RequestMapping(value="/register/checkUserID/{id}", method=RequestMethod.POST)
	public String checkUserID(HttpServletResponse response, @PathVariable("id") String userID, @RequestBody UserVo tempuser) {
		
		JsonObject jsonObject = new JsonObject();
		
		if(userService.countUserID(userID) == 0) {
			response.setStatus( HttpServletResponse.SC_OK);
			jsonObject.addProperty("isIdDuplicate", false);
		}
			
		else {
			response.setStatus( HttpServletResponse.SC_OK);
			jsonObject.addProperty("isIdDuplicate", true);
		}
		
			
		return jsonObject.toString();
	}
	
}
