package com.capstone.helper.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
import com.capstone.helper.repository.UserRepository;
import com.capstone.helper.service.ReceiverEnvironmentService;
import com.capstone.helper.service.TokenService;
import com.capstone.helper.service.UserService;
import com.capstone.helper.vo.TokenVo;
import com.capstone.helper.vo.UserVo;


	
@RestController 
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReceiverEnvironmentService receiverEnvironmentService;
	
	@Autowired
	private TokenService tokenService;
	
	
	@RequestMapping(value="/user-list", method=RequestMethod.GET)
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<List<User>> getAllmembers() { 
		List<User> user = userService.findAll(); 
		return new ResponseEntity<List<User>>(user, HttpStatus.OK); 
	}

	
	@RequestMapping(value="/user/{id}", method=RequestMethod.GET)
    @GetMapping(path = "/user/{id}")
    public User getOneUser(@PathVariable("id") int id) {
		Integer select_id = id;
    	System.err.println("add : " + select_id);
    	return userService.findOne(select_id);
    }
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	@PostMapping()
	public User saveUser(@RequestBody User user) {
    	System.err.println(user.getName() + user.getAddress());
	    return userService.save(user);
	}
	
	@RequestMapping(value="/user", method=RequestMethod.PATCH)
	@PatchMapping(path = "/user")
	public User updateUser(@RequestBody UserVo val) {
	    return userService.update(val);
	}
	
	
	@RequestMapping(value="/user", method=RequestMethod.DELETE)
	@DeleteMapping(path = "/user")
    public int deleteUser(@RequestBody UserVo val) {
    	Integer select_id = val.getId();
    	System.err.println("delete : " + select_id);
    	return userService.delete(select_id);
    }
	
	
	@RequestMapping(value="/user/{id}/token", method=RequestMethod.PATCH)
    public int updateToken(@RequestBody TokenVo tokenVo) {
    	return 0;
    }
	
	@RequestMapping(value="/user/{id}/has-app/{is-true}", method=RequestMethod.POST)
    public Token registerAppWithToken(@PathVariable("id") int id, @PathVariable("is-true") boolean isTrue, @RequestBody TokenVo tokenVo) {
		
		ReceiverEnvironment receiverEnvironment = receiverEnvironmentService.findByUserId(id);
		if(receiverEnvironment == null) {
			receiverEnvironment = createDefaultReceiverEnvironment(id);
		}
		receiverEnvironment.setHasApp(true);
		
		receiverEnvironmentService.save(receiverEnvironment);
		
		
		Token newToken = new Token(id, tokenVo.getToken());
		
    	return tokenService.save(newToken);
    }
	
	
	@RequestMapping(value="/user/{id}/has-web/{is-true}", method=RequestMethod.POST)
    public ReceiverEnvironment registerWeb(@PathVariable("id") int id, @PathVariable("is-true") boolean isTrue) {
		ReceiverEnvironment receiverEnvironment = receiverEnvironmentService.findByUserId(id);
		if(receiverEnvironment == null) {
			receiverEnvironment = createDefaultReceiverEnvironment(id);
		}
		receiverEnvironment.setHasWeb(true);
		return receiverEnvironmentService.save(receiverEnvironment);
    }
	
	public ReceiverEnvironment createDefaultReceiverEnvironment(int userId) {
		ReceiverEnvironment receiverEnvironment = new ReceiverEnvironment(userId, false, false);
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
