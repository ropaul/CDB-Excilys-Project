package com.excilys.computerdatabase.springconfig;


import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.excilys.computerdatabase.controller.Validation;
import com.excilys.computerdatabase.persistence.CompanyDaoSpring;
import com.excilys.computerdatabase.persistence.ComputerDaoSpring;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@Configuration
@ComponentScan(basePackages ={"com.excilys.computerdatabase.persistence",
		"com.excilys.computerdatabase.service",
		"com.excilys.computerdatabase.model",
		"com.excilys.computerdatabase.ui",
		"com.excilys.computerdatabase.controller",
		"com.excilys.computerdatabase.springconfig"})
public class Application {

	static Logger logger = LoggerFactory.getLogger(Application.class);
	
	
	
	@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:message/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
	
	
	@Bean
    public LocaleChangeInterceptor localeInterceptor(){
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
}
    
	
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setCookieName("lang");
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
}
	
		@Bean
	   public ViewResolver viewResolver() {
	      InternalResourceViewResolver bean = new InternalResourceViewResolver();
	 
	      bean.setViewClass(JstlView.class);
	      bean.setPrefix("/static/jsp/");
	      bean.setSuffix(".jsp");
	 
	      return bean;
	   }
	
	
	


	@Bean("companyDaoSpring")
	public CompanyDaoSpring getCompanyDaoSpring(){
		logger.info("creation of beans : companyDaoSpring");
				return new CompanyDaoSpring();
	}

	@Bean("computerDaoSpring")
	public ComputerDaoSpring getComputerDaoSpring()
	{
		logger.info("creation of beans : computerDaoSpring");
		return new ComputerDaoSpring();
	}

	@Bean("companyService")
	public CompanyService getCompanyService()
	{
		logger.info("creation of beans : companyService");
		return new CompanyService();
	}

	@Bean("computerService")
	public ComputerService getComputerService()
	{
		logger.info("creation of beans : computerService");
		return new ComputerService();
	}


		@Bean("DataSource")
		public DriverManagerDataSource getDriverManagerDataSource(){
			logger.info("creation of beans : dataSource");
			String DRIVER = "com.mysql.jdbc.Driver";
			String URL = "jdbc:mysql://localhost:3306/computer-database-db";
			String USER_NAME = "admincdb";
			String PASSWORD = "qwerty1234";
			DriverManagerDataSource dataSource=  new DriverManagerDataSource();
			dataSource.setDriverClassName(DRIVER);
			dataSource.setUsername(USER_NAME);
			dataSource.setUrl(URL);
			dataSource.setPassword(PASSWORD);
			return dataSource;
			
		}

	
	
	@Bean("validation")
	public Validation getValidation()
	{
		Validation validation= Validation.getInstance();

		return validation;
	}
}

