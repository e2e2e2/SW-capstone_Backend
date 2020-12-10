package com.capstone.helper.repository;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.helper.model.Token;
import com.capstone.helper.model.User;

@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TokenRepositoryTest {

	@Autowired
	UserRepository userRepository;
	@Autowired
	TokenRepository tokenRepository;
	

	@Test
	public void shouldFindTokensByUserID() {
		User expectedUser = new User("a","name","pw",0,"111-2222-3333","address");
		userRepository.save(expectedUser);
		Assertions.assertNotEquals(null,userRepository.getOne(expectedUser.getId()));
		
		Token expectedToken = new Token(expectedUser.getId(),"cklBYJZ4TnalsXkdFYbV1O:APA91bHjPnBWVBIRtez283VIKBVLzPbDmeTBSqd7nU_97nkPYf8XYHvEzwNkCmPmuzqmJfMOWUIiwIvuSf1QjYX1rz32CJftvMZzm6Y0cwSBImZTL5tcVPJscSalRZ33n2WvM-pdhQKP");
		tokenRepository.save(expectedToken);
		Assertions.assertNotEquals(null,tokenRepository.getOne(expectedToken.getId()));
		
		Token actualToken = tokenRepository.findByUserId(expectedUser.getId()).get(0);
		
		Assertions.assertEquals(expectedToken, actualToken);
		
	}	
	
}
