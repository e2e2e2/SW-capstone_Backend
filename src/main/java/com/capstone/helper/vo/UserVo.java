package com.capstone.helper.vo;



public class UserVo{
	String name;
	String password;
	Integer auth;
	String phone_number;
	String address;
	
	
    public UserVo(String name, String password, int auth, String phone_number, String address) {

    	this.password = password;
    	this.auth = auth;
    	this.name = name;
    	this.phone_number = phone_number;
    	this.address = address;
    }
	public UserVo() {
	
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





