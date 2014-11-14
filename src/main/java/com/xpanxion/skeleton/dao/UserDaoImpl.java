package com.xpanxion.skeleton.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.xpanxion.skeleton.dto.entity.UserEntity;

/**
 * Implementation of the UserDao interface. 
 * 
 * @author mhalberstadt
 *
 */
@Repository
public class UserDaoImpl implements UserDao {
	
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<UserEntity> getAllItems() {
		return this.sessionFactory.getCurrentSession().getNamedQuery("userNamesAndPasswords.getAll").list();
	}
	
	/**
     * Sets the session factory for this dao to use. 
     * 
     * @param factory the session factory for this dao.
     */
    @Resource
    public void setSesionFactory(SessionFactory factory) {
        this.sessionFactory = factory;
    }
    
    /**
     * Returns the Session Factory being used by the dao
     * 
     * @return the session factory being used by this dao
     */
    public SessionFactory getSessionFactory(){
    	return this.sessionFactory;
    }

}
