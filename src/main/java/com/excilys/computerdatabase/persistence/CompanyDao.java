package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	
	private String SELECT_ALL = "SELECT * FROM company";


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
	
	private ArrayList<Company> getAll(PreparedStatement query, Connection conn){
		ArrayList<Company>  companyList = new ArrayList<Company>();
	
		try {
			
			logger.info("Connection to get all the companies.");
			ResultSet result =  query.executeQuery();
			conn.commit();
			result.first();
			do{

				companyList.add(new Company(result.getInt(Constant.ID), result.getString(Constant.NAME)));
			}while (result.next());
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
	
	
	public ArrayList<Company> getAll() {
		Connection conn = HikariCP.getInstance().getConnection(); 
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement( "SELECT * FROM company");
		} catch (SQLException e) {
			logger.error("Error during creation of a query.");
		}
		
		return getAll(query, conn);
	}
	
	
	public ArrayList<Company> search(String name)  {
		Connection conn = HikariCP.getInstance().getConnection(); 
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement( "SELECT * FROM company WHERE name LIKE '%" + name+ "%'" );
		} catch (SQLException e) {
			logger.error("Error during creation of a query.");
		}
		
		return getAll(query,conn);
	}

	public Optional<Company> get(long companyID){
		Connection conn =  null;
		try {
			conn = HikariCP.getInstance().getConnection();;
			logger.info("Connection to get the company with id =" + companyID);
			PreparedStatement query =conn.prepareStatement("SELECT * FROM company Where id =" + companyID);
			ResultSet result =  query.executeQuery();
			conn.commit();
			Company companyResult;
			if (result.first()) {
				result.first();
				companyResult = new Company(result.getInt(Constant.ID), result.getString(Constant.NAME));
				conn.close();
				return Optional.ofNullable(companyResult);
			}
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
	
	
	
	public boolean delete(Company company, Boolean commit)  {
		boolean result  = true;
		ComputerDao computerDao = ComputerDao.getInstance();
		
		ArrayList<Computer> allComputerOfTheCompany = computerDao.search("",company.getName(),Integer.MAX_VALUE, 0L);
		for (Computer computer : allComputerOfTheCompany) {
			result  = result && computerDao.delete(computer, false);
		}
		Connection conn = HikariCP.getInstance().getConnection(); 
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement("DELETE FROM company  WHERE id = " + company.getId());
		} catch (SQLException e) {
			logger.error("Error during creation of a query.");
		}
		result  = result && HikariCP.getInstance().commit(query,conn, commit);
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error("Error closing of connection");
		}
		return result;
 	}
	
	public boolean delete(Long id, boolean commit) {
		return delete(this.get(id).orElse(null), commit);
		
	}

	
public static void main(String[] args) {
		CompanyDao cd = CompanyDao.getInstance();
		Company company = cd.get(1L).orElse(null);
		System.out.println(cd.delete(company, true));
	}
	
}


