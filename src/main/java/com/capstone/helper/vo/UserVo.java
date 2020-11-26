package com.capstone.helper.vo;


import com.capstone.helper.model.User;

public class UserVo{
	Integer id;
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
	
	//name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	//password
	public String getPassword() {
		return this.password;
	}
}





