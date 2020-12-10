package com.capstone.helper.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.capstone.helper.model.ReceiverEnvironment;
import com.capstone.helper.model.Token;
import com.capstone.helper.model.User;
import com.capstone.helper.repository.TokenRepository;
import com.capstone.helper.repository.UserRepository;
import com.capstone.helper.service.ReceiverEnvironmentService;
import com.capstone.helper.service.TokenService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.TokenVo;
import com.capstone.helper.vo.UserVo;
import com.google.gson.JsonObject;


	
@RestController 
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReceiverEnvironmentService receiverEnvironmentService;
	
	@Autowired
	private TokenService tokenService;
	
	//기능 넣지 말것---for master
	@RequestMapping(value="/user-list", method=RequestMethod.GET)
	public ResponseEntity<List<User>> getAllmembers() { 
		List<User> user = userService.findAll(); 
		return new ResponseEntity<List<User>>(user, HttpStatus.OK); 
	}

	
	@RequestMapping(value="/user", method=RequestMethod.GET)
    public User getOneUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
    	return userService.findOne(numID);
    }
	
	
	//RegisterController의 userRegister를 이용할 것---for master
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public User saveUser(@RequestBody User user) {
	    return userService.save(user);
	}
	
	
	//json에는 number ID를 제외한 정보만 담을 것
	@RequestMapping(value="/user", method=RequestMethod.PATCH)
	public User updateUser(HttpServletRequest request, @RequestBody UserVo val) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
	    return userService.update(val, numID);
	}
	
	
	@RequestMapping(value="/user", method=RequestMethod.DELETE)
    public int deleteUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		Integer select_id = userService.findIdByuserID(userID);
    	return userService.delete(select_id);
    }
	
	
	@RequestMapping(value="/user/token", method=RequestMethod.PATCH)
    public int updateToken(HttpServletRequest request, @RequestBody TokenVo tokenVo) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
    	return 0;
    }
	
	@RequestMapping(value="/user/token", method=RequestMethod.DELETE)
    public String deleteToken(HttpServletRequest request, @RequestBody TokenVo tokenVo) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		int numID = userService.findIdByuserID(userID);
		
		JsonObject jsonObject = new JsonObject();
    	
		
		List<Token> tokenList = tokenService.findByUserId(numID);
		for (Token tempToken : tokenList) {
			if(tempToken.getToken().equals( tokenVo.getToken())) {
				//tokenService. delete
				tokenService.delete(tempToken.getId());
				jsonObject.addProperty("result","success");
				return jsonObject.toString();
			}
		}
		jsonObject.addProperty("result","fail");
    	return jsonObject.toString();
    }
	
	@RequestMapping(value="/user/has-app/{is-true}", method=RequestMethod.POST)
    public Token registerAppWithToken(HttpServletRequest request, @PathVariable("is-true") boolean isTrue, @RequestBody TokenVo tokenVo) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		Integer numID = userService.findIdByuserID(userID);
		
		ReceiverEnvironment receiverEnvironment = receiverEnvironmentService.findByUserId(numID);
		if(receiverEnvironment == null) {
			receiverEnvironment = createDefaultReceiverEnvironment(numID);
		}
		receiverEnvironment.setHasApp(isTrue);
		
		receiverEnvironmentService.save(receiverEnvironment);
		
		List<Token> tokenList = tokenService.findByUserId(numID);
		
		for (Token tempToken : tokenList) {
			if(tempToken.getToken().equals( tokenVo.getToken())) {
				return tempToken;
			}
		}
		
		Token newToken = new Token(numID, tokenVo.getToken());
		
    	return tokenService.save(newToken);
    }
	
	
	@RequestMapping(value="/user/has-web/{is-true}", method=RequestMethod.POST)
    public ReceiverEnvironment registerWeb(HttpServletRequest request, @PathVariable("is-true") boolean isTrue) {
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		Integer numID = userService.findIdByuserID(userID);
		
		ReceiverEnvironment receiverEnvironment = receiverEnvironmentService.findByUserId(numID);
		if(receiverEnvironment == null) {
			receiverEnvironment = createDefaultReceiverEnvironment(numID);
		}
		receiverEnvironment.setHasWeb(isTrue);
		return receiverEnvironmentService.save(receiverEnvironment);
    }
	
	public ReceiverEnvironment createDefaultReceiverEnvironment(int userId) {
		ReceiverEnvironment receiverEnvironment = new ReceiverEnvironment(userId, true, true);
		return receiverEnvironment;
	}
	
/*    
	
	@PostMapping 
	public ResponseEntity<User> save(User member) { 
		return new ResponseEntity<User>(userService.save(member), HttpStatus.OK); 
	} 

	@RequestMapping(value="/saveMember", method = RequestMethod.GET) 
	public ResponseEntity<User> save(HttpServletRequest req, User member){ 
		return new ResponseEntity<User>(userService.save(member), HttpStatus.OK); 
	}
	*/
}
