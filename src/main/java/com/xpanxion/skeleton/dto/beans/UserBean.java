package com.xpanxion.skeleton.dto.beans;

public class UserBean {
	private long id;
	private String userName;
	private String password;
	
	/**
     * Sets the id of this bean
     * 
     * @param id
     *            the id to set
     */
	public void setID(long id){
		this.id = id;
	}
	
	/**
     * Returns the id of this bean
     * 
     * @return the id the id of this bean
     */
    public long getId() {
        return this.id;
    }
	
	/**
	 * Sets the user name of this bean
	 * 
	 * @param userName the username to set
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	/**
	 * Returns the user name of this bean
	 * 
	 * @return the user name of this bean
	 */
	public String getUserName(){
		return this.userName;
	}
	
	/**
	 * Sets the password of this bean
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * Returns the password of this bean
	 * 
	 * @return the password of this bean
	 */
	public String getPassword(){
		return this.password;
	}
}
