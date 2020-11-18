package com.capstone.helper.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Alarms")
public class Alarm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name="alarmTypeId")
	Integer alarmTypeId;

	@Column(name="eventId")
	Integer eventId;

	@Column(name="senderId")
	Integer senderId;

	@Column(name="receiverId")
	Integer receiverId;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@Column(name="timestamp")
	LocalDateTime timestamp;

	public Alarm(Integer alarmTypeId, Integer eventId, Integer senderId, Integer receiverId, LocalDateTime timestamp) {
		this.alarmTypeId = alarmTypeId;
		this.eventId = eventId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.timestamp = timestamp;
	}

	public Alarm() {
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAlarmTypeId() {
		return alarmTypeId;
	}

	public void setAlarmTypeId(Integer alarmTypeId) {
		this.alarmTypeId = alarmTypeId;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Integer getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	
}
