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

	
}
