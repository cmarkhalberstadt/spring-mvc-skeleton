package com.xpanxion.skeleton.dto.beans;

public class ChangeUserPasswordBean {
	
	private String oldpassword;
	private String newpassword;
	
	public ChangeUserPasswordBean(){}
	
	public String getNewpassword() {
		return newpassword;
	}


	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}


	public String getOldpassword() {
		return oldpassword;
	}


	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
}
