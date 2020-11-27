package com.capstone.helper.vo;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class HomeOutEventVo {

	private int userId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime timestamp;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
