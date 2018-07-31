package com.excilys.computerdatabase.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Computer;

@Transactional
public interface ComputerDaoHibernate extends JpaRepository<Computer, Long>{
	
	Computer findById(int id);

	
}