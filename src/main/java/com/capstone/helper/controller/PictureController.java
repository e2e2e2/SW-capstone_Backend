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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.helper.model.Picture;
import com.capstone.helper.service.FallEventService;
import com.capstone.helper.service.NonActiveEventService;
import com.capstone.helper.service.PictureService;

import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
public class PictureController {
	
	@Autowired
	private PictureService pictureService;
	@Autowired
	private FallEventService fallEventService;
	@Autowired
	private NonActiveEventService nonActiveEventService;
	

	private static String FILE_URL = System.getProperty("user.dir") + "\\Pictures\\";
	
	//이미지 전송
	@RequestMapping(value="/test/postImage", method=RequestMethod.POST)
	public Integer postPictures(HttpServletRequest request, @RequestPart MultipartFile files, 
			@RequestParam("tag") String tag) throws Exception{
		
		
		
		String srcFileFullName = files.getOriginalFilename(); 
		String srcFileName = FilenameUtils.getBaseName(srcFileFullName);
        String srcFileNameExtension = FilenameUtils.getExtension(srcFileFullName).toLowerCase(); 
        Integer numId = Integer.valueOf(srcFileName);
        
        
        File destFile = new File(FILE_URL +  tag); 
        
        if (!destFile.isDirectory()) {

        	destFile.mkdirs();

    	}
        
        
        String destFileLoc = FILE_URL  + tag + "\\" + srcFileName +  "." + srcFileNameExtension;

        destFile = new File(FILE_URL);         
        
        System.out.println("destinationFile = "+ destFile.getAbsolutePath());
        System.out.println("destinationFile = "+ destFile.getAbsolutePath());
        System.out.println("destinationFile.getParentFile() = " + destFile.getParentFile());
        System.out.println("destFileLoc = " + destFileLoc);
        
        
        
        files.transferTo(new File(destFileLoc));
        
        Picture pic = new Picture(numId,tag,destFileLoc);
        
        pictureService.save(pic);
	    
        if(tag.equals("F"))	fallEventService.toFalse(numId);
        else 				nonActiveEventService.toFalse(numId);
        return pic.getEventId();
	}
	
	
	//for
	@RequestMapping(value="/test/fall/pictures/{event-id}", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getFallPictures(HttpServletRequest request, @PathVariable("event-id") String eventID) throws Exception{
		
		Integer numId = Integer.valueOf(eventID);
		
		String picURL = pictureService.findByEventID(numId, "F");
		
		File imgPath = new File(picURL);

	    byte[] image = Files.readAllBytes(imgPath.toPath());
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    headers.setContentLength(image.length);
	    
	    return new ResponseEntity<>(image, headers, HttpStatus.OK);
	}
	

	@RequestMapping(value="/test/non-active/pictures/{event-id}", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getNonActPictures(HttpServletRequest request, @PathVariable("event-id") String eventID) throws Exception{
		
		
		String picURL = pictureService.findByEventID(Integer.valueOf(eventID), "A");
		
		File imgPath = new File(picURL);

	    byte[] image = Files.readAllBytes(imgPath.toPath());
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    headers.setContentLength(image.length);
	    return new ResponseEntity<>(image, headers, HttpStatus.OK);
	}
}
