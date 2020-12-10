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

import com.capstone.helper.model.NonActiveEvent;
import com.capstone.helper.model.Picture;
import com.capstone.helper.model.User;

@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class PictureRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PictureRepository pictureRepository; 
	
	@Autowired
	NonActiveEventRepository nonActiveEventRepository;
	
	User expectedUser;
	Picture expectedPicture;
	NonActiveEvent expectedNonActiveEvent;
	
	@BeforeEach
	public void setUp() {
		
		expectedUser = new User("a","name","pw",0,"111-2222-3333","address");
		userRepository.save(expectedUser);
		Assertions.assertNotEquals(null,userRepository.getOne(expectedUser.getId()));
		
		LocalDateTime timestamp = LocalDateTime.now(); 
		expectedNonActiveEvent = new NonActiveEvent(expectedUser.getId(),(float)127.0,(float)37.0,timestamp);
		nonActiveEventRepository.save(expectedNonActiveEvent);
		Assertions.assertNotEquals(null,nonActiveEventRepository.getOne(expectedNonActiveEvent.getId()));
		
		expectedPicture = new Picture(expectedNonActiveEvent.getId(),"C","picUrl");
		pictureRepository.save(expectedPicture);
	}

	@Test
	public void shouldFindPictureByEventIdAndTag() {
		
		Picture actualPicture = pictureRepository.findByEventIdAndTag(expectedNonActiveEvent.getId().intValue(),expectedPicture.getTag());
		
		Assertions.assertEquals(expectedPicture.getEventId(), actualPicture.getEventId());
		Assertions.assertEquals(expectedPicture.getTag(), actualPicture.getTag());
		Assertions.assertEquals(expectedPicture.getPicURL(), actualPicture.getPicURL());
		
	}	
	
	
}
