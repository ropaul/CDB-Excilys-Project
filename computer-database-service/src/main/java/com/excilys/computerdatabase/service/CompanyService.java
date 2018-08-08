package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.CompanyDaoHibernate;

//@Service

public class CompanyService {

	
	
	//private Logger logger= LoggerFactory.getLogger(CompanyService.class);
	@Autowired
	CompanyDaoHibernate companyDaoHibernate;
	
	@Transactional
	public ArrayList<Company> getAll(){
		return (ArrayList<Company>) companyDaoHibernate.findAll();
		
	}

	@Transactional
	public Company get(long id) {
		
		return companyDaoHibernate.findById(id).orElse(null);
		
	}
	
	@Transactional
	public boolean delete(Company company) {
		 companyDaoHibernate.delete(company);
		 return true;
	}
	
//	public static void main(String[] args) {
//		ComputerService cs = new ComputerService();
//		CompanyDaoHibernate h = new CompanyDaoHibernate();
//		System.out.println(cs.get(1));
//	}
}
