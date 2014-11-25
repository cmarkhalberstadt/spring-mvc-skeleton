package com.xpanxion.skeleton.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xpanxion.skeleton.dao.UserDao;
import com.xpanxion.skeleton.dto.beans.UserBean;
import com.xpanxion.skeleton.dto.entity.UserEntity;

/**
 * Implementation of the user test service interface. 
 * 
 * @author mhalberstadt
 *
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;

	@Override
	public List<UserBean> getUserTestBeans() {
		List<UserEntity> userItems = this.userDao.getAllItems();
		List<UserBean> output = new ArrayList<UserBean>();
		for (UserEntity user : userItems){
			UserBean bean = new UserBean();
			bean.setID(user.getId());
			bean.setUserName(user.getUserName());
			bean.setPassword(user.getPassword());
			output.add(bean);
		}
		return output;
	}
	
	/**
	 * Sets the UserDao for this service to use
	 * 
	 * @param dao the dao for this service to use
	 */
	@Resource
	public void setUserDao(UserDao dao){
		this.userDao = dao;
	}
	
	/**
	 * returns the UserDao object for this service.
	 * 
	 * @return the UserDao Object for this service
	 */
	@Override
	public UserDao getUserDao(){
		return this.userDao;
	}

}
