package com.xpanxion.skeleton.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xpanxion.skeleton.service.TestService;

/**
 * Controller for the Main page
 * 
 * @author mhalberstadt
 *
 */
@Controller
public class MainController {
	
	/**
	 * Controller for the Main page
	 * 
	 */
	@RequestMapping(value="**/main", method=RequestMethod.GET)
	public ModelAndView getMainPage(){
		ModelAndView mAndV = new ModelAndView("main");
		return mAndV;
	}
	
	
	
}
