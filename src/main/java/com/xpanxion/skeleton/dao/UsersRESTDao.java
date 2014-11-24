package com.xpanxion.skeleton.dao;

import org.hibernate.SessionFactory;

public interface UsersRESTDao {
	Object GET(String url);
	Object GET(String url, String RequestParam);
	Object DELETE(String url);
	Object PUT(String url, String RequestParam);
	Object POST(String url, String RequestParam);
	
	/**
     * Returns the SessionFactory Object for this UserRESTDao
     * 
     * @return the SessionFactory Object for this UserRESTDao
     */
    public SessionFactory getSessionFactory();
}
