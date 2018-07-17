package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;

//@Service
public class CompanyService {

	
	
	//private Logger logger= LoggerFactory.getLogger(CompanyService.class);
	@Autowired
	CompanyDao companyDao;
	
	public ArrayList<Company> getAll(){
		return companyDao.getAll();
		
	}

	public Company get(long id) {
		
		return companyDao.get(id).orElse(null);
		
	}
	
	public boolean delete(Company company) {
		return companyDao.delete(company, true);
	}
	
	
}
