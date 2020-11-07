package com.user.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class tempUser{
	String name;
	String password;
	Integer auth;
	String phone_number;
	String address;
	
	
    public tempUser(String name, String password, int auth, String phone_number, String address) {

    	this.password = password;
    	this.auth = auth;
    	this.name = name;
    	this.phone_number = phone_number;
    	this.address = address;
    }
	public tempUser() {
	
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
	public void setPassword(String password) {
		this.password = password;
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
		
		if(this.auth == null)
			return -1;
		return auth;
	}
	public void setAuth(Integer auth) {

		if(auth == null) this.auth = -1;
		else this.auth = auth;
	}
}





