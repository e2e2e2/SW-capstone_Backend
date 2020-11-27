package com.capstone.helper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.helper.model.Home;

public interface HomeRepository extends JpaRepository<Home, Integer>{
	Home findByUserId(int userId);
}
