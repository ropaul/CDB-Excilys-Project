package com.excilys.computerdatabase.springconfig;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.orm.jpa.vendor.Database;

@Configuration
@EnableWebMvc
@EnableJpaRepositories("com.excilys.formation.computerdatabase")
@ComponentScan(basePackages ={"com.excilys.computerdatabase.persistence",
		"com.excilys.computerdatabase.service",
		"com.excilys.computerdatabase.model",
		"com.excilys.computerdatabase.ui",
		"com.excilys.computerdatabase.controller",
"com.excilys.computerdatabase.springconfig"})
@EntityScan(basePackages ={"com.excilys.computerdatabase.persistence",
		"com.excilys.computerdatabase.service",
		"com.excilys.computerdatabase.model",
		"com.excilys.computerdatabase.ui",
		"com.excilys.computerdatabase.controller",
"com.excilys.computerdatabase.springconfig"})
@PropertySource("classpath:configuration.properties")
public class JPAConfig implements WebMvcConfigurer{
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JPAConfig.class);
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
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		logger.info("EntityManagedFactory: {}", INIT_MESSAGE);
		LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
		lemfb.setDataSource(dataSource);
		lemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lemfb.setPackagesToScan("com.excilys.formation.computerdatabase");
		logger.info("EntityManagedFactory: {}", ACK_MESSAGE);
		return lemfb;
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
	
    
    
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // Specifying static resource location for themes related files(css etc)
//    	logger.info("viewResolver: {}", INIT_MESSAGE);
//    	registry.addResourceHandler("/static/**")
//    		.addResourceLocations("/static/");
//    	logger.info("viewResolver: {}", ACK_MESSAGE);
//}
}
