package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.Token;
import com.capstone.helper.repository.TokenRepository;

@Service("tokenService")
public class TokenService {

	@Autowired
	TokenRepository tokenRepository;
	
	public Token findByUserId(int userId) {
		return tokenRepository.findByUserId(userId);
	}
	
	public Token save(Token token) {
		return tokenRepository.save(token); 
	}
}
