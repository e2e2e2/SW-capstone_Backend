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

	@Column(name="is_true")
	boolean isTrue;
	
	public FallEvent(Integer userId, Float latitude, Float longitude, LocalDateTime timestamp) {
		this.userId = userId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
		this.isTrue = true;
	}

	public FallEvent() {
	}

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public boolean getIsTrue() {
		return isTrue;
	}

	public void toFalse() {
		this.isTrue = false;
	}
	
}
