package com.capstone.helper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.capstone.helper.model.SendersAndReceivers;

public interface SenderReceiverRepository extends JpaRepository<SendersAndReceivers, Integer>{
	List<SendersAndReceivers> findBySenderId(int senderid);
}

