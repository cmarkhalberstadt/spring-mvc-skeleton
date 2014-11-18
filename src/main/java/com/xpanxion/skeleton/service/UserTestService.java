package com.xpanxion.skeleton.service;

import java.util.List;



import com.xpanxion.skeleton.dao.UserDao;
import com.xpanxion.skeleton.dto.beans.UserBean;

/**
 * A service dealing with User Test Beans
 * 
 * @author mhalberstadt
 *
 */

public interface UserTestService {
	/**
     * Return all of the available user test beans. 
     * 
     * @return all of the user test beans
     */
    List<UserBean> getUserTestBeans();
    
    /**
     * Return the UserDao Object for this service
     * 
     * @return the UserDao Object for this service
     */
    public UserDao getUserDao();
}
