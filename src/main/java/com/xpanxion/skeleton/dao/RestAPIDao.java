package com.xpanxion.skeleton.dao;

import java.util.List;

import com.xpanxion.skeleton.dto.beans.UserBean;
import com.xpanxion.skeleton.dto.entity.UserEntity;

public interface RestApiDao {
	
	List<UserEntity> getAllItems();
	
	public UserBean getUserWithUsername(String Username);
    public void changePasswordOfUser(String Username, String newPassword);
    public void addUserToDatabase(String Username, String Password);
    public void deleteUserFromDatabase(String Username);
    public boolean isUsernameInDatabase(String Username);
    public boolean isPasswordCorrectForGivenUsername(String Username, String Password);
}
