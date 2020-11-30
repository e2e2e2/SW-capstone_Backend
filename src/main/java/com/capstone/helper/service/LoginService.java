package com.capstone.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.User;
import com.capstone.helper.repository.UserRepository;
import com.capstone.helper.security.EncryptionUtils;


@Service("loginService")
public class LoginService {
	@Autowired
	private UserRepository userRepository;

	public Integer loginCheck(String userID, String pw) {
		List<User> user = new ArrayList<>();
		userRepository.findByUserID(userID).forEach(e -> user.add(e));
		
		System.err.println("size = " + user.size());
		
		if(user.size() <1) 
			return -200;//아이디가 틀릴경우
		if(user.get(0).matchPassword(EncryptionUtils.encryptMD5(pw)))
			return 1;//맞을경우
		else
			return -100;//비밀번호가 틀릴경우
		
	}
}
