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
	
	private UserService userService;
	private String targetPageAfterUserAuthentication = "users";
	
	
	/**
	 * Returns the ModelAndView object for the users page
	 * 
	 */
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ModelAndView getUsersPage(){
		ModelAndView mAndV = new ModelAndView("users");
		mAndV.addObject("users", this.userService.getUserBeans());
		return mAndV;
	}
	
	
	
	
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	@ResponseBody
	public String addANewUserToTheTableOfUsers(
			@RequestParam String UsernameToAdd,
			@RequestParam String passwordToAdd
			){
		boolean booleanReturnValue = this.userService.validateAndAddNewUserToDatabase(UsernameToAdd, passwordToAdd);
		if (booleanReturnValue){
			return "true";
		} else {
			return "false";
		}
	}
	
	
	
	
	
	@RequestMapping(value="/user/{Username}", method=RequestMethod.PUT)
	@ResponseBody
	public String changePasswordForGivenUser(
			@PathVariable String Username,
			@RequestParam(value="oldPassword") String oldPassword,
			@RequestParam(value="newPassword") String newPassword
			){
		boolean booleanReturnValue =  this.userService.validateAndChangeUserPassword(Username, oldPassword, newPassword);
		if (booleanReturnValue){
			return "true";
		} else {
			return "false";
		}
	}
	
	
	@RequestMapping(value="/user/{Username}", method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteGivenUserFromDataBase(@PathVariable String Username){
		boolean booleanReturnValue = this.userService.deleteUserFromDatabase(Username);
		if (booleanReturnValue){
			return "true";
		} else {
			return "false";
		}
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
		
        if (this.userService.isUsernameInDatabase(userName)){
        	if (this.userService.isPasswordCorrectForGivenUsername(userName, Password)){
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
    public void setUserService(UserService service){
    	this.userService = service;
    }
    
    /**
     * Returns the User Test Service
     * 
     * @return Returns the User Test Service
     */
    public UserService getUserService(){
    	return this.userService;
    }
    
    
    
    
	
	
}
