package com.xpanxion.skeleton.controllers;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xpanxion.skeleton.dto.beans.UserBean;
import com.xpanxion.skeleton.service.UserTestService;

/**
 * RESTFul Webservice Controller
 * @author mhalberstadt
 *
 */
@Controller
public class RESTAPIController {
	private UserTestService userTestService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<UserBean> getAllUsers(){
		return (ArrayList<UserBean>)this.userTestService.getUserTestBeans();
	}
	
	/**
	 * Returns a specific user's information as a JSON string based on the input username
	 * @param Username String of the username to be found in the list and have user info returned for
	 * @return String containing JSON information 
	 */
	@RequestMapping( method=RequestMethod.GET)
	@ResponseBody
	public String getSpecificUserByRequestParam(@RequestParam String Username){
		ArrayList<UserBean> list = (ArrayList<UserBean>)this.userTestService.getUserTestBeans();
		int numEntries = list.size();
		for (int i = 0; i<numEntries; i++){
			UserBean bean = list.get(i);
			if (bean.getUserName().equals(Username)){
				return this.convertUserBeanToJSON(bean);
			}
		}
		// this username is not in the list - return the empty string
		return "";
	}
	
	
	/**
	 * Returns a specific user's information as a JSON string based on the input username
	 * @param Username String of the username to be found in the list and have user info returned for
	 * @return String containing JSON information 
	 */
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public String getSpecificUserByPathVariable(@PathVariable String Username){
		ArrayList<UserBean> list = (ArrayList<UserBean>)this.userTestService.getUserTestBeans();
		int numEntries = list.size();
		for (int i = 0; i<numEntries; i++){
			UserBean bean = list.get(i);
			if (bean.getUserName().equals(Username)){
				return this.convertUserBeanToJSON(bean);
			}
		}
		// this username is not in the list - return the empty string
		return "";
	}
	
	
	
	/**
	 * Converts a UserBean Object into JSON output as a String
	 * @param userBean UserBean Object to be converted
	 * @return A String containing JSON for the given UserBean input
	 */
	private String convertUserBeanToJSON(UserBean userBean){
		String retval = "{" + "\n";
		
		retval += "    ";
		retval += "\"id\":";
		retval += "" + userBean.getId() + "";
		retval += ",";
		retval += "\n";
		
		retval += "    ";
		retval += "\"userName\":";
		retval += "\"";
		retval += "" + userBean.getUserName() + "";
		retval += "\"";
		retval += ",";
		retval += "\n";
		
		retval += "    ";
		retval += "\"password\":";
		retval += "\"";
		retval += "" + userBean.getPassword() + "";
		retval += "\"";
		retval += "\n";
		
		retval += "}";
		
		return retval;
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
