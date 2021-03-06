package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.Picture;
import com.capstone.helper.repository.PictureRepository;

@Service("pictureService")
public class PictureService {
	@Autowired
	PictureRepository pictureRepository;
	
	public void save(Picture pic) {
		Picture p = new Picture();
		p.setEventId(pic.getEventId());
		p.setTag(pic.getTag());
		p.setPicURL(pic.getPicURL());
		
		pictureRepository.save(p);
	}
	
	public String findByEventID(int enevtID,String tag) {
		
		Picture p = pictureRepository.findByEventIdAndTag(enevtID, tag);
		
		return p.getPicURL();
	}
	
}
