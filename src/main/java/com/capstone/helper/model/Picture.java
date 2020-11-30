package com.capstone.helper.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Pictures")
public class Picture {
	
	
	@Id
	Integer eventId;

	@Column(name="pic_url")
	String pic_url;
	

	public Picture(Integer eventId, String picURL) {
		this.eventId = eventId;
		this.pic_url = picURL;
	}

	public Picture() {
	}

	
	
	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
	public String getPicURL() {
		return pic_url;
	}
	public void setPicURL(String picURL) {
		this.pic_url = picURL;
	}
	
}
