package com.xpanxion.skeleton.controllers;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xpanxion.skeleton.service.RestApiUserService;
import com.xpanxion.skeleton.service.UserService;
import com.xpanxion.skeleton.service.UserServiceImpl;

/**
 * Controller for the Users page
 * 
 * @author mhalberstadt
 *
 */
@Controller
public class UsersController {
	
	@Autowired
	private RestApiUserService userService;
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
	
	
	
	
	/**
	 * This method executes when a POST command is sent to add a new user to the database
	 * @param UsernameToAdd - Username of 
	 * @param passwordToAdd
	 * @return - boolean value as a string ("true" for true, "false" for false). Returns true if
	 * the user has been successfully added to the database
	 */
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
	
	/**
	 * Returns the ModelAndView Object for the users authentication page
	 * @return - ModelAndView Object for the Users authentication page
	 */
	public ModelAndView getUsersAuthenticationPage(){
		ModelAndView mAndV = new ModelAndView("usersAuthentication");
		return mAndV;
	}
	
	/**
	 * Returns the ModelAndView object for the users authentication page. The target page which the browser
	 * should go to after a user is successfully authenticated is passed in as a String through a path variable.
	 * 
	 */
	@RequestMapping(value="/usersAuthentication/{targetPage}", method=RequestMethod.GET)
	public ModelAndView getUsersAuthenticationPage_target_users(@PathVariable String targetPage){
		this.targetPageAfterUserAuthentication = targetPage;
		return this.getUsersAuthenticationPage();
	}
	
	
	/**
	 * This method executes when a user presses the submit button for password authentication.
	 * 
	 * @param Username - Username submitted as a path variable
	 * @param Password - password
	 * @param m
	 * @return - The string returned will either be "falseUsername" indicating that the input username is not
	 * in the database, "falsePassword" indicating that the password is incorrect for the given username, or the URL the 
	 * browser should be sent to after authenticating.
	 */
	@RequestMapping(value="/user/{Username}", method=RequestMethod.GET)
	@ResponseBody
	public String userNameAndPasswordSubmitWithPathVariable(@PathVariable String Username,@RequestParam("Password") String Password, Model m){
		return this.getStringToReturnBasedOnUserNameAndPasswordSubmission(Username, Password);
	}
	
	/**
	 * This method executes when a user presses the submit button for password authentication.
	 * 
	 * @param Username - Username submitted as a request parameter
	 * @param Password - password
	 * @param m
	 * @return - The string returned will either be "falseUsername" indicating that the input username is not
	 * in the database, "falsePassword" indicating that the password is incorrect for the given username, or the URL the 
	 * browser should be sent to after authenticating.
	 */
	@RequestMapping(value="/user", method=RequestMethod.GET)
	@ResponseBody
	public String userNameAndPasswordSubmitWithRequestParam(@RequestParam String Username,@RequestParam String Password, Model m){
		return this.getStringToReturnBasedOnUserNameAndPasswordSubmission(Username, Password);
	}
	
	/**
	 * This method will return a string to be sent to the client to display the correct error message for
	 * user authentication. The string returned will either be "falseUsername" indicating that the input username is not
	 * in the database, "falsePassword" indicating that the password is incorrect for the given username, or the URL the 
	 * browser should be sent to after authenticating.
	 * 
	 * @param userName - username for authentication
	 * @param Password - password for authentication to match with the input username
	 * @return - The string returned will either be "falseUsername" indicating that the input username is not
	 * in the database, "falsePassword" indicating that the password is incorrect for the given username, or the URL the 
	 * browser should be sent to after authenticating.
	 * 
	 */
	private String getStringToReturnBasedOnUserNameAndPasswordSubmission(
			String userName,
			String Password){
        if (this.userService.isUsernameInDatabase(userName)){
        	if (this.userService.isPasswordCorrectForGivenUsername(userName, Password)){
        		if (this.targetPageAfterUserAuthentication.isEmpty()){
        			this.targetPageAfterUserAuthentication = "users";
        		}
        		return this.targetPageAfterUserAuthentication;
        	} else {
        		return "falsePassword";
        	}
        } else {
        	return "falseUsername";
        }
	}
	
	/**
     * Sets the user test service for this controller
     * 
     * @param service the  user test service to use in this controller. 
     */
    @Resource
    public void setRestApiUserService(RestApiUserService service){
    	this.userService = service;
    }
    
    /**
     * Returns the User Test Service
     * 
     * @return Returns the User Test Service
     */
    public RestApiUserService getUserService(){
    	return this.userService;
    }
    
    
    
    
	
	
}
