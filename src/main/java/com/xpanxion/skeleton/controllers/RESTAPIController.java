package com.xpanxion.skeleton.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xpanxion.skeleton.dto.beans.ChangeUserPasswordBean;
import com.xpanxion.skeleton.dto.beans.TestBean;
import com.xpanxion.skeleton.dto.beans.UserBean;
import com.xpanxion.skeleton.dto.entity.UserEntity;
import com.xpanxion.skeleton.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * RESTFul Webservice Controller
 * @author mhalberstadt
 *
 */
@Controller
public class RESTAPIController {
	private UserService userService;
	
	
	/**
	 * Returns a list of user beans as a JSON object
	 * @return
	 */
	@RequestMapping(value="/api/users", method=RequestMethod.GET)
	@ResponseBody
	public List<UserBean> getAllUsers_ReturnAsJSONResponseBody(){
		return this.userService.getUserBeans();
	}
	
	
	/**
	 * Returns a specific user's information as a JSON string based on the input username
	 * @param Username String of the username to be found in the list and have user info returned for
	 * @return String containing JSON information 
	 */
	@RequestMapping(value = "/api/user/{Username}", method=RequestMethod.GET)
	@ResponseBody
	public UserBean getSpecificUserByPathVariable(@PathVariable String Username, HttpServletResponse response){
		return this.userService.getUserWithUsername(Username);
	}
	
	
	
	/**
	 * Returns a specific user's information as a JSON string based on the input username
	 * @param Username String of the username to be found in the list and have user info returned for
	 * @return String containing JSON information 
	 */
	@RequestMapping(value="/api/user", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public UserBean getSpecificUserByRequestParam(@RequestParam String Username, HttpServletResponse response){
		return this.userService.getUserWithUsername(Username);
	}
	
	/*
	 * Working
	 */
	@RequestMapping(value="/api/user/{Username}", method=RequestMethod.DELETE, produces="application/json")
	@ResponseBody
	public void deleteGivenUser(@PathVariable String Username){
		this.userService.deleteUserFromDatabase(Username);
	}
	
	/*
	 * Working
	 */
	@RequestMapping(value="/api/user/{Username}", method=RequestMethod.PUT, produces="application/json")
	@ResponseBody
	public UserBean changePasswordForGivenUser(@PathVariable String Username, @RequestBody ChangeUserPasswordBean changeUserPasswordBean){
		UserBean fromDatabase = this.userService.getUserWithUsername(Username);
		if (fromDatabase.getPassword().equals(changeUserPasswordBean.getOldpassword())){
			// old password matches the password stored in the database
			this.userService.changePasswordOfUser(Username, changeUserPasswordBean.getNewpassword());
			return this.userService.getUserWithUsername(Username);
		} else {
			return null;
		}
	}
	
	
	/*
	 * Working
	 */
	@RequestMapping(value= "/api/user", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody UserBean addAUserToTheDatabase(@RequestBody UserBean request){
		UserBean checkFromDatabase = this.userService.getUserWithUsername(request.getUsername());
		if (checkFromDatabase != null){
			if (!checkFromDatabase.getUsername().isEmpty()){
				if (checkFromDatabase.getUsername().equals(request.getUsername())){
					return null;
				}
			}
		}
		this.userService.addUserToDatabase(request.getUsername(), request.getPassword());
		return this.userService.getUserWithUsername(request.getUsername());
	}
	
	
	
	
	
	/**
     * Sets the user test service for this controller
     * 
     * @param service the  user test service to use in this controller. 
     */
    @Resource
    public void setUserTestService(UserService service){
    	this.userService = service;
    }
    
    /**
     * Returns the User Test Service
     * 
     * @return Returns the User Test Service
     */
    public UserService getUserTestService(){
    	return this.userService;
    }
	
	
}
