package com.excilys.computerdatabase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/delete")
public class Error {
	
	
	@RequestMapping(value = "dashboard", method = RequestMethod.POST)
	public String homePage(ModelMap model)
	{
		
		return "error";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String home()
	{
		return "error";
}
}
