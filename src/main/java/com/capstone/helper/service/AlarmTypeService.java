package com.capstone.helper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capstone.helper.model.AlarmType;
import com.capstone.helper.repository.AlarmTypeRepository;

@Service("alarmTypeService")
public class AlarmTypeService {
	@Autowired
	private AlarmTypeRepository alarmTypeRepository;
	
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
