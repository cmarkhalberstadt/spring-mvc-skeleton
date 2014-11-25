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
	public List<UserBean> getUserBeans() {
		List<UserEntity> userItems = this.userDao.getAllItems();
		List<UserBean> output = new ArrayList<UserBean>();
		for (UserEntity user : userItems){
			UserBean bean = new UserBean();
			bean.setId(user.getId());
			bean.setUsername(user.getUsername());
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

	@Override
	public UserBean getUserWithUsername(String Username) {
		return this.userDao.getUserWithUsername(Username);
	}

	@Override
	public void changePasswordOfUser(String Username, String newPassword) {
		this.userDao.changePasswordOfUser(Username, newPassword);
	}

	@Override
	public void addUserToDatabase(String Username, String Password) {
		this.userDao.addUserToDatabase(Username, Password);
	}

	@Override
	public void deleteUserFromDatabase(String Username) {
		this.userDao.deleteUserFromDatabase(Username);
	}

	@Override
	public boolean isUsernameInDatabase(String Username) {
		return this.userDao.isUsernameInDatabase(Username);
	}

	@Override
	public boolean isPasswordCorrectForGivenUsername(String Username,
			String Password) {
		return this.userDao.isPasswordCorrectForGivenUsername(Username, Password);
	}
	
	
	

}
