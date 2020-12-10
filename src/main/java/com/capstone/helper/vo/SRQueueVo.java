package com.capstone.helper.vo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SRQueueVo{
	


	Integer queueId;
	String name;
	Integer auth;
	String phone_number;
	String address;
	Integer alarmType;
	
	

	public SRQueueVo(Integer queId, String name,  int auth, String phone_number, String address, Integer alarmType) {
		this.queueId =  queId;
    	this.auth = auth;
    	this.name = name;
    	this.phone_number = phone_number;
    	this.address = address;
    	this.alarmType = alarmType;
    }
    
	public SRQueueVo() {
	
	}


	public Integer getQueueId() {
		return queueId;
	}
	public void setQueueId(int queId) {
		this.queueId =  queId;
	}
	
	
	//name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	
	//phone_number
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
		
	
	//address
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	//authorization
	public int getAuth() {
		return auth;
	}
	public void setAuth(Integer auth) {
		if(auth == null) this.auth = -1;
		else this.auth = auth;
	}
	

    public Integer getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
}





