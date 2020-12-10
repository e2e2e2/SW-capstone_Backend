package com.capstone.helper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.capstone.helper.model.SenderAndReceiverQueue;

public interface SenderAndReceiverQueueRepository extends JpaRepository<SenderAndReceiverQueue, Integer>{
	
	List<SenderAndReceiverQueue> findByTargetId(int targetId);
	SenderAndReceiverQueue deleteById(int id);
}

