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
	private String usernameOfLastEditedUserPassword = "";
	private boolean wasOldPasswordInputCorrectly = true;
	private boolean didNewPasswordAndConfirmNewPasswordMatch = true;
	
	/**
	 * Returns the ModelAndView object for the users page
	 * 
	 */
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ModelAndView getUsersPage(){
		ModelAndView mAndV = new ModelAndView("users");
		mAndV.addObject("users", this.userTestService.getUserTestBeans());
		
		
		// determine what to put into users page for 
		if (this.usernameOfLastEditedUserPassword != null){
			if (!this.usernameOfLastEditedUserPassword.isEmpty()){
				mAndV.addObject("userNameOfLastEditedUserPassword", this.usernameOfLastEditedUserPassword);
				if (this.wasOldPasswordInputCorrectly){
					if (this.didNewPasswordAndConfirmNewPasswordMatch){
						mAndV.addObject("displayChangePasswordErrorMessage", "false");
						mAndV.addObject("changePasswordErrorString", "");
					} else {
						mAndV.addObject("displayChangePasswordErrorMessage", "true");
						mAndV.addObject("changePasswordErrorString", "Sorry, but the two input new passwords do not match. Please try again.");
					}
				} else {
					// old password not input correctly
					mAndV.addObject("displayChangePasswordErrorMessage", "true");
					mAndV.addObject("changePasswordErrorString", "Sorry, but the old password was not input correctly. Please try again.");
				}
			} else {
				mAndV.addObject("displayChangePasswordErrorMessage", "false");
				mAndV.addObject("changePasswordErrorString", "");
				
			}
		} else {
			mAndV.addObject("displayChangePasswordErrorMessage", "false");
			mAndV.addObject("changePasswordErrorString", "");
			//mAndV.addObject("userNameOfLastEditedUserPassword", "");
		}
		
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
			String tableName = "usernamesandpasswords";
			
			String SQLQuery = "INSERT INTO " + tableName + " (username, password)" + "\n";
			SQLQuery += "VALUES ('" + Username + "', '" + Password_1 + "');";
			
			this.runSQLQueryWithNoReturnValue(SQLQuery);
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
	public ModelAndView changePasswordForGivenUser(
			@PathVariable String Username,
			@RequestParam(value="oldPassword") String oldPassword,
			@RequestParam(value="newPassword") String newPassword,
			@RequestParam(value="confirmNewPassword") String confirmNewPassword
			){
		
		this.usernameOfLastEditedUserPassword = Username;
		
		String oldPasswordFromDatabase = "";
		
		/*
		 * Obtain the current password from the database
		 */
		
		
		
		String tableName = "usernamesandpasswords";
		
		String SQLQuery = "SELECT password FROM " + tableName + "\n";
		SQLQuery += "WHERE username=" + "'" + Username + "'" + ";";
		
		ArrayList list = this.runSQLQueryAndGetReturnList(SQLQuery);
		
		for (Object o : list){
			oldPasswordFromDatabase = o.toString();
		}
		
		this.wasOldPasswordInputCorrectly = oldPasswordFromDatabase.equals(oldPassword);
		this.didNewPasswordAndConfirmNewPasswordMatch = newPassword.equals(confirmNewPassword);
		if (this.wasOldPasswordInputCorrectly){
			// we know the old password is correct
			System.out.println("Old password is correct");
			if (this.didNewPasswordAndConfirmNewPasswordMatch){
				// the two new passwords match - CHANGE THE PASSWORD
				
				System.out.println("Change the password");
				
				tableName = "usernamesandpasswords";
				
				SQLQuery = "";
				
				SQLQuery = "UPDATE " + tableName + "\n";
				SQLQuery += "SET password=" + "'" + newPassword + "'" + "\n";
				SQLQuery += "WHERE username=" + "'" + Username + "'" + ";";
				this.runSQLQueryWithNoReturnValue(SQLQuery);
			} else {
				// the old password is correct, but the new passwords do not match
				
				System.out.println("Two input new passwords do not match.");
			}
		} else {
			// the old password is not correct
			
			System.out.println("The old password is not correct.");
		}
		
		
		
		
		
		ModelAndView mAndV = new ModelAndView("main");
		String errorString = "";
		
		//mAndV.addObject("errorString", errorString);
		//mAndV.addObject("userName", Username);
		return mAndV;
		
	}
	
	
	
	
	
	@RequestMapping(value="/user/{Username}", method=RequestMethod.DELETE)
	public ModelAndView deleteGivenUserFromDataBase(@PathVariable String Username){
		

		String tableName = "usernamesandpasswords";
		
		String SQLQuery = "DELETE FROM " + tableName  + "\n";
		SQLQuery += "WHERE username='" + Username + "';";
		
		this.runSQLQueryWithNoReturnValue(SQLQuery);
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
    
    
    private ArrayList runSQLQueryAndGetReturnList(String SQLQuery){
    	Session session = this.userTestService.getUserDao().getSessionFactory().openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
		} catch (HibernateException ex){
			System.err.println("Exception thrown when beginning transaction: " + ex);
		}
		
		
		
		ArrayList returnValue = (ArrayList) session.createSQLQuery(SQLQuery).list();
		
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
		return returnValue;
    }
    
    private int runSQLQueryWithNoReturnValue(String SQLQuery){
    	Session session = this.userTestService.getUserDao().getSessionFactory().openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
		} catch (HibernateException ex){
			System.err.println("Exception thrown when beginning transaction: " + ex);
		}
		
		
		
		int returnValue = session.createSQLQuery(SQLQuery).executeUpdate();
		
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
		return returnValue;
    }
	
	
}
