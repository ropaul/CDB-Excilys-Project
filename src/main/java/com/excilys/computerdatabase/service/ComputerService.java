package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.ComputerDaoSpring;


//@Service("computerService")
public class ComputerService {
	
	
	
	//private Logger logger = LoggerFactory.getLogger(ComputerService.class);
	@Autowired
	ComputerDaoSpring computerDaoSpring;
	
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
}
	
	public ArrayList<Computer> getAll(){
		return computerDaoSpring.getAll();
		
	}
	
	public ArrayList<Computer> getAll(int numberOfElement, long id){
		return computerDaoSpring.getAll( numberOfElement, id);
		
	}

	public Computer get(long id) {
		return computerDaoSpring.get(id).orElse(null);
		
	}
	
	public boolean add(Computer c) {
		return computerDaoSpring.add(c, true);
	}
	
	public boolean update(Computer c) {
		return computerDaoSpring.update(c, true);
	}
	
	public boolean delete(Computer c) {
		return computerDaoSpring.delete(c, true);
	}
	
	public Boolean delete(long id) {
		return computerDaoSpring.delete(id, true);
	}
	
	
	public ArrayList<Computer> search(String nameComputer, String nameCompany, int number, Long idBegin) {
		return computerDaoSpring.search(nameComputer, nameCompany, number,idBegin);
		
	}
}
