package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.ComputerDaoHibernate;



public class ComputerService {
	
	
	
	//private Logger logger = LoggerFactory.getLogger(ComputerService.class);
//	@Autowired
	ComputerDaoHibernate computerDaoHibernate;
	
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
}
	@Transactional
	public ArrayList<Computer> getAll(){
		return (ArrayList<Computer>) computerDaoHibernate.findAll();
		
	}
	
//	public ArrayList<Computer> getAll(int numberOfElement, long id){
//		return computerDaoHibernate.getAll( numberOfElement, id);
//		
//	}

	@Transactional
	public Computer get(long id) {
		return computerDaoHibernate.findById(id).orElse(null);
		
	}
	
	@Transactional
	public boolean add(Computer c) {
		return computerDaoHibernate.save(c) != null;
	}
	
	@Transactional
	public boolean update(Computer c) {
		return computerDaoHibernate.save(c) != null;
	}
	
	@Transactional
	public boolean delete(Computer c) {
		computerDaoHibernate.delete(c);
		return true;
	}
	
	@Transactional
	public Boolean delete(long id) {
		computerDaoHibernate.delete(this.get(id));
		return true;
	}
	
	@Transactional
	public ArrayList<Computer> search(String nameComputer, String nameCompany, int number, Long idBegin) {
		ArrayList<Computer> computers = (ArrayList<Computer>) computerDaoHibernate.findAll();
		int index = computers.indexOf(this.get(idBegin));
		return (ArrayList<Computer>) computers.subList(index, number);
		
	}
}
