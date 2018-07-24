package com.excilys.computerdatabase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	@RequestMapping(value = "dashboard", method = RequestMethod.POST)
	public String homePage(ModelMap model, @RequestParam("act") String act)
	{
		
		return "dashboard";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String home()
	{
		return "dashboard";
	}
	
	@RequestMapping(value="/dashboard",method = RequestMethod.GET)
	public String homePage()
	{
		return "dashboard";
	}
		
}