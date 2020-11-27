package com.capstone.helper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Homes")
public class Home {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name="userId")
	Integer userId;
	
	@Column(name="latitude")
	float latitude;
	
	@Column(name="longitude")
	float longitude;

	public Home(Integer userId, Float latitude, Float longitude) {
		this.userId = userId;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Home() {
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


	
}
