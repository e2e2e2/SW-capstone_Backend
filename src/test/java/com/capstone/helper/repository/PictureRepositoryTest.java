package com.capstone.helper.repository;

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

@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class PictureRepositoryTest {

	
	@Autowired
	PictureRepository pictureRepository; 
	
	@Autowired
	NonActiveEventRepository nonActiveEventRepository;
	
	Picture expectedPicture;
	NonActiveEvent expectedNonActiveEvent;
	
	@BeforeEach
	public void setUp() {
		
		expectedNonActiveEvent = new NonActiveEvent();
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
