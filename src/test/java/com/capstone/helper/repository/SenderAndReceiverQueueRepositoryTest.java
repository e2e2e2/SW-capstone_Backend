package com.capstone.helper.repository;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.helper.model.AlarmType;
import com.capstone.helper.model.SenderAndReceiverQueue;
import com.capstone.helper.model.User;

@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class SenderAndReceiverQueueRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AlarmTypeRepository alarmTypeRepository;
	
	@Autowired
	SenderAndReceiverQueueRepository senderAndReceiverQueueRepository; 
	
	User expectedSender;
	User expectedReceiver;
	SenderAndReceiverQueue expectedSenderAndReceiverQueue;
	AlarmType expectedAlarmType;
	
	@BeforeEach
	public void setUp() {
		expectedSender = new User("a","name","pw",0,"111-2222-3333","address");
		userRepository.save(expectedSender);
		Assertions.assertNotEquals(null,userRepository.getOne(expectedSender.getId()));
		
		expectedReceiver = new User("b","name1","pw1",0,"111-2222-4444","address1");
		userRepository.save(expectedReceiver);
		Assertions.assertNotEquals(null,userRepository.getOne(expectedReceiver.getId()));
		
		expectedAlarmType = new AlarmType();
		expectedAlarmType.setAlarmName("fallalarm");
		alarmTypeRepository.save(expectedAlarmType);
		Assertions.assertNotEquals(null,alarmTypeRepository.getOne(expectedAlarmType.getId()));
		
		expectedSenderAndReceiverQueue = new SenderAndReceiverQueue(0, expectedSender.getId(),
				expectedReceiver.getId(),expectedAlarmType.getId());
		senderAndReceiverQueueRepository.save(expectedSenderAndReceiverQueue);
		Assertions.assertNotEquals(null,senderAndReceiverQueueRepository.getOne(expectedSenderAndReceiverQueue.getId()));
	}

	@Test
	public void shouldFindSenderAndReceiverQueueByTargetId() {
		
		SenderAndReceiverQueue actualSenderAndReceiverQueue = senderAndReceiverQueueRepository.findByTargetId(expectedReceiver.getId()).get(0);
		

		Assertions.assertEquals(expectedSenderAndReceiverQueue, actualSenderAndReceiverQueue );
		
		
	}	
	
	@Test
	public void shouldFindSenderAndReceiverQueueByReqIdAndAlarmTypeId() {
		
		SenderAndReceiverQueue actualSenderAndReceiverQueue = senderAndReceiverQueueRepository.findByReqIdAndAlarmTypeId(expectedSender.getId(),expectedAlarmType.getId()).get(0);
		
	
		Assertions.assertEquals(expectedSenderAndReceiverQueue, actualSenderAndReceiverQueue );
	}	
	
	@Test
	public void shouldDeleteById() {
		
		senderAndReceiverQueueRepository.deleteById(expectedSenderAndReceiverQueue.getId());
		
		
		Assertions.assertThrows(JpaObjectRetrievalFailureException.class, ()->{
			senderAndReceiverQueueRepository.getOne(expectedSenderAndReceiverQueue.getId());
		});
	}
	
}
