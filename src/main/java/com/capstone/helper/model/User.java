package com.capstone.helper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "Users")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name="userid")
	String userID;
	
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
	
	
    public User(String userID, String name, String password, int auth, String phone_number, String address) {
    	this.userID = userID;
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

	
	//password
	public boolean matchPassword(String password) {
		if( password.equals(this.password))
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
	
	public int hashCode() { return (id).hashCode(); }
	
	public boolean equals(Object obj) {

        if(obj instanceof User) {

            User temp = (User)obj;

            return this.id.equals(temp.getId());

        } else {

            return false;

        }

    }
	
}





