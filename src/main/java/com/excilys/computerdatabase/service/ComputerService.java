package com.excilys.computerdatabase.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;

public class ComputerService {
	
	
	private static ComputerService INSTANCE;
	private Logger logger;
	
	private ComputerService() 
	{
		logger = LoggerFactory.getLogger(ComputerService.class);
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
		ComputerDao computerSql = ComputerDao.getInstance();
		return computerSql.getAll();
		
	}
	
	public ArrayList<Computer> getAll(int numberOfElement, long id){
		ComputerDao computerSql = ComputerDao.getInstance();
		return computerSql.getAll( numberOfElement, id);
		
	}

	public Computer get(long id) {
		ComputerDao computerSql = ComputerDao.getInstance();
		return computerSql.get(id).orElse(new Computer());
		
	}
	
	public boolean add(Computer c) {
		ComputerDao computerSql = ComputerDao.getInstance();
		return computerSql.add(c);
	}
	
	public boolean update(Computer c) {
		ComputerDao computerSql = ComputerDao.getInstance();
		return computerSql.update(c);
	}
	
	public boolean delete(Computer c) {
		ComputerDao computerSql = ComputerDao.getInstance();
		return computerSql.delete(c);
	}
	
	public Boolean delete(long id) {
		ComputerDao computerSql = ComputerDao.getInstance();
		return computerSql.delete(id);
	}
}
