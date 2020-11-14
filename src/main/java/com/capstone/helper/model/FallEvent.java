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
@Table(name = "FallEvents")
public class FallEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name="userId")
	Integer userId;

	@Column(name="latitude")
	Float latitude;

	@Column(name="longitude")
	Float longitude;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@Column(name="timestamp")
	LocalDateTime timestamp;

	public FallEvent(Integer userId, Float latitude, Float longitude, LocalDateTime timestamp) {
		this.userId = userId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
	}

	public FallEvent() {
	}

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
