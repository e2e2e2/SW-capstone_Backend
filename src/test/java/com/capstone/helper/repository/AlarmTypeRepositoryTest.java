package com.capstone.helper.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import com.capstone.helper.model.User;
import com.capstone.helper.model.AlarmType;

@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AlarmTypeRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AlarmTypeRepository alarmTypeRepository;
	
	User expectedUser;
	AlarmType expectedAlarmType;
	
	@BeforeEach
	public void setUp() {
		expectedUser = new User("a","name","pw",0,"111-2222-3333","address");
		userRepository.save(expectedUser);
		Assertions.assertNotEquals(null,userRepository.getOne(expectedUser.getId()));
		
		expectedAlarmType = new AlarmType();
		expectedAlarmType.setAlarmName("fallalarm");
		alarmTypeRepository.save(expectedAlarmType);
		Assertions.assertNotEquals(null,alarmTypeRepository.getOne(expectedAlarmType.getId()));
		
	}

	@Test
	public void shouldFindAlarmTypeByAlarmName() {
		
		AlarmType actualAlarmType = alarmTypeRepository.findByAlarmName(expectedAlarmType.getAlarmName());
		

		Assertions.assertEquals(expectedAlarmType, actualAlarmType );
		
		
	}	


	
}
