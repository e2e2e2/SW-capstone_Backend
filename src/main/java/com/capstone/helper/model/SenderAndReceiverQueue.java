package com.capstone.helper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SendersAndReceiversQueue")
public class SenderAndReceiverQueue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name="reqId")
	Integer reqId;
	

	@Column(name="targetId")
	Integer targetId;
	
	@Column(name="alarmTypeId")
	Integer alarmTypeId;

	public SenderAndReceiverQueue(Integer id, Integer reqId, Integer targetId, Integer alarmTypeId) {
		this.id = id;
		this.reqId = reqId;
		this.targetId = targetId;
		this.alarmTypeId = alarmTypeId;
	}
	public SenderAndReceiverQueue(Integer reqId, Integer targetId, Integer alarmTypeId) {
		super();
		this.reqId = reqId;
		this.targetId = targetId;
		this.alarmTypeId = alarmTypeId;
	}



	public SenderAndReceiverQueue() {
		super();
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReqId() {
		return reqId;
	}

	public void setReqId(Integer reqId) {
		this.reqId = reqId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Integer getAlarmTypeId() {
		return alarmTypeId;
	}

	public void setAlarmTypeId(Integer alarmTypeId) {
		this.alarmTypeId = alarmTypeId;
	}
}
