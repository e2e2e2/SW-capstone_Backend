package com.capstone.helper.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.capstone.helper.service.UserService;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
								Object handler) throws Exception {
		//handler method인가 체크
		if( handler instanceof HandlerMethod == false ) {
			return true;
		}
		
		//있으면 세션 유무 체크
		HttpSession session = request.getSession();
		if( session == null ) {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		
		String userID = (String)session.getAttribute("userID");
		
		System.out.println(session);
		System.out.println(userID);
		
		if(userID == null) {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		
		if(userService.countUserID(userID).equals(0)) {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		
		return true;
	
	}
}
