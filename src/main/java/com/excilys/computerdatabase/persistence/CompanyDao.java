package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.Constant;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

public class CompanyDao {

	String url = "jdbc:mysql://localhost:3306/computer-database-db";
	static Logger logger;
	private static CompanyDao INSTANCE = null;


	private CompanyDao(){
		
		logger = LoggerFactory.getLogger(CompanyDao.class);
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
	}
	
	public static CompanyDao getInstance() 
	{   
		if (INSTANCE == null){   
			synchronized(CompanyDao.class){
				if (INSTANCE == null){
					INSTANCE = new CompanyDao();
				}
			}
		}
		return INSTANCE;
	}
	
	private ArrayList<Company> getAll(String query){
		ArrayList<Company>  companyList = new ArrayList<Company>();
		Connection conn = null;
		try {
			conn = HikariCP.getInstance().getConnection();  //DriverManager.getConnection(url, "root", "root");
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			logger.info("Connection to get all the companies.");
			ResultSet result =  state.executeQuery(query);
			conn.commit();
			result.first();
			do{

				companyList.add(new Company(result.getInt(Constant.ID), result.getString(Constant.NAME)));
			}while (result.next());
			state.close();
			conn.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e1) {
				logger.error("Can't do the rollback: " + e.getMessage());
			}
			logger.error(e.getMessage());
			
		}
		
		return companyList;
	}
	
	
	public ArrayList<Company> getAll(){
		String query = "SELECT * FROM company";
		
		return getAll(query);
	}
	
	
	public ArrayList<Company> search(String name) {
		String query = "SELECT * FROM company WHERE name LIKE '%" + name+ "%'" ;
		
		return getAll(query);
	}

	public Optional<Company> get(long companyID){
		Connection conn =  null;
		try {
			conn = HikariCP.getInstance().getConnection();;
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			logger.info("Connection to get the company with id =" + companyID);
			String query = "SELECT * FROM company Where id =" + companyID;
			ResultSet result =  state.executeQuery(query);
			conn.commit();
			Company companyResult;
			if (result.first()) {
				result.first();
				companyResult = new Company(result.getInt(Constant.ID), result.getString(Constant.NAME));
				state.close();
				conn.close();
				return Optional.ofNullable(companyResult);
			}
			state.close();
			conn.close();
			
			
		} catch (SQLException e) {
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e1) {
				logger.error("Can't do the rollback: " + e.getMessage());
			}
			logger.error(e.getMessage());
			
		}
		return Optional.empty();
	}
	
	
	
	public boolean delete(Company company) {
		ComputerDao computerDao = ComputerDao.getInstance();
		ArrayList<Computer> allComputerOfTheCompany = computerDao.search("",company.getName(),Integer.MAX_VALUE, 0L);
		for (Computer computer : allComputerOfTheCompany) {
			System.out.println("on supprime ça : " + computer);
			computerDao.delete(computer);
		}
		String query =  "DELETE FROM company  WHERE id = " + company.getId();
		return HikariCP.getInstance().commit(query);
 	}
	
	public boolean delete(Long id) {
		return delete(this.get(id).orElse(null));
		
	}

	
public static void main(String[] args) {
		CompanyDao cd = CompanyDao.getInstance();
		Company company = cd.get(1L).orElse(null);
		
		cd.delete(company);
		System.out.println(company);
	}
	
}


