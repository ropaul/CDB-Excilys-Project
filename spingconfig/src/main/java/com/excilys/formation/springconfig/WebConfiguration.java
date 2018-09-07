package com.excilys.formation.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


@EnableWebMvc
@Configuration
@EnableWebSecurity
@Import({SecurityConfig.class})
@ComponentScan(basePackages ={
		"com.excilys.formation.controller",
		"com.excilys.formation.spingconfig"})
//@ImportResource({"classpath:springSecurityConfig.xml"})
public class WebConfiguration implements WebMvcConfigurer{

	
	//SECURITY
	
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("WebContent/static/jsp/dashboard.jsp").setViewName("dashboard");
        registry.addViewController("WebContent/static/jsp/login.jsp").setViewName("login");
        registry.addViewController("WebContent/static/jsp/addComputer.jsp").setViewName("add");
        registry.addViewController("WebContent/static/jsp/editComputer.jsp").setViewName("edit");
    }
	
	//MVC
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//	      registry.addViewController("WebContent/static/jsp/dashboard.jsp");
//	   }
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/");
	}

	
	
	@Bean
    public LocaleChangeInterceptor localeInterceptor(){
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
    }
}
