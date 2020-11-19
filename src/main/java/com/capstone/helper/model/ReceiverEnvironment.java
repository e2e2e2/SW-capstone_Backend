package com.capstone.helper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ReceiverEnvironments")
public class ReceiverEnvironment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name="userId")
	Integer userId;

	@Column(name="hasWeb")
	Boolean hasWeb;
	
	@Column(name="hasApp")
	Boolean hasApp;

	public ReceiverEnvironment(Integer userId, Boolean hasWeb, Boolean hasApp) {
		this.userId = userId;
		this.hasWeb = hasWeb;
		this.hasApp = hasApp;
	}

	public ReceiverEnvironment() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Boolean getHasWeb() {
		return hasWeb;
	}

	public void setHasWeb(Boolean hasWeb) {
		this.hasWeb = hasWeb;
	}

	public Boolean getHasApp() {
		return hasApp;
	}

	public void setHasApp(Boolean hasApp) {
		this.hasApp = hasApp;
	}
	
	
}
