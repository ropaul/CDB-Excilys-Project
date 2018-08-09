package com.excilys.formation.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excilys.formation.model.Company;



@Repository
public interface CompanyDaoHibernate extends JpaRepository<Company, Long>{
	
	Company findById(int id);

}
