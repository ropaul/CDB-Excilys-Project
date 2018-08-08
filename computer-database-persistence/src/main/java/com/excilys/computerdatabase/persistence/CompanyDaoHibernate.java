package com.excilys.computerdatabase.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.model.Company;

@Repository
public interface CompanyDaoHibernate extends JpaRepository<Company, Long>{
	
	Company findById(int id);

}
