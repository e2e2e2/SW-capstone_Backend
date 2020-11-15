package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.Alarm;
import com.capstone.helper.repository.AlarmRepository;

@Service("alarmService")
public class AlarmService {
	@Autowired
	private AlarmRepository alarmRepository;
	
	public Alarm save(Alarm alarm) { 
		 alarmRepository.save(alarm); 
		 return alarm; 
	 }
	
}
