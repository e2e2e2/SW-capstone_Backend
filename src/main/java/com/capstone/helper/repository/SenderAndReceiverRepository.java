package com.capstone.helper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.capstone.helper.model.SenderAndReceiver;

public interface SenderAndReceiverRepository extends JpaRepository<SenderAndReceiver, Integer>{
	List<SenderAndReceiver> findByReceiverId(int receiverId);
	List<SenderAndReceiver> findBySenderIdAndAlarmTypeId(int senderid,int alarmTypeId);
}

