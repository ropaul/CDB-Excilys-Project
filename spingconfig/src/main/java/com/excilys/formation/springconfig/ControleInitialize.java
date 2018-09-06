package com.excilys.formation.springconfig;

import javax.servlet.ServletRegistration;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ControleInitialize extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { Application.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfiguration.class, SecurityConfig.class} ;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/dashboard", "/addComputer", "/editComputer", "/error" , "/delete", "/login"};
	}

	@Override
	protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		return dispatcherServlet;
	}
	
	
	@Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration)
    {
        super.customizeRegistration(registration);
        registration.setLoadOnStartup(1);
    }
	
}