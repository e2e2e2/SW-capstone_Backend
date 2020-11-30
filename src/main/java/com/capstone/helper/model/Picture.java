package com.capstone.helper.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "Pictures")
@IdClass(PictureId.class)
public class Picture{
	
	
	@Id
	Integer eventId;

	@Id
	String tag;
	
	@Column(name="pic_url")
	String pic_url;
	

	public Picture(Integer eventId, String tag, String picURL) {
		this.eventId = eventId;
		this.tag = tag;
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

	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	public String getPicURL() {
		return pic_url;
	}
	public void setPicURL(String picURL) {
		this.pic_url = picURL;
	}
	
}



