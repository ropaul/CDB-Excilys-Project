package com.excilys.formation.springconfig;


import java.util.Locale;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@ComponentScan(basePackages ={
		"com.excilys.formation.service",
		"com.excilys.formation.spingconfig",
		"com.excilys.formation.controller",
		})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages ={
		"com.excilys.formation.persistence",
		"com.excilys.formation.service",
		},
entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager"
)
@EntityScan(basePackages ={
		"com.excilys.formation.model"
		})
@PropertySource("classpath:configuration.properties")
public class Application {
	
	static Logger logger = LoggerFactory.getLogger(Application.class);

	
	
	
	//spring DATA 
	private static final String INIT_MESSAGE = "Initiate...";
	private static final String ACK_MESSAGE = "OK";
		
	@Resource
	private Environment environment;	
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		logger.info("jpaVendorAdapter: {}", INIT_MESSAGE);
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setGenerateDdl(true);
		logger.info("jpaVendorAdapter: {}", ACK_MESSAGE);
		return jpaVendorAdapter;
	}
	
	
	@Bean
    public JpaTransactionManager transactionManager() {
		logger.info("transactionManager: {}", INIT_MESSAGE);
		JpaTransactionManager txnMgr = new JpaTransactionManager();
        txnMgr.setEntityManagerFactory(entityManagerFactory().getObject());
        logger.info("transactionManager: {}", ACK_MESSAGE);
        return txnMgr;
    }

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		logger.info("exceptionTranslation: {}", INIT_MESSAGE);
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	

	@Bean
    DataSource dataSource(Environment env) {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSourceConfig.setJdbcUrl(env.getRequiredProperty("db.url"));
        dataSourceConfig.setUsername(env.getRequiredProperty("db.username"));
        dataSourceConfig.setPassword(env.getRequiredProperty("db.password"));
 
        return new HikariDataSource(dataSourceConfig);
    }
     
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.excilys.formation");
        Properties jpaProperties = new Properties();
        //Configures the used database dialect. This allows Hibernate to create SQL
        //that is optimized for the used database.
        jpaProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        //Specifies the action that is invoked to the database when the Hibernate
        //SessionFactory is created or closed.
        jpaProperties.put("hibernate.hbm2ddl.auto", 
        		environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        //Configures the naming strategy that is used when Hibernate creates
        //new database objects and schema elements
        jpaProperties.put("hibernate.ejb.naming_strategy", 
        		environment.getRequiredProperty("hibernate.ejb.naming_strategy"));
        //If the value of this property is true, Hibernate writes all SQL
        //statements to the console.
        jpaProperties.put("hibernate.show_sql", 
        		environment.getRequiredProperty("hibernate.show_sql"));
        //If the value of this property is true, Hibernate will format the SQL
        //that is written to the console.
        jpaProperties.put("hibernate.format_sql", 
        		environment.getRequiredProperty("hibernate.format_sql"));
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }
    
    
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


	//SPRING MVC

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:message/message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}


	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.ENGLISH);
		localeResolver.setCookieName("lang");
		localeResolver.setCookieMaxAge(3600);
		return localeResolver;
	}

	
	// COMPUTER DATABASE
	
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();

		bean.setViewClass(JstlView.class);
		bean.setPrefix("/static/jsp/");
		bean.setSuffix(".jsp");

		return bean;
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


//	@Bean("validation")
//	public Validation getValidation()
//	{
//		Validation validation= Validation.getInstance();
//
//		return validation;
//	}

	

	
}

