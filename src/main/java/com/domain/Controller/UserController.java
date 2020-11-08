package com.domain.Controller;



import java.util.ArrayList;
import java.util.List;
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

import com.domain.Entity.User;
import com.domain.Repository.UserRepository;
import com.domain.Service.UserService;
import com.domain.TempEntity.tempUser;


	
@RestController 
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/user")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<List<User>> getAllmembers() { 
		List<User> user = userService.findAll(); 
		return new ResponseEntity<List<User>>(user, HttpStatus.OK); 
	}

	
	@RequestMapping("/getUser/{id}")
    @GetMapping(path = "/getUser/{id}")
    public User getOneUser(@PathVariable("id") int id) {
    	System.err.println("add : " + id);
    	Integer select_id = id;
    	return userService.findOne(select_id);
    }
	
	@RequestMapping(value="/putUser")
	@PostMapping()
	public User saveUser(@RequestBody User user) {
    	System.err.println(user.getName() + user.getAddress());
	    return userService.save(user);
	}
	
	@RequestMapping(value="/updateUser/{id}")
	@PatchMapping(path = "/updateUser/{id}")
	public User updateUser(@PathVariable("id") int id, @RequestBody tempUser val) {
		System.err.println("adwdqasaddsdadasdadasdasdasdadasda");
	    return userService.update(id,val);
	}
	
	
	@RequestMapping("/delUser/{id}")
	@DeleteMapping(path = "/delUser/{id}")
    public int deleteUser(@PathVariable("id") int id) {
    	System.err.println("delete : " + id);
    	Integer select_id = id;
    	return userService.delete(select_id);
    }
	
	
/*    
	
	 // 회원 입력 
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
