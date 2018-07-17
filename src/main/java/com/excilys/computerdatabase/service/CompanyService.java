package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;

@Repository("companyService")
public class CompanyService extends AbstractService{

	
	private static CompanyService INSTANCE;
	private Logger logger= LoggerFactory.getLogger(CompanyService.class);
	CompanyDao companySql;
	
	private CompanyService() 
	{
		
		companySql = CompanyDao.getInstance();
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
		return companySql.getAll();
		
	}

	public Company get(long id) {
		
		return companySql.get(id).orElse(null);
		
	}
	
	public boolean delete(Company company) {
		return companySql.delete(company, true);
	}
	
	
}
