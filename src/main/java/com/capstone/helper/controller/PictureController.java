package com.capstone.helper.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.helper.model.Picture;
import com.capstone.helper.service.PictureService;

@RestController
public class PictureController {
	
	@Autowired
	PictureService pictureService;
	
	//이미지 전송
	@RequestMapping(value="/test/postImage", method=RequestMethod.POST)
	public Integer getFallEventByEventId(HttpServletRequest request, @RequestPart MultipartFile data) throws Exception{
		Picture pic = new Picture();
		
		String sourceFileName = data.getOriginalFilename(); 
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); 
        File destinationFile; 
        String destinationFileName = FilenameUtils.getBaseName(sourceFileName);
        String fileUrl = "../Pictures/";

        destinationFile = new File(fileUrl + destinationFileName); 
        
        destinationFile.getParentFile().mkdirs(); 
        data.transferTo(destinationFile);
        
        pic.setPicURL(fileUrl + destinationFileName);
        pic.setEventId(Integer.valueOf(destinationFileName));
        pictureService.save(pic);
        
        return pic.getEventId();
	}
}
