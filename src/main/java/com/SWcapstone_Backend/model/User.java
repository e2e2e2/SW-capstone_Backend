package com.SWcapstone_Backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "User")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name="name")
	String name;
	
	@Column(name="password")
	String password;
	
	@Column(name="auth")
	Integer auth;
	
	@Column(name="phone_number")
	String phone_number;
	
	@Column(name="address")
	String address;
	
	
    public User(int id, String name, String password, int auth, String phone_number, String address) {
    	this.id = id;
    	this.password = password;
    	this.auth = auth;
    	this.name = name;
    	this.phone_number = phone_number;
    	this.address = address;
    	System.err.println("make user addr : " + address);
    }
	public User() {
	
	}
	//id
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
	public boolean matchPassword(String password) {
		if(this.password == password)
			return true;
		else 
			return false;
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
		return auth;
	}
	public void setAuth(Integer auth) {
		if(auth == null) this.auth = -1;
		else this.auth = auth;
	}
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}
}





