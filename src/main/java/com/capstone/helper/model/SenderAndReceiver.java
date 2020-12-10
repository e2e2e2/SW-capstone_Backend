package com.capstone.helper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SendersAndReceivers")
public class SenderAndReceiver {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name="senderId")
	Integer senderId;
	
	@Column(name="receiverId")
	Integer receiverId;
	
	@Column(name="alarmTypeId")
	Integer alarmTypeId;

	public SenderAndReceiver(Integer senderId, Integer receiverId, Integer alarmTypeId) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.alarmTypeId = alarmTypeId;
	}
	public SenderAndReceiver() {
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getAlarmTypeId() {
		return alarmTypeId;
	}
	public void setAlarmTypeId(Integer alarmTypeId) {
		this.alarmTypeId = alarmTypeId;
	}

	
	
}
