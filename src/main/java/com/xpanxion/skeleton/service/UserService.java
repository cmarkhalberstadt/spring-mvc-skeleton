package com.xpanxion.skeleton.service;

import java.util.List;




import com.xpanxion.skeleton.dao.UserDao;
import com.xpanxion.skeleton.dto.beans.UserBean;
import com.xpanxion.skeleton.dto.entity.UserEntity;

/**
 * A service dealing with User Test Beans
 * 
 * @author mhalberstadt
 *
 */

public interface UserService {
	/**
     * Return all of the available user test beans. 
     * 
     * @return all of the user test beans
     */
    List<UserBean> getUserBeans();
    
    public UserBean getUserWithUsername(String Username);
    public void changePasswordOfUser(String Username, String newPassword);
    public void addUserToDatabase(String Username, String Password);
    public void deleteUserFromDatabase(String Username);
    public boolean isUsernameInDatabase(String Username);
    public boolean isPasswordCorrectForGivenUsername(String Username, String Password);
}
