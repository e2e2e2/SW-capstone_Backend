package com.capstone.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.helper.model.Alarm;
import com.capstone.helper.repository.AlarmRepository;

@Service("alarmService")
public class AlarmService {
	@Autowired
	private AlarmRepository alarmRepository;
	
	public List<Alarm> findByReceiverId(int receiverId){
		List<Alarm> alarms = new ArrayList<>();
		alarmRepository.findByReceiverId(receiverId).forEach(e -> alarms.add(e));
		return alarms;
	}
	
	public Alarm save(Alarm alarm) { 
		 alarmRepository.save(alarm); 
		 return alarm; 
	 }
	
}
