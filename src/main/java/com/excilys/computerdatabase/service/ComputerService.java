package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.ComputerDao;

public class ComputerService {
	
	
	private static ComputerService INSTANCE;
	private Logger logger;
	ComputerDao computerSql;
	
	private ComputerService() 
	{
		logger = LoggerFactory.getLogger(ComputerService.class);
		computerSql = ComputerDao.getInstance();
	}

	public static ComputerService getInstance() 
	{   
		if (INSTANCE == null){   
			synchronized(ComputerService.class){
				if (INSTANCE == null){
					INSTANCE = new ComputerService();
				}
			}
		}
		return INSTANCE;
	}
	
	public ArrayList<Computer> getAll(){
		return computerSql.getAll();
		
	}
	
	public ArrayList<Computer> getAll(int numberOfElement, long id){
		return computerSql.getAll( numberOfElement, id);
		
	}

	public Computer get(long id) {
		return computerSql.get(id).orElse(new Computer());
		
	}
	
	public boolean add(Computer c) {
		return computerSql.add(c);
	}
	
	public boolean update(Computer c) {
		return computerSql.update(c);
	}
	
	public boolean delete(Computer c) {
		return computerSql.delete(c);
	}
	
	public Boolean delete(long id) {
		return computerSql.delete(id);
	}
	
	
	public ArrayList<Computer> search(String nameComputer, String nameCompany, int number, Long idBegin) {
		return computerSql.search(nameComputer, nameCompany, number,idBegin);
		
	}
}
