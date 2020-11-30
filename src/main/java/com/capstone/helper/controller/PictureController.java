package com.capstone.helper.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
	private static String FILE_URL = System.getProperty("user.dir") + "/Pictures/";
	
	//이미지 전송
	@RequestMapping(value="/test/postImage", method=RequestMethod.POST)
	public Integer postPictures(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
		Picture pic = new Picture();
		
		String srcFileName = files.getOriginalFilename(); 
        String srcFileNameExtension = FilenameUtils.getExtension(srcFileName).toLowerCase(); 
        File destFile; 
        String destinationFileName = FilenameUtils.getBaseName(srcFileName);

        destFile = new File(FILE_URL);         
        
        System.out.println("destinationFile = "+ destFile.getAbsolutePath());
        System.out.println("destinationFile = "+ destFile.getAbsolutePath());
        System.out.println("destinationFile.getParentFile() = " + destFile.getParentFile());
        System.out.println("destFile.isDirectory() = " + destFile.isDirectory());
        
        if (!destFile.isDirectory()) {

        	destFile.mkdirs();

    	}
        
        files.transferTo(new File(FILE_URL + destinationFileName + "." + srcFileNameExtension));
        
        pic.setPicURL(destFile + "." + srcFileNameExtension);
        pic.setEventId(Integer.valueOf(destinationFileName));
        
        pictureService.save(pic);
        
        System.out.println("result = "+ pic.getEventId());
        return pic.getEventId();
	}
	
	@RequestMapping(value="/test/pictures/{event-id}", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getPictures(HttpServletRequest request, @PathVariable("event-id") String eventID) throws Exception{
		
		
		
		File imgPath = new File(FILE_URL + eventID + ".jpg");

	    byte[] image = Files.readAllBytes(imgPath.toPath());
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    headers.setContentLength(image.length);
	    return new ResponseEntity<>(image, headers, HttpStatus.OK);
	}
	

}
