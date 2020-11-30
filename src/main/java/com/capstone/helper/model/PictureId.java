package com.capstone.helper.model;

import java.io.Serializable;

import javax.persistence.Id;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class PictureId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	Integer eventId;
	@Id
	String tag;
    
    public PictureId() {
    	
    }


	public PictureId(Integer eventId, String tag) {
    	this.eventId =eventId;
    	this.tag = tag;
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
   }
