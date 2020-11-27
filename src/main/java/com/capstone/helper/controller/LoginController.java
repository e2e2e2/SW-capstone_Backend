package com.capstone.helper.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.helper.model.User;
import com.capstone.helper.service.LoginService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.UserVo;


@RestController 
public class LoginController {
	
	@Autowired
    LoginService loginService;

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginProcess(HttpServletRequest request, @RequestBody UserVo tempuser) {
		String userID = tempuser.getUserID();
		String password = tempuser.getPassword();
		HttpSession session = request.getSession();
		
        
		if(loginService.loginCheck(userID, password)){
			System.err.println("pass");
            session.setAttribute("loginCheck",true);
            session.setAttribute("userID",userID);
//          return ResponseEntity.status(HttpStatus.OK);
            return "pass";
        }
        
        else{
        	System.err.println("fail");
//        	return ResponseEntity.status(HttpStatus.BAD_REQUEST);
        	return "fail";
        }
    }
    
    @RequestMapping(value="/logout", method=RequestMethod.POST)
    public String logoutProcess(HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();
                            
        session.invalidate();
        
        response.setStatus( HttpServletResponse.SC_OK);
        
        return "logout";
    }
    
    @RequestMapping(value="/needLogin", method=RequestMethod.POST)
    public String needLoginPage(HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();
    	
        if(session.getAttribute("loginCheck")!=null){
        	response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
        	return "already login";
        }
        else{
        	response.setStatus( HttpServletResponse.SC_OK);
        	return "need login";
        }
    }
    
}
