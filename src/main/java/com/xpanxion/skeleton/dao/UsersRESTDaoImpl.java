package com.xpanxion.skeleton.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.xpanxion.skeleton.dto.beans.UserBean;
import com.xpanxion.skeleton.dto.entity.UserEntity;

/**
 * Implementation of the UsersRESTDao interface. 
 * 
 * @author mhalberstadt
 *
 */
@Repository
public class UsersRESTDaoImpl implements UserDao {
	private SessionFactory sessionFactory;
	
	
	private RestTemplate restTemplate;

	@Override
	public List<UserEntity> getAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBean getUserWithUsername(String Username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePasswordOfUser(String Username, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUserToDatabase(String Username, String Password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUserFromDatabase(String Username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsernameInDatabase(String Username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPasswordCorrectForGivenUsername(String Username,
			String Password) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
