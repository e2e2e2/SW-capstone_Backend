package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.helper.model.Token;
import com.capstone.helper.repository.TokenRepository;

public class TokenService {

	@Autowired
	TokenRepository tokenRepository;
	
	public Token findByUserId(int userId) {
		Token token = tokenRepository.findByUserId(userId);
		return token;
	}
}
