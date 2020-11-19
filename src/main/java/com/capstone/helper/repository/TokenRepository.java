package com.capstone.helper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.helper.model.Token;

public interface TokenRepository  extends JpaRepository<Token, Integer>{
	Token findByUserId(int userId);
}
