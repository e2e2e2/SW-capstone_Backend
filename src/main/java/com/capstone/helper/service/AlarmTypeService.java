package com.capstone.helper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capstone.helper.model.AlarmType;
import com.capstone.helper.repository.AlarmTypeRepository;

@Service("alarmTypeService")
public class AlarmTypeService {
	@Autowired
	private AlarmTypeRepository alarmTypeRepository;
	
	public List<AlarmType> findAll(){
		List<AlarmType> alarms = new ArrayList<>();
		alarmTypeRepository.findAll().forEach(e -> alarms.add(e));
		return alarms;
	}

	public List<Integer> findAllId(){
		List<Integer> alarmId = new ArrayList<>();
		alarmTypeRepository.findAll().forEach(e -> alarmId.add(e.getId()));
		
		return alarmId;
	}
	
	public AlarmType findByAlarmName(String alarmName) {
		AlarmType alarmType = new AlarmType();
		alarmType = alarmTypeRepository.findByAlarmName(alarmName);
		return alarmType;
	}
	
	public AlarmType save(AlarmType alarmType) { 
		 alarmTypeRepository.save(alarmType); 
		 return alarmType; 
	 }

}
