package com.capstone.helper.repository;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.helper.model.User;

@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	

	@Test
	public void shouldFindUsersByUserID() {
		User expectedUser = new User("a","name","pw",0,"111-2222-3333","address");
		userRepository.save(expectedUser);
		
		Assertions.assertNotEquals(null,userRepository.getOne(expectedUser.getId()));
		
		User actualUser = userRepository.findByUserID(expectedUser.getUserID()).get(0);
		
		Assertions.assertEquals(expectedUser, actualUser);
		
	}
	
	
	@Test
	public void shouldFindUsersByPhoneNumber() {
		User expectedUser = new User("a","name","pw",0,"111-2222-3333","address");
		userRepository.save(expectedUser);
		
		Assertions.assertNotEquals(null,userRepository.getOne(expectedUser.getId()));
		
		User actualUser = userRepository.findByPhoneNumber(expectedUser.getPhone_number()).get(0);
		
		Assertions.assertEquals(expectedUser, actualUser);
		
	}

	
	
}
