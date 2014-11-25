package com.xpanxion.skeleton.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import com.xpanxion.skeleton.dto.beans.UserBean;
import com.xpanxion.skeleton.dto.entity.UserEntity;
import com.xpanxion.skeleton.service.UserService;

/**
 * Controller for the Users page
 * 
 * @author mhalberstadt
 *
 */
@Controller
public class UsersController {
	
	private UserService userTestService;
	private String targetPageAfterUserAuthentication = "users";
	
	// variables used for displaying error messages on changing passwords
	private String usernameOfLastEditedUserPassword_changePassword = "";
	private boolean wasOldPasswordInputCorrectly_changePassword = true;
	private boolean didNewPasswordAndConfirmNewPasswordMatch_changePassword = true;
	private boolean displayNewPasswordErrorMessage_changePassword = false;
	
	// variables used for displaying error messages on adding users
	private boolean wasInputUserNameADuplicate_addUser = false;
	private boolean didNewPasswordAndConfirmNewPasswordMatch_addUser = true;
	private boolean displayErrorMessage_addUser = false;
	
	/**
	 * Returns the ModelAndView object for the users page
	 * 
	 */
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ModelAndView getUsersPage(){
		ModelAndView mAndV = new ModelAndView("users");
		mAndV.addObject("users", this.userTestService.getUserTestBeans());
		
		mAndV = this.setErrorMessageOnUsersPage_changePassword(mAndV);
		mAndV = this.setErrorMessageOnUsersPage_addUser(mAndV);
		
		return mAndV;
	}
	
	private ModelAndView setErrorMessageOnUsersPage_addUser(ModelAndView mAndV){
		// set the default values
		mAndV.addObject("displayAddNewUserErrorMessage_addNewUser", "false");
		mAndV.addObject("usernameErrorMessage_addUser", "");
		mAndV.addObject("passwordErrorMessage_addUser", "");
		
		// regular logic
		if (this.displayErrorMessage_addUser){
			mAndV.addObject("displayAddNewUserErrorMessage_addNewUser", "true");
			if (this.wasInputUserNameADuplicate_addUser){
				mAndV.addObject("usernameErrorMessage_addUser", "Username not available.");
				mAndV.addObject("passwordErrorMessage_addUser", "");
			} else {
				if (this.didNewPasswordAndConfirmNewPasswordMatch_addUser){
					// if we get to this point, it means that the password field is empty.
					mAndV.addObject("usernameErrorMessage_addUser", "");
					mAndV.addObject("passwordErrorMessage_addUser", "Please enter a valid password.");
				} else {
					// password and confirm password did not match
					mAndV.addObject("usernameErrorMessage_addUser", "");
					mAndV.addObject("passwordErrorMessage_addUser", "Confirmation password did not match.");
				}
			}
			this.displayErrorMessage_addUser = false;
		}
		
		return mAndV;
	}
	
	private ModelAndView setErrorMessageOnUsersPage_changePassword(ModelAndView mAndV){
		// default values
		mAndV.addObject("displayChangePasswordErrorMessage_changePassword", "false");
		mAndV.addObject("userNameOfLastEditedUserPassword_changePassword", this.usernameOfLastEditedUserPassword_changePassword);
		mAndV.addObject("oldPasswordIncorrectErrorMessage_changePassword", "");
		mAndV.addObject("newPasswordConfirmErrorMessage_changePassword", "");
		
		// regular logic
		if (this.displayNewPasswordErrorMessage_changePassword){
			if (this.wasOldPasswordInputCorrectly_changePassword){
				if (this.didNewPasswordAndConfirmNewPasswordMatch_changePassword){
					mAndV.addObject("displayChangePasswordErrorMessage_changePassword", "false");
				} else {
					mAndV.addObject("displayChangePasswordErrorMessage_changePassword", "true");
					mAndV.addObject("newPasswordConfirmErrorMessage_changePassword", "Confirmation does not match.");
				}
			} else {
				mAndV.addObject("displayChangePasswordErrorMessage_changePassword", "true");
				mAndV.addObject("oldPasswordIncorrectErrorMessage_changePassword", "Incorrect old password.");
			}
			this.usernameOfLastEditedUserPassword_changePassword = "";
			this.displayNewPasswordErrorMessage_changePassword = false;
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
	
	/*
	 * 
	 * TODO - Utilize a @RequestBody to send data for adding a new user
	 */
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public ModelAndView addANewUserToTheTableOfUsers(
			@RequestParam String UsernameToAdd,
			@RequestParam String passwordToAdd, 
			@RequestParam String confirmPasswordToAdd 
			){
		// set the defaults for the error message handling
		this.displayErrorMessage_addUser = false;
		this.wasInputUserNameADuplicate_addUser = false;
		this.didNewPasswordAndConfirmNewPasswordMatch_addUser = true;
		
		// check to see if username is already in database
		if (this.userTestService.getUserDao().isUsernameInDatabase(UsernameToAdd)){
			this.displayErrorMessage_addUser = true;
			this.wasInputUserNameADuplicate_addUser = true;
			this.didNewPasswordAndConfirmNewPasswordMatch_addUser = false;
			return this.getUsersPage();
		}
		
		// validate the input user information
		if (passwordToAdd.equals(confirmPasswordToAdd)){
			
			if (passwordToAdd.isEmpty()){
				this.displayErrorMessage_addUser = true;
				this.wasInputUserNameADuplicate_addUser = false;
				this.didNewPasswordAndConfirmNewPasswordMatch_addUser = true;
				return this.getUsersPage();
			}
			// add new user to the database 
			this.userTestService.getUserDao().addUserToDatabase(UsernameToAdd, passwordToAdd);
			return this.getUsersPage();
		} else {
			// password input and password confirm did not match
			this.displayErrorMessage_addUser = true;
			this.wasInputUserNameADuplicate_addUser = false;
			this.didNewPasswordAndConfirmNewPasswordMatch_addUser = false;
			return this.getUsersPage();
		}
		
		
	}
	
	
	
	
	
	/*
	 * TODO - utilize the @RequestBody here
	 */
	@RequestMapping(value="/user/{Username}", method=RequestMethod.PUT)
	@ResponseBody
	public String changePasswordForGivenUser(
			@PathVariable String Username,
			@RequestParam(value="oldPassword") String oldPassword,
			@RequestParam(value="newPassword") String newPassword,
			@RequestParam(value="confirmNewPassword") String confirmNewPassword
			){
		
		this.usernameOfLastEditedUserPassword_changePassword = Username;
		
		String oldPasswordFromDatabase = 
				this.userTestService.getUserDao().getUserWithUsername(Username).getPassword();
		
		/*
		 * Obtain the current password from the database
		 */
		
		this.wasOldPasswordInputCorrectly_changePassword = oldPasswordFromDatabase.equals(oldPassword);
		this.didNewPasswordAndConfirmNewPasswordMatch_changePassword = newPassword.equals(confirmNewPassword);
		if (this.wasOldPasswordInputCorrectly_changePassword){
			// we know the old password is correct
			//System.out.println("Old password is correct");
			if (this.didNewPasswordAndConfirmNewPasswordMatch_changePassword){
				// the two new passwords match - CHANGE THE PASSWORD
				
				//System.out.println("Change the password");
				this.displayNewPasswordErrorMessage_changePassword = false;
				this.usernameOfLastEditedUserPassword_changePassword = "";
				// implement USERS DAO
				this.userTestService.getUserDao().changePasswordOfUser(Username, newPassword);
			} else {
				// the old password is correct, but the new passwords do not match
				this.displayNewPasswordErrorMessage_changePassword = true;
				//System.out.println("Two input new passwords do not match.");
			}
		} else {
			// the old password is not correct
			this.displayNewPasswordErrorMessage_changePassword = true;
			//System.out.println("The old password is not correct.");
		}
		
		
		
		
		
		ModelAndView mAndV = new ModelAndView("main");
		//String errorString = "";
		
		//mAndV.addObject("errorString", errorString);
		//mAndV.addObject("userName", Username);
		//return mAndV;
		return "{\"name\":\"testName\", \"password\":\"testPassword\"}";
	}
	
	
	@RequestMapping(value="/user/{Username}", method=RequestMethod.DELETE)
	public ModelAndView deleteGivenUserFromDataBase(@PathVariable String Username){
		

		this.userTestService.getUserDao().deleteUserFromDatabase(Username);
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
	
	
	
	@RequestMapping(value="/user/{Username}", method=RequestMethod.GET)
	public ModelAndView userNameAndPasswordSubmitWithPathVariable(@PathVariable String Username,@RequestParam("Password") String Password, Model m){
		return this.getModelAndViewToReturnBasedOnUserNameAndPasswordSubmission(Username, Password);
	}
	
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public ModelAndView userNameAndPasswordSubmitWithRequestParam(@RequestParam String Username,@RequestParam String Password, Model m){
		return this.getModelAndViewToReturnBasedOnUserNameAndPasswordSubmission(Username, Password);
	}
	
	
	private ModelAndView getModelAndViewToReturnBasedOnUserNameAndPasswordSubmission(
			String userName,
			String Password){
		ModelAndView mAndV = null;
		String errorString = null;
		
        if (this.userTestService.getUserDao().isUsernameInDatabase(userName)){
        	if (this.userTestService.getUserDao().isPasswordCorrectForGivenUsername(userName, Password)){
        		if (this.targetPageAfterUserAuthentication.equals("users")){
        			return this.getUsersPage();
        		}
        		mAndV = new ModelAndView(this.targetPageAfterUserAuthentication);
				return mAndV;
        	} else {
        		errorString = "This password does not match the given user name. Please try again.";
				mAndV = new ModelAndView("usersAuthentication");
				mAndV.addObject("errorString", errorString);
				return mAndV;
        	}
        } else {
        	errorString = "This user name is not recognized. Please try again.";
			mAndV = new ModelAndView("usersAuthentication");
			mAndV.addObject("errorString", errorString);
			return mAndV;
        }
	}
	
	/**
     * Sets the user test service for this controller
     * 
     * @param service the  user test service to use in this controller. 
     */
    @Resource
    public void setUserTestService(UserService service){
    	this.userTestService = service;
    }
    
    /**
     * Returns the User Test Service
     * 
     * @return Returns the User Test Service
     */
    public UserService getUserTestService(){
    	return this.userTestService;
    }
    
    
    
    
	
	
}
