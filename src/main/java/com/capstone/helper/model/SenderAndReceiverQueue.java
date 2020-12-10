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
	@Column(name="id")
	Integer queueId;
	
	@Column(name="reqId")
	Integer reqId;
	
	@Column(name="targetId")
	Integer targetId;

	public SenderAndReceiverQueue( Integer reqId, Integer targetId) {
		this.reqId = reqId;
		this.targetId = targetId;
	}
	
	public SenderAndReceiverQueue() {
		
	}

	public Integer getId() {
		return queueId;
	}
	public void setId(int queId) {
		this.queueId =  queId;
	}

	public Integer getQueueId() {
		return queueId;
	}
	public void setQueueId(int queId) {
		this.queueId =  queId;
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

}
