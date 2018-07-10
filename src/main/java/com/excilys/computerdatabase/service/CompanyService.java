package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;

public class CompanyService {

	
	private static CompanyService INSTANCE;
	private Logger logger;
	
	private CompanyService() 
	{
		logger = LoggerFactory.getLogger(CompanyService.class);
	}

	public static CompanyService getInstance() 
	{   
		if (INSTANCE == null){   
			synchronized(CompanyService.class){
				if (INSTANCE == null){
					INSTANCE = new CompanyService();
					INSTANCE.logger.info("new instance of CompanyService");
				}
			}
		}
		return INSTANCE;
	}
	
	public ArrayList<Company> getAll(){
		CompanyDao companySql = CompanyDao.getInstance();
		return companySql.getAll();
		
	}

	public Company get(long id) {
		CompanyDao companySql = CompanyDao.getInstance();
		return companySql.get(id).orElse(new Company());
		
	}
	
	
	
	
}
