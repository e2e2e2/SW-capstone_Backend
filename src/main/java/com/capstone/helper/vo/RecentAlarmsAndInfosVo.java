package com.capstone.helper.vo;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class RecentAlarmsAndInfosVo {

	
	private int alarmTypeId;
	private int eventId;
	private int id;
	private int receiverId;
	private int senderId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime timestamp;
	private String senderName;
	private String phoneNumber;
	private String isValid;
	public int getAlarmTypeId() {
		return alarmTypeId;
	}
	public void setAlarmTypeId(int alarmTypeId) {
		this.alarmTypeId = alarmTypeId;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public RecentAlarmsAndInfosVo(int alarmTypeId, int eventId, int id, int receiverId, int senderId,
			LocalDateTime timestamp, String senderName, String phoneNumber, String isValid) {
		super();
		this.alarmTypeId = alarmTypeId;
		this.eventId = eventId;
		this.id = id;
		this.receiverId = receiverId;
		this.senderId = senderId;
		this.timestamp = timestamp;
		this.senderName = senderName;
		this.phoneNumber = phoneNumber;
		this.isValid = isValid;
	}

}
