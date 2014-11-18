package com.xpanxion.skeleton.controllers;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import com.xpanxion.skeleton.dto.beans.UserBean;
import com.xpanxion.skeleton.dto.entity.UserEntity;
import com.xpanxion.skeleton.service.UserTestService;

/**
 * Controller for the Users page
 * 
 * @author mhalberstadt
 *
 */
@Controller
public class UsersController {
	
	private UserTestService userTestService;
	private String targetPageAfterUserAuthentication = "users";
	
	/**
	 * Returns the ModelAndView object for the users page
	 * 
	 */
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ModelAndView getUsersPage(){
		ModelAndView mAndV = new ModelAndView("users");
		mAndV.addObject("users", this.userTestService.getUserTestBeans());
		return mAndV;
	}
	
	/**
	 * Returns the ModelAndView object for the users page
	 * 
	 */
	@RequestMapping(value="/addNewUserPage", method=RequestMethod.GET)
	public ModelAndView getNewUserPage(){
		ModelAndView mAndV = new ModelAndView("addNewUserPage");
		mAndV.addObject("users", this.userTestService.getUserTestBeans());
		String errorString="";
		mAndV.addObject("errorString", errorString);
		return mAndV;
	}
	
	@RequestMapping(value="/addNewUser", method=RequestMethod.POST)
	public ModelAndView addANewUserToTheTableOfUsers(
			@RequestParam String Username,
			@RequestParam String Password_1, 
			@RequestParam String Password_2 
			){
		
		// validate the input user information
		if (Password_1.equals(Password_2)){
			// add new user to the database
			
			Session session = this.userTestService.getUserDao().getSessionFactory().openSession();
			Transaction tx = null;
			
			try {
				tx = session.beginTransaction();
			} catch (HibernateException ex){
				System.err.println("Exception thrown when beginning transaction: " + ex);
			}
			
			String tableName = "usernamesandpasswords";
			
			String SQLQuery = "INSERT INTO " + tableName + " (username, password)" + "\n";
			SQLQuery += "VALUES ('" + Username + "', '" + Password_1 + "');";
			
			session.createSQLQuery(SQLQuery).executeUpdate();
			
			try {
				if (tx != null){
					tx.commit();
				}
			} catch (HibernateException ex){
				System.err.println("Exception thrown when committing transaction: " + ex);
				if (tx != null){
					tx.rollback();
				}
			} finally {
				session.close();
			}
			return this.getUsersPage();
		} else {
			ModelAndView mAndV = new ModelAndView("addNewUserPage");
			mAndV.addObject("users", this.userTestService.getUserTestBeans());
			String errorString="Passwords did not match. Please put your information in again.";
			mAndV.addObject("errorString", errorString);
			return mAndV;
		}
		
		
	}
	
	@RequestMapping(value="/user/{Username}", method=RequestMethod.PUT)
	public String getChangePasswordPage(@PathVariable String Username){
		System.out.println("Put method called with " + Username + " as Username.");
		ModelAndView mAndV = new ModelAndView("changePassword");
		String errorString = "";
		mAndV.addObject("errorString", errorString);
		mAndV.addObject("userName", Username);
		//return mAndV;
		return "/main";
	}
	
	
	
	
	@RequestMapping(value="/user/{Username}", method=RequestMethod.DELETE)
	public ModelAndView deleteGivenUserFromDataBase(@PathVariable String Username){
		Session session = this.userTestService.getUserDao().getSessionFactory().openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
		} catch (HibernateException ex){
			System.err.println("Exception thrown when beginning transaction: " + ex);
		}
		
		String tableName = "usernamesandpasswords";
		
		String SQLQuery = "DELETE FROM " + tableName  + "\n";
		SQLQuery += "WHERE username='" + Username + "';";
		
		session.createSQLQuery(SQLQuery).executeUpdate();
		
		try {
			if (tx != null){
				tx.commit();
			}
		} catch (HibernateException ex){
			System.err.println("Exception thrown when committing transaction: " + ex);
			if (tx != null){
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return this.getUsersPage();
	}
	
	
	public ModelAndView getUsersAuthenticationPage(){
		String errorString = "";
		ModelAndView mAndV = new ModelAndView("usersAuthentication");
		mAndV.addObject("errorString", errorString);
		return mAndV;
	}
	
	/**
	 * Returns the ModelAndView object for the users authentication page with target page users
	 * 
	 */
	@RequestMapping(value="/usersAuthentication/{targetPage}", method=RequestMethod.GET)
	public ModelAndView getUsersAuthenticationPage_target_users(@PathVariable String targetPage){
		System.out.println("Correct Method " + targetPage );
		this.targetPageAfterUserAuthentication = targetPage;
		return this.getUsersAuthenticationPage();
	}
	
	
	// TODO - Fix me - Currently not working
	//@RequestMapping(value="/user/{Username}", method=RequestMethod.GET)
	public ModelAndView userNameAndPasswordSubmitWithPathVariable(@PathVariable String Username,@RequestParam("Password") String Password, Model m){
		return this.getModelAndViewToReturnBasedOnUserNameAndPasswordSubmission(Username, Password);
	}
	
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public ModelAndView userNameAndPasswordSubmitWithRequestParam(@RequestParam String Username,@RequestParam String Password, Model m){
		return this.getModelAndViewToReturnBasedOnUserNameAndPasswordSubmission(Username, Password);
	}
	
	
	
	private ModelAndView getModelAndViewToReturnBasedOnUserNameAndPasswordSubmission(
			String Username,
			String Password
			){
		String errorString = "";
		ModelAndView mAndV = null;
		/*
		 * Test the user name and password here
		 */
		int userNameArrayListIndex = this.getUserNameArrayListIndexForGivenUserName(Username);
		if (userNameArrayListIndex != -1){
			// this username is in the database
			if (this.doesPasswordMatchForGivenArrayListIndex(userNameArrayListIndex, Password)){
				// the user name and password match and we should grant access to the users page
				if (this.targetPageAfterUserAuthentication.equals("users")){
					return this.getUsersPage();
				} else {
					mAndV = new ModelAndView(this.targetPageAfterUserAuthentication);
					return mAndV;
				}
			} else {
				// the user name and password do not match
				errorString = "This password does not match the given user name. Please try again.";
				mAndV = new ModelAndView("usersAuthentication");
				mAndV.addObject("errorString", errorString);
				return mAndV;
			}
		} else {
			// This user name is not in the database
			errorString = "This user name is not recognized. Please try again.";
			mAndV = new ModelAndView("usersAuthentication");
			mAndV.addObject("errorString", errorString);
			return mAndV;
		}
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	private int getUserNameArrayListIndexForGivenUserName(String username){
		ArrayList<UserBean> list = (ArrayList<UserBean>)this.userTestService.getUserTestBeans();
		int index = 0;
		for (UserBean b : list){
			if (username.equals(b.getUserName())){
				return index;
			} else {
				index++;
			}
		}
		// return -1 to signify that the given username was not found
		return -1;
	}
	
	private boolean doesPasswordMatchForGivenArrayListIndex(int index, String password){
		ArrayList<UserBean> list = (ArrayList<UserBean>)this.userTestService.getUserTestBeans();
		UserBean bean = list.get(index);
		return password.equals(bean.getPassword());
	}
	
	
	
	
	
	/**
     * Sets the user test service for this controller
     * 
     * @param service the  user test service to use in this controller. 
     */
    @Resource
    public void setUserTestService(UserTestService service){
    	this.userTestService = service;
    }
    
    /**
     * Returns the User Test Service
     * 
     * @return Returns the User Test Service
     */
    public UserTestService getUserTestService(){
    	return this.userTestService;
    }
	
	
}
