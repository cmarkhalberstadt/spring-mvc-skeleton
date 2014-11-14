package com.xpanxion.skeleton.controllers;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xpanxion.skeleton.dto.beans.UserBean;
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
	@RequestMapping("**/users")
	public ModelAndView getUsersPage(){
		ModelAndView mAndV = new ModelAndView("users");
		mAndV.addObject("users", this.userTestService.getUserTestBeans());
		return mAndV;
	}
	
	/**
	 * Returns the ModelAndView object for the users authentication page
	 * 
	 */
	@RequestMapping("**/usersAuthentication")
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
	@RequestMapping("**/usersAuthentication_target_users")
	public ModelAndView getUsersAuthenticationPage_target_users(){
		this.targetPageAfterUserAuthentication = "users";
		return this.getUsersAuthenticationPage();
	}
	
	
	/**
	 * Returns the ModelAndView object for the users authentication page with target page home
	 * 
	 */
	@RequestMapping("**/usersAuthentication_target_home")
	public ModelAndView getUsersAuthenticationPage_target_home(){
		this.targetPageAfterUserAuthentication = "home";
		return this.getUsersAuthenticationPage();
	}
	
	@RequestMapping(value="**/usersAuthentication", method=RequestMethod.POST)
	public ModelAndView userNameAndPasswordSubmit(@RequestParam String Username,@RequestParam String Password, Model m){
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
