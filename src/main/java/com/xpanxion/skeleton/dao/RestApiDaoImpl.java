package com.xpanxion.skeleton.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import javax.annotation.Resource;







import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.xpanxion.skeleton.dto.beans.ChangeUserPasswordBean;
import com.xpanxion.skeleton.dto.beans.UserBean;
import com.xpanxion.skeleton.dto.entity.UserEntity;

/**
 * Implementation of the UsersRESTDao interface. 
 * 
 * @author mhalberstadt
 *
 */
@Repository
public class RestApiDaoImpl implements RestApiDao {
	
	
	private RestTemplate restTemplate;
	/*
	//@Autowired
	public RestApiDaoImpl(RestTemplate template){
		//this.restTemplate = template;
	}
	
	
	/**
	 * Sets the RestTemplate
	 * @param restTemplate - the RestTemplate to be set
	 */
	
	@Resource
	public void setRestTemplate(RestTemplate restTemplate){
		this.restTemplate = restTemplate;
	}
	
	
	@Override
	public List<UserEntity> getAllItems() {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String url = "http://localhost:8080/api/users";
		ArrayList output = this.restTemplate.getForObject(url, ArrayList.class);
		ArrayList<UserEntity> retval = new ArrayList<UserEntity>();
		
		for (Object o: output){
			UserBean b = mapper.convertValue(o, UserBean.class);
			UserEntity u = new UserEntity();
			u.setId(b.getId());
			u.setPassword(b.getPassword());
			u.setUsername(b.getUsername());
			retval.add(u);
		}
		
		return retval;
		
	}

	@Override
	public UserBean getUserWithUsername(String Username) {
		String url = "http://localhost:8080/api/user/" + Username;
		return this.restTemplate.getForObject(url, UserBean.class);
	}

	@Override
	public void changePasswordOfUser(String Username, String newPassword) {
		String url = "http://localhost:8080/api/user/" + Username;
		ChangeUserPasswordBean b = new ChangeUserPasswordBean();
		b.setNewpassword(newPassword);
		UserBean fromDB = this.getUserWithUsername(Username);
		b.setOldpassword(fromDB.getPassword());
		this.restTemplate.put(url, b);
	}

	@Override
	public void addUserToDatabase(String Username, String Password) {
		String url = "http://localhost:8080/api/user";
		UserBean toAdd = new UserBean();
		toAdd.setPassword(Password);
		toAdd.setUsername(Username);
		this.restTemplate.postForEntity(url, toAdd, UserBean.class);
	}

	@Override
	public void deleteUserFromDatabase(String Username) {
		String url = "http://localhost:8080/api/user/" + Username;
		this.restTemplate.delete(url);
	}

	@Override
	public boolean isUsernameInDatabase(String Username) {
		UserBean fromDatabase = this.getUserWithUsername(Username);
		if (fromDatabase == null){
			return false;
		} else {
			String passwordFromDatabase = fromDatabase.getPassword();
			if (passwordFromDatabase.isEmpty()){
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public boolean isPasswordCorrectForGivenUsername(String Username,
			String Password) {
		UserBean fromDatabase = this.getUserWithUsername(Username);
		if (fromDatabase == null){
			return false;
		} else {
			String passwordFromDatabase = fromDatabase.getPassword();
			if (passwordFromDatabase.isEmpty()){
				return false;
			} else {
				if (passwordFromDatabase.equals(Password)){
					return true;
				} else {
					return false;
				}
			}
		}
	}

	
	
}
