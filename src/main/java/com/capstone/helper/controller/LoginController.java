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
    public String loginProcess(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVo tempuser) {
		String userID = tempuser.getUserID();
		String password = tempuser.getPassword();
		HttpSession session = request.getSession();
		
        
		if(loginService.loginCheck(userID, password)){
            session.setAttribute("userID",userID);
            response.setStatus( HttpServletResponse.SC_OK);
            
            return "pass";
        }
        
        else{

        	response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
        	return "fail";
        }
    }
    
    //테스트용 로그인
    /*
    @RequestMapping(value="/loginM", method=RequestMethod.POST)
    public String loginTEST(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVo tempuser) {
		String userID = "aaaa";
		String password = "1q2w3e4r";
		HttpSession session = request.getSession();
		
        
		if(loginService.loginCheck(userID, password)){
            session.setAttribute("userID",userID);
            response.setStatus( HttpServletResponse.SC_OK);
            
            return "pass";
        }
        
        else{

        	response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
        	return "fail";
        }
    }
    */
    
    @RequestMapping(value="/logout", method=RequestMethod.POST)
    public String logoutProcess(HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();
                            
        session.invalidate();
        response.setStatus( HttpServletResponse.SC_OK);
        
        return "logout";
    }
    
}
