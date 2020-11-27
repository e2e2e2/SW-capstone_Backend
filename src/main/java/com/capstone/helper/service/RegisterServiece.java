package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.User;
import com.capstone.helper.repository.UserRepository;
import com.capstone.helper.vo.UserVo;

@Service("registerService")
public class RegisterServiece {
	@Autowired
	private UserRepository userRepository;

	public User register(UserVo tempuser) {
		User user = new User();
		
		user.setUserID(tempuser.getUserID());
		user.setAddress(tempuser.getAddress());
		user.setAuth(tempuser.getAuth());
		user.setName(tempuser.getName());
		user.setPassword(tempuser.getPassword());
		user.setPhone_number(tempuser.getPhone_number());
		 
		return userRepository.save(user); 
	}
}
