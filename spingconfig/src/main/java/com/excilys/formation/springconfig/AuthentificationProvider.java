package com.excilys.formation.springconfig;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.excilys.formation.service.UserService;

public class AuthentificationProvider extends DaoAuthenticationProvider {
	
	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		
		String name = auth.getName();
		String password = auth.getCredentials().toString();
		
		UserDetails user = userService.loadUserByUsername(name);
		
		if(user == null) {
			throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
		}
		
		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}