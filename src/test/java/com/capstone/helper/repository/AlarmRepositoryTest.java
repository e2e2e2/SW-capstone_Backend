package com.capstone.helper.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import com.capstone.helper.model.User;
import com.capstone.helper.model.Alarm;
import com.capstone.helper.model.AlarmType;
import com.capstone.helper.model.NonActiveEvent;

@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AlarmRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AlarmRepository alarmRepository;
	
	@Autowired
	AlarmTypeRepository alarmTypeRepository;
	
	@Autowired
	NonActiveEventRepository nonActiveEventRepository;
	
	User expectedSender;
	User expectedReceiver;
	
	AlarmType expectedAlarmType;
	NonActiveEvent expectedNonActiveEvent;
	Alarm expectedAlarm;
	
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
		
		LocalDateTime timestamp = LocalDateTime.now(); 
		expectedNonActiveEvent = new NonActiveEvent(expectedSender.getId(),(float)127.0,(float)37.0,timestamp);
		nonActiveEventRepository.save(expectedNonActiveEvent);
		Assertions.assertNotEquals(null,nonActiveEventRepository.getOne(expectedNonActiveEvent.getId()));
		
		expectedAlarm = new Alarm(expectedAlarmType.getId(),expectedNonActiveEvent.getId()
				,expectedSender.getId(),expectedReceiver.getId(),expectedNonActiveEvent.getTimestamp());
		alarmRepository.save(expectedAlarm);
		Assertions.assertNotEquals(null,alarmRepository.getOne(expectedAlarm.getId()));
		
	}

	@Test
	public void shouldFindAlarmByReceiverId() {
		
		Alarm actualAlarm = alarmRepository.findByReceiverId(expectedReceiver.getId()).get(0);
		

		Assertions.assertEquals(expectedAlarm, actualAlarm);
		
		
	}	



	
}
