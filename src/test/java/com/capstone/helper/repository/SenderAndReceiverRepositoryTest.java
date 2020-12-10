package com.capstone.helper.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.helper.model.AlarmType;
import com.capstone.helper.model.SenderAndReceiver;
import com.capstone.helper.model.User;

@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class SenderAndReceiverRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AlarmTypeRepository alarmTypeRepository;
	
	@Autowired
	SenderAndReceiverRepository senderAndReceiverRepository; 

	@Test
	public void shouldFindSenderAndReceiverByReceiverId() {
		User expectedSender = new User("a","name","pw",0,"111-2222-3333","address");
		userRepository.save(expectedSender);
		Assertions.assertNotEquals(null,userRepository.getOne(expectedSender.getId()));
		
		User expectedReceiver = new User("b","name1","pw1",0,"111-2222-4444","address1");
		userRepository.save(expectedReceiver);
		Assertions.assertNotEquals(null,userRepository.getOne(expectedReceiver.getId()));
		
		AlarmType expectedAlarmType = new AlarmType();
		expectedAlarmType.setAlarmName("fallalarm");
		alarmTypeRepository.save(expectedAlarmType);
		Assertions.assertNotEquals(null,alarmTypeRepository.getOne(expectedAlarmType.getId()));
		
		SenderAndReceiver expectedSenderAndReceiver = new SenderAndReceiver(expectedSender.getId(),
				expectedReceiver.getId(),expectedAlarmType.getId());
		senderAndReceiverRepository.save(expectedSenderAndReceiver);
		Assertions.assertNotEquals(null,senderAndReceiverRepository.getOne(expectedSenderAndReceiver.getId()));
		
		
		SenderAndReceiver actualSenderAndReceiver = senderAndReceiverRepository.findByReceiverId(expectedReceiver.getId()).get(0);
		
		
		System.out.println(expectedSenderAndReceiver.getId());
		System.out.println(actualSenderAndReceiver.getId());
		Assertions.assertEquals(expectedSenderAndReceiver, actualSenderAndReceiver );
		
		
	}	
	
}
