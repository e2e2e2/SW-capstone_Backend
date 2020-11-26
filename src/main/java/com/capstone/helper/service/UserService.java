package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList; 
import java.util.List; 
import java.util.Optional;
import java.util.Objects;

import com.capstone.helper.model.User;
import com.capstone.helper.repository.UserRepository;
import com.capstone.helper.vo.UserVo;
	
@Service("userService")
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
    
	public List<User> findAll() { 
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(e -> users.add(e));
		return users;
	}
	 
	
	public User findOne(Integer id) {
		User user = userRepository.getOne(id);
		return user;
	}
	
	 public User save(User user) { 
		 return userRepository.save(user); 
	 }
	 
	 public User register(UserVo tempuser) { 
		 User user = new User();
		 
		 user.setAddress(tempuser.getAddress());
		 user.setAuth(tempuser.getAuth());
		 user.setName(tempuser.getName());
		 user.setPassword(tempuser.getPassword());
		 user.setPhone_number(tempuser.getPhone_number());
		 
		 return userRepository.save(user); 
	 }
	
	 public int delete(int id) { 
		 userRepository.deleteById(id); 
		 return id;
	 }
	 
	 public User update(int id, UserVo val) { 
		 User user = userRepository.getOne(id);
		 
		 
		 if(val.getAddress() != null)
			 user.setAddress(val.getAddress());
		 if(val.getAuth() != -1)
			 user.setAuth(val.getAuth());
		 if(val.getName() != null)
			 user.setName(val.getName());
		 if(val.getPhone_number() != null)
			 user.setPhone_number(val.getPhone_number());
		 if(val.getPassword() != null)
			 user.setPassword(val.getPassword());
		 
		 System.err.println("name : " + user.getName());
		 userRepository.save(user);
		 return user; 
	 }
	/* 
	 public Optional<User> findById(Long id) { 
		 Optional<User> user = userRepository.findById(id);
		 return user;
	 }
	 
	 

	*/
		 
		 
}
