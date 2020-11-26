package com.capstone.helper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AlarmTypes")
public class AlarmType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name="alarmName")
	String alarmName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public AlarmType(String alarmName) {
		this.alarmName = alarmName;
	}

	public AlarmType() {
	}
	
	

}
