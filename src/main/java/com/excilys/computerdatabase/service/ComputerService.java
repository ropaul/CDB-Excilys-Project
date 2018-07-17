package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;


//@Service("computerService")
public class ComputerService {
	
	
	
	//private Logger logger = LoggerFactory.getLogger(ComputerService.class);
	@Autowired
	ComputerDao computerDao;
	
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
}
	
	public ArrayList<Computer> getAll(){
		return computerDao.getAll();
		
	}
	
	public ArrayList<Computer> getAll(int numberOfElement, long id){
		return computerDao.getAll( numberOfElement, id);
		
	}

	public Computer get(long id) {
		return computerDao.get(id).orElse(null);
		
	}
	
	public boolean add(Computer c) {
		return computerDao.add(c, true);
	}
	
	public boolean update(Computer c) {
		return computerDao.update(c, true);
	}
	
	public boolean delete(Computer c) {
		return computerDao.delete(c, true, null);
	}
	
	public Boolean delete(long id) {
		return computerDao.delete(id, true, null);
	}
	
	
	public ArrayList<Computer> search(String nameComputer, String nameCompany, int number, Long idBegin) {
		return computerDao.search(nameComputer, nameCompany, number,idBegin);
		
	}
}
