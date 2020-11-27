package com.capstone.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.controller.EncryptionUtils;
import com.capstone.helper.model.User;
import com.capstone.helper.repository.UserRepository;


@Service("loginService")
public class LoginService {
	@Autowired
	private UserRepository userRepository;

	public boolean loginCheck(String userID, String pw) {
		List<User> user = new ArrayList<>();
		userRepository.findByUserID(userID).forEach(e -> user.add(e));
		
		System.err.println("size = " + user.size());
		
		if(user.size() <1) 
			return false;
		
		return user.get(0).matchPassword(EncryptionUtils.encryptMD5(pw));
		
	}
}
