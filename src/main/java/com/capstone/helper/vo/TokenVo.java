package com.capstone.helper.vo;



public class TokenVo {

	Integer userId;
	String token;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public TokenVo(Integer userId, String token) {
		super();
		this.userId = userId;
		this.token = token;
	}
	public TokenVo() {
		super();
	}
	
	
}
