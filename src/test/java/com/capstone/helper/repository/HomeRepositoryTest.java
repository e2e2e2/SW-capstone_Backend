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
import com.capstone.helper.model.Home;

@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class HomeRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HomeRepository homeRepository;
	
	User expectedUser;
	Home expectedHome;
	
	@BeforeEach
	public void setUp() {
		expectedUser = new User("a","name","pw",0,"111-2222-3333","address");
		userRepository.save(expectedUser);
		Assertions.assertNotEquals(null,userRepository.getOne(expectedUser.getId()));
		
		expectedHome = new Home(expectedUser.getId(),(float)127.0,(float)37.0);
		homeRepository.save(expectedHome);
		Assertions.assertNotEquals(null,homeRepository.getOne(expectedHome.getId()));
		
	}

	@Test
	public void shouldFindHomeByUserId() {
		
		Home actualHome = homeRepository.findByUserId(expectedUser.getId());
		

		Assertions.assertEquals(expectedHome, actualHome );
		
		
	}	
	
	
}
