package com.xpanxion.skeleton.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import javax.annotation.Resource;





import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;



import org.hibernate.cfg.Configuration;
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
		//return this.sessionFactory.getCurrentSession().getNamedQuery("userNamesAndPasswords.getAll").list();
		
		
		
		Session session = null;
		Transaction tx = null;
		ArrayList<UserEntity> retval = new ArrayList<UserEntity>();
		
		try{
			session = this.sessionFactory.openSession();
		} catch (HibernateException ex){
			System.out.println("Exception thrown while opening session: " + ex);
		}
		
		try {
			tx = session.beginTransaction();
		} catch (HibernateException ex){
			System.out.println("Expection thrown while beginning transaction: " + ex);
		}
		
		retval = (ArrayList<UserEntity>) session.createQuery("From UserEntity").list();
		
		try {
			tx.commit();
		} catch (HibernateException ex){
			System.out.println("Expection thrown while committing transaction: " + ex);
			if (tx != null){
				tx.rollback();
			}
		}
		
		
		try {
			session.close();
		} catch (HibernateException ex){
			System.out.println("Exception thrown while closing session: " + ex);
		}
		
		Collections.sort(retval);
		
		return retval;
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
