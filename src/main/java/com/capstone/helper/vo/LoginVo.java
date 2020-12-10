package com.capstone.helper.vo;



public class LoginVo{
	
	
	String userID;
	String password;
	
	
    public LoginVo(String userID, String name, String password, int auth, String phone_number, String address) {
    	this.userID = userID;
    	this.password = password;
    }
    
	public LoginVo() {
	
	}
	
	

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID =  userID;
	}
	
	//password
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}





