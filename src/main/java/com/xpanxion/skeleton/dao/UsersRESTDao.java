package com.xpanxion.skeleton.dao;

import org.hibernate.SessionFactory;

public interface UsersRESTDao {
	
	/**
     * Returns the SessionFactory Object for this UserRESTDao
     * 
     * @return the SessionFactory Object for this UserRESTDao
     */
    public SessionFactory getSessionFactory();
}
