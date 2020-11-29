package com.capstone.helper.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.helper.model.Picture;
import com.capstone.helper.service.PictureService;

import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
public class PictureController {
	
	@Autowired
	PictureService pictureService;
	
	//이미지 전송
	@RequestMapping(value="/test/postImage", method=RequestMethod.POST)
	public Integer getFallEventByEventId(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
		Picture pic = new Picture();
		
		String sourceFileName = files.getOriginalFilename(); 
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); 
        File destinationFile; 
        String destinationFileName = FilenameUtils.getBaseName(sourceFileName);
        String fileUrl = "../../Pictures/";

        destinationFile = new File(fileUrl + destinationFileName); 
        System.out.println("destinationFile = "+ destinationFile);
        System.out.println("destinationFile.getParentFile() = " + destinationFile.getParentFile());
        destinationFile.getParentFile().mkdirs(); 
        files.transferTo(destinationFile.getParentFile());
        
        pic.setPicURL(destinationFile + sourceFileNameExtension);
        pic.setEventId(Integer.valueOf(destinationFileName));
        pictureService.save(pic);
        
        System.out.println("result = "+ pic.getEventId());
        return pic.getEventId();
	}
	

}
