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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
//@Component
public class UsersRESTDaoImpl {
	
	
	private RestTemplate restTemplate;
	
	//@Autowired
	public UsersRESTDaoImpl(RestTemplate template){
		this.restTemplate = template;
	}
	
	/**
	 * Sets the RestTemplate
	 * @param restTemplate - the RestTemplate to be set
	 */
	public void setRestTemplate(RestTemplate restTemplate){
		this.restTemplate = restTemplate;
	}
	
	public RestTemplate getRestTemplate(){
		return this.restTemplate;
	}

	
	public List<UserEntity> getAllItems() {
		String url = "/api/users";
		return (List<UserEntity>)this.restTemplate.getForObject(url, List.class);
	}

	
	public UserBean getUserWithUsername(String Username) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void changePasswordOfUser(String Username, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	
	public void addUserToDatabase(String Username, String Password) {
		// TODO Auto-generated method stub
		
	}

	
	public void deleteUserFromDatabase(String Username) {
		// TODO Auto-generated method stub
		
	}

	
	public boolean isUsernameInDatabase(String Username) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean isPasswordCorrectForGivenUsername(String Username,
			String Password) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
