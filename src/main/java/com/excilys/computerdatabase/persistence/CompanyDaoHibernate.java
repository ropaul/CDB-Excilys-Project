package com.excilys.computerdatabase.persistence;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Company;

@Transactional
public interface CompanyDaoHibernate extends JpaRepository<Company, Long>{
	
	Company findById(int id);

}
