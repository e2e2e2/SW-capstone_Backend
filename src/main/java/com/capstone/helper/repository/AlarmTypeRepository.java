package com.capstone.helper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.helper.model.AlarmType;

public interface AlarmTypeRepository extends JpaRepository<AlarmType, Integer>{

	AlarmType findByAlarmName(String AlarmName);
}
