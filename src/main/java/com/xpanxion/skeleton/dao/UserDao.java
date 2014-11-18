package com.xpanxion.skeleton.dao;

import java.util.List;

import org.hibernate.SessionFactory;

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
    
    /**
     * Returns the SessionFactory Object for this UserDao
     * 
     * @return the SessionFactory Object for this UserDao
     */
    public SessionFactory getSessionFactory();
}
