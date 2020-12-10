package com.capstone.helper.vo;



public class SRQueueVo{
	
	
	Integer queueId;
	String name;
	Integer auth;
	String phone_number;
	String address;
	
	

	public SRQueueVo(Integer queueId, String name,  int auth, String phone_number, String address) {
		this.queueId = queueId;
    	this.auth = auth;
    	this.name = name;
    	this.phone_number = phone_number;
    	this.address = address;
    }
    
	public SRQueueVo() {
	
	}

	public Integer getQueueId() {
		return queueId;
	}

	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
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
}





