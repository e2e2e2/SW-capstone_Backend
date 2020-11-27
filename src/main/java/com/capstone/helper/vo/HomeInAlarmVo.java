package com.capstone.helper.vo;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class HomeInAlarmVo {

	private int senderId;
	private String alarmType;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime timestamp;
	private float longitude;
	private float latitude;
	
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public HomeInAlarmVo(int senderId, String alarmType, LocalDateTime timestamp, float longitude, float latitude) {
		this.senderId = senderId;
		this.alarmType = alarmType;
		this.timestamp = timestamp;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	
}
