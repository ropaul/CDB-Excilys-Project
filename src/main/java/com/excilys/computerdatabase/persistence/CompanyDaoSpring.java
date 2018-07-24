package com.excilys.computerdatabase.persistence;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.excilys.computerdatabase.Constant;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

public class CompanyDaoSpring {

	
	
//	@Autowired
	ComputerDaoSpring computerDaoSpring;
	
//	@Autowired
	DriverManagerDataSource dataSource;
	
    JdbcTemplate jdbcTemplate ;
	
	String url = "jdbc:mysql://localhost:3306/computer-database-db";
	Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	
	public CompanyDaoSpring() {
		this.computerDaoSpring = new ComputerDaoSpring();
		if (dataSource == null) {
			dataSource = Configuration.getDataSource();
		}
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	private String SELECT_ALL = "SELECT * FROM company";
	private String ERROR_DURING_QUERY = "Error during creation of a query.";
	private String ROLLBACK = "Can't do the rollback: ";
	private String DELETE = "DELETE FROM company  WHERE id = ?";
	private static String SELECT_SEARCH =  "SELECT * FROM company WHERE name LIKE '% ? %'" ;
	private String SELECT_ID = "SELECT * FROM company Where id = ?";



	
	private ArrayList<Company> getAll(String query){
		ArrayList<Company>  companyList = new ArrayList<Company>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
		
		
		for (Map<String, Object> data: rows) {
			
			companyList.add(new Company(Integer.parseInt(data.get(Constant.ID).toString()),data.get(Constant.NAME).toString()));
			
		}
		
		return companyList;
	}
	
	
	public ArrayList<Company> getAll() {
		
		String query = SELECT_ALL;
		return getAll(query);
	}
	
	
	public ArrayList<Company> search(String name)  {
		
			String query = String.format(SELECT_SEARCH,name);
		return getAll(query);
	}

	public Optional<Company> get(long companyID){
		ArrayList<Company>  companyList = new ArrayList<Company>();
		String query =  SELECT_ID;
			query = String.format(query, Long.toString(companyID));
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
			
			
			for (Map<String, Object> data: rows) {
				
				companyList.add(new Company(Integer.parseInt(data.get(Constant.ID).toString()),data.get(Constant.NAME).toString()));
				
			}
			
			return Optional.ofNullable(companyList.get(0));
	}
	
	
	
	public boolean delete(Company company, Boolean commit)  {
		boolean result  = true;
		try {
			dataSource.getConnection().setAutoCommit(false);

			ArrayList<Computer> allComputerOfTheCompany = computerDaoSpring.search("",company.getName(),Integer.MAX_VALUE, 0L);
			for (Computer computer : allComputerOfTheCompany) {
				result  = result && computerDaoSpring.delete(computer, false);
			}
			String query = DELETE + company.getId();
			jdbcTemplate.queryForList(query);
		} catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
			try {
				dataSource.getConnection().rollback();
			} catch (SQLException e1) {
				logger.error(ROLLBACK);
			}
		}
		
		
		
		
		return result;
 	}
	
	public boolean delete(Long id, boolean commit) {
		return delete(this.get(id).orElse(null), commit);
		
	}
	
	public static void main(String[] args) {
		CompanyDaoSpring c = new CompanyDaoSpring();
		System.out.println(c.getAll());
	}
	
}
