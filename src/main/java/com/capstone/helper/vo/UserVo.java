package com.capstone.helper.vo;


import javax.persistence.Column;

import com.capstone.helper.model.User;

public class UserVo{
	
	
	Integer id;
	
	String userID;
	String name;
	String password;
	Integer auth;
	String phone_number;
	String address;
	
	
    public UserVo(int id, String name, String password, int auth, String phone_number, String address) {
    	this.id = id;
    	this.password = password;
    	this.auth = auth;
    	this.name = name;
    	this.phone_number = phone_number;
    	this.address = address;
    }
    
	public UserVo() {
	
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id =  id;
	}
	

	//userID
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID =  userID;
	}
	
	
	//name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	
	//phone_number
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
		
	
	//address
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	//authorization
	public int getAuth() {
		return auth;
	}
	public void setAuth(Integer auth) {
		if(auth == null) this.auth = -1;
		else this.auth = auth;
	}
	
	//password
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}





