package com.excilys.computerdatabase.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.model.Computer;

@Repository
public interface ComputerDaoHibernate extends JpaRepository<Computer, Long>{
	
	Computer findById(int id);
	

//	 @Query("SELECT c FROM Computer c "
////	 		+ "JOIN Company "
////	 		+ "WHERE c.name LIKE '%nameComputer%' "
////	 		+ "OR Company.name LIKE '%nameCompany%' "
//	 		)
//	    public List<Computer> find(@Param("nameComputer") String nameComputer, @Param("nameCompany") String nameCompany);
//	
}