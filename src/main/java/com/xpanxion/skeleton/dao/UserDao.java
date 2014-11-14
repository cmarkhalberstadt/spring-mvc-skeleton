package com.xpanxion.skeleton.dao;

import java.util.List;

import com.xpanxion.skeleton.dto.entity.UserEntity;

/**
 * Interface for the User Dao
 * 
 * @author mhalberstadt
 *
 */
public interface UserDao {
	/**
     * Returns all of the User entities.
     * 
     * @return all of the User entities. 
     */
    List<UserEntity> getAllItems();
}
