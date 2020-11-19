package com.capstone.helper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.helper.model.ReceiverEnvironment;

public interface ReceiverEnvironmentRepository extends JpaRepository<ReceiverEnvironment, Integer> {
	ReceiverEnvironment findByUserId(int userId);
}
