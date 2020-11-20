package com.capstone.helper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.helper.model.Token;

public interface TokenRepository  extends JpaRepository<Token, Integer>{
	List<Token> findByUserId(int userId);
}
