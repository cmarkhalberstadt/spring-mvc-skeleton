package com.xpanxion.skeleton.service;

import java.util.List;

import com.xpanxion.skeleton.dto.beans.UserBean;

public interface RestApiUserService {
	/**
     * Return all of the available user test beans. 
     * 
     * @return all of the user test beans
     */
    List<UserBean> getUserBeans();
    
    
    public boolean validateAndChangeUserPassword(String username, String oldPassword, String newPassword);
    public boolean validateAndAddNewUserToDatabase(String username, String password);
    public boolean deleteUserFromDatabase(String Username);
    
    public UserBean getUserWithUsername(String Username);
    public void changePasswordOfUser(String Username, String newPassword);
    public void addUserToDatabase(String Username, String Password);
    
    
    
    public boolean isUsernameInDatabase(String Username);
    public boolean isPasswordCorrectForGivenUsername(String Username, String Password);
}
