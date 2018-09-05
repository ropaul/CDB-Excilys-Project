package com.excilys.formation.springconfig;

import java.io.IOException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.formation.service.UserService;

@Configuration
@EnableWebSecurity

@ComponentScan(basePackages ={
		"com.excilys.formation.controller",
"com.excilys.formation.spingconfig"})
@ImportResource({"classpath:springSecurityConfig.xml"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	UserService userService;

	@Autowired
	protected void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
		auth.authenticationProvider(getProvider());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.and()
		.authorizeRequests()
		.antMatchers(new String[]{ "/css/**", "/js/**", "/images/**", "/login*" }).permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.passwordParameter("password")
		.usernameParameter("username")
		.defaultSuccessUrl("/dashboard")
		.permitAll()
		.and()
		.logout()
		.permitAll()
		.logoutSuccessUrl("/login?signedout=true");
	}

	@Bean
	public AuthenticationProvider getProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] arg) {
		switch(6) {
		default :
			System.out.println("h");
		case 5 :
			System.out.println("t");
		}
		String tiger = "tiger";
		String tigre = 255>300? "truc": (tiger = "truc2")  ;
		System.out.println(tigre);
		
	}


}

