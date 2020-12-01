package com.capstone.helper.vo;



public class ReceiverInfoVo {

	private String phone_number;
	private boolean non_active;
	private boolean fall_down;
	private boolean gps;
	
	
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public boolean isNon_active() {
		return non_active;
	}
	public void setNon_active(boolean non_active) {
		this.non_active = non_active;
	}
	public boolean isFall_down() {
		return fall_down;
	}
	public void setFall_down(boolean fall_down) {
		this.fall_down = fall_down;
	}
	public boolean isGps() {
		return gps;
	}
	public void setGps(boolean gps) {
		this.gps = gps;
	}

	
	
}
