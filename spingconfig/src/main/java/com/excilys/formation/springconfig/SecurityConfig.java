package com.excilys.formation.springconfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.excilys.formation.service.AppAuthProvider;
import com.excilys.formation.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
    UserService userService;
	
	@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth**").authenticated()
//                .antMatchers("/auth/admin**").hasAuthority(adminRole)
                .anyRequest().permitAll()
            .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/auth/dashboard").failureUrl("/login")
                .usernameParameter("name").passwordParameter("password")
            .and()
                .logout().invalidateHttpSession(true)
                .logoutUrl("/error")
                .logoutSuccessUrl("/login")
            .and()
                .csrf()
            .and()
                .sessionManagement().maximumSessions(1).expiredUrl("/login");
    }
	
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//          .authorizeRequests()
//          .antMatchers("/login*").anonymous()
//          .anyRequest().authenticated()
//          .and()
//          .formLogin()
//          .loginPage("/login.jsp")
//          .defaultSuccessUrl("dashboard.html")
//          .failureUrl("/error.jsp")
//          .and()
//          .logout().logoutSuccessUrl("/login.jsp");
//    }
  
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
    
  