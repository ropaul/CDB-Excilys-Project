package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.CompanyDaoSpring;

//@Service
public class CompanyService {

	
	
	//private Logger logger= LoggerFactory.getLogger(CompanyService.class);
	@Autowired
	CompanyDaoSpring companyDaoSpring;
	
	public ArrayList<Company> getAll(){
		return companyDaoSpring.getAll();
		
	}

	public Company get(long id) {
		
		return companyDaoSpring.get(id).orElse(null);
		
	}
	
	public boolean delete(Company company) {
		return companyDaoSpring.delete(company, true);
	}
	
	
}
