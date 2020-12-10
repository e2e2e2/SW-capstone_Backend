package com.capstone.helper.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import com.capstone.helper.model.ReceiverEnvironment;
import com.capstone.helper.model.User;

@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ReceiverEnvironmentRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ReceiverEnvironmentRepository receiverEnvironmentRepository; 
	
	User expectedUser;
	ReceiverEnvironment expectedReceiverEnvironment;
	
	@BeforeEach
	public void setUp() {
		expectedUser = new User("a","name","pw",0,"111-2222-3333","address");
		userRepository.save(expectedUser);
		Assertions.assertNotEquals(null,userRepository.getOne(expectedUser.getId()));
		
		expectedReceiverEnvironment = new ReceiverEnvironment(expectedUser.getId(),
				false,false);
		receiverEnvironmentRepository.save(expectedReceiverEnvironment);
		Assertions.assertNotEquals(null,receiverEnvironmentRepository.getOne(expectedReceiverEnvironment.getId()));
	}

	@Test
	public void shouldFindReceiverEnvironmentByUserId() {
		
		ReceiverEnvironment actualReceiverEnvironment = receiverEnvironmentRepository.findByUserId(expectedUser.getId());
		

		Assertions.assertEquals(expectedReceiverEnvironment, actualReceiverEnvironment );
		
		
	}	
	
	
}
