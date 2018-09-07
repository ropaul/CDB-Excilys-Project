package com.excilys.formation.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.service.UserService;


@Controller
//@RequestMapping("/login")
public class LoginController {
	
	@GetMapping("/login")
	public String showLoginPage(ModelMap model) {
		return "login";
	}
//	public static final String NAME  = "name";
//	public static final String PASSWORD  = "password";
//
//	@Autowired
//	UserService userService;
//	
//	@Autowired
//	Validation validation;
//	
//	@Autowired
//	AuthenticationProvider authentication;
//
////	@RequestMapping(method = RequestMethod.POST)
////	public String home(ModelMap model,@RequestParam(name = NAME,required = false) String name,
////			@RequestParam(name = PASSWORD,required = false) String password
////			) {
////		
////			return "login";
////		}
//
//	
//
//
//	@RequestMapping(method = RequestMethod.GET)
//	public ModelAndView home(ModelMap model) {
//		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            return new ModelAndView("redirect:/dashboard");
//        }
//        return new ModelAndView("login");
//	}
	
	
}