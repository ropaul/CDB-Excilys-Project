package com.excilys.computerdatabase.persistence;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.excilys.computerdatabase.Constant;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

//@Repository
public class ComputerDaoSpring {
	String url = "jdbc:mysql://localhost:3306/computer-database-db";
	Logger logger =LoggerFactory.getLogger(ComputerDao.class);

//	@Autowired
	CompanyDaoSpring companyDaoSpring;

//	@Autowired
	DriverManagerDataSource dataSource;

	JdbcTemplate jdbcTemplate ;



	public ComputerDaoSpring() {
//		this.companyDaoSpring = new CompanyDaoSpring();
		if (dataSource == null) {
			dataSource = Configuration.getDataSource();
		}
		jdbcTemplate = new JdbcTemplate(dataSource);
	}



	private String ERROR_DURING_QUERY = "Error during creation of a query.";
	private String ROLLBACK           = "Can't do the rollback: ";
	private String SELECT_ALL         = "SELECT * FROM computer";
	private String SELECT_ID          = "SELECT * FROM computer WHERE " + Constant.ID + " > %s LIMIT  %s";
	private String SELECT_ONE         = "SELECT * FROM computer Where id = %s";
	private String SELECT_SEARCH      = "SELECT * FROM computer WHERE " + Constant.NAME + " LIKE '%%s%' AND"  + Constant.ID + " > %s LIMIT %s";
	private String ADD                = "INSERT INTO computer  VALUES (%s, '%s',%s ,%s, %s )";
	private String ADD_WHITHOUT_ID    = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES ( '%s',%s ,%s, %s )";
	private String DELETE             = "DELETE FROM computer  WHERE id = %s";
	private String UPDATE = "UPDATE  computer  SET " + Constant.NAME +" = %s, "+ 
			Constant.INTRODUCED +" = %s, " +
			Constant.DISCONTINUED +" = %s, " +
			Constant.COMPAGNYID +" = %s " +
			"WHERE " +  Constant.ID +" in (%s);";



	private ArrayList<Computer> getAll(String query)   {
		this.companyDaoSpring = new CompanyDaoSpring();
		ArrayList<Computer>  computersList = new ArrayList<Computer>();
		ArrayList<Company> companies;



		companies = companyDaoSpring.getAll();
		HashMap<Long, Company> companyPerID = new HashMap<Long, Company>();
		for (Company company: companies) {
			companyPerID.put(company.getId(), company);
		}

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
		ComputerFactory usineChinoise = new ComputerFactory();

		for (Map<String, Object> data: rows) {

			
			Date introduced = data.containsKey(Constant.INTRODUCED) && data.get(Constant.INTRODUCED) != null ? Date.valueOf(data.get(Constant.INTRODUCED).toString()) : null;
			Date discontinued = data.containsKey(Constant.DISCONTINUED)  && data.get(Constant.DISCONTINUED) != null ? Date.valueOf(data.get(Constant.DISCONTINUED).toString()) : null;
			int companyId = data.containsKey(Constant.COMPAGNYID)  && data.get(Constant.COMPAGNYID) != null ? Integer.parseInt(data.get(Constant.COMPAGNYID).toString()) : 0;
			Computer computer = usineChinoise.createComputer(Optional.ofNullable(Integer.parseInt(data.get(Constant.ID).toString())),
					Optional.ofNullable(data.get(Constant.NAME).toString()),
					Optional.ofNullable( introduced),
					Optional.ofNullable(discontinued),
					Optional.ofNullable(companyId));
			
			computersList.add(computer);
		}


		return computersList;


	}

	public ArrayList<Computer> getAll(){
		String query = SELECT_ALL;
		return getAll(query);
	}


	public ArrayList<Computer> getAll(int number, Long idBegin) {

		String query = String.format(SELECT_ID,Constant.ID, idBegin, number);

		return getAll(query);
	}



	public ArrayList<Computer> search(String name, int number, Long idBegin) throws SQLException{

		String query = String.format(SELECT_SEARCH,name, Constant.ID, idBegin,number);
		return getAll(query);
	}

	public ArrayList<Computer> search(String nameComputer, String nameCompany, int number, Long idBegin) {

		String query = SELECT_ALL;
		if ((nameCompany != null && nameCompany != "") ||
				(nameComputer != null && nameComputer != "")||
				(number != 0 || idBegin != 0)) {
			query+=" WHERE " + Constant.ID + " > " + idBegin ;
		}
		else {

			return getAll(query);

		}
		if ((nameCompany != null && nameCompany != "")) {
			ArrayList<Company> companies = companyDaoSpring.search(nameCompany);
			String idCompany = "(";
			for (int i = 0;  i< companies.size(); i++) {
				idCompany += (int) companies.get(i).getId() + ", ";
			}
			idCompany = idCompany.substring(0, idCompany.length() -2) ;
			idCompany += ")";
			query += " AND company_id in " + idCompany ;
		}
		if (nameComputer != null && nameComputer != "") {
			query += " OR " +Constant.NAME + " LIKE '%" + nameComputer + "%'"; 
		}
		query += " LIMIT " + number;

		return getAll(query);


	}

	public Boolean add(Computer c, boolean commit){
		long id = c.getId();
		String name = c.getName();
		String introduced;
		String query = "";

		introduced = c.getIntroduced()!= null ? "'" +c.getIntroduced().toString()+"'" : "null" ;


		String discontinued;

		discontinued = c.getDiscontinued() != null ? "'" +c.getDiscontinued().toString()+"'" : "null" ;


		Long companyId = null;
		if (c.getCompany() != null && c.getCompany().getId() != 0){
			companyId =  c.getCompany().getId();
		}


		if (id > 0) { 

			query = String.format(ADD, id, name, introduced, discontinued, companyId );


		}
		else { 
			query = String.format(ADD_WHITHOUT_ID, name, introduced, discontinued, companyId );

		}


		jdbcTemplate.execute(query);
		
		return true;


	}


	public Boolean update( Computer newComputer, boolean commit)  {
		long oldId = newComputer.getId();
		String name = "'" +newComputer.getName() +"'" ;
		String introduced ;

		introduced = newComputer.getIntroduced()!= null ? "'" +newComputer.getIntroduced().toString()+"'" : "null" ;


		String discontinued;

		discontinued = newComputer.getDiscontinued() != null ? "'" +newComputer.getDiscontinued().toString()+"'" : "null" ;


		Long companyId = null;
		if (newComputer.getCompany() != null){
			companyId =  newComputer.getCompany().getId();
		}
		String query = null;

		query = String.format(UPDATE, name, introduced, discontinued, companyId, oldId );



		return (jdbcTemplate.queryForList(query) != null);


	}


	public Boolean delete(Computer c, boolean commit) {
		return delete(c.getId(),  commit);
	}

	public Boolean delete(long id, boolean commit) {
		Boolean result = false;
		try {
			dataSource.getConnection().setAutoCommit(false);
			String query = "";
			query = String.format(DELETE, id );

			result = (jdbcTemplate.queryForList(query) != null);
			if (commit) {
			dataSource.getConnection().commit();
		}
			}
		catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
			try {
				dataSource.getConnection().rollback();
			} catch (SQLException e1) {
				logger.error(ROLLBACK);
			}
		}
		
		
		return result ;
	}


	public Optional<Computer> get(long computerID){

		String query = String.format(SELECT_ONE, computerID);


		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
		ComputerFactory usineChinoise = new ComputerFactory();
		Computer computer = null;
		for (Map<String, Object> data: rows) {
			Date introduced = data.containsKey(Constant.INTRODUCED) && data.get(Constant.INTRODUCED) != null ? Date.valueOf(data.get(Constant.INTRODUCED).toString()) : null;
			Date discontinued = data.containsKey(Constant.DISCONTINUED)  && data.get(Constant.DISCONTINUED) != null ? Date.valueOf(data.get(Constant.DISCONTINUED).toString()) : null;
			int companyId = data.containsKey(Constant.COMPAGNYID)  && data.get(Constant.COMPAGNYID) != null ? Integer.parseInt(data.get(Constant.COMPAGNYID).toString()) : 0;
			computer = usineChinoise.createComputer(Optional.ofNullable(Integer.parseInt(data.get(Constant.ID).toString())),
					Optional.ofNullable(data.get(Constant.NAME).toString()),
					Optional.ofNullable( introduced),
					Optional.ofNullable(discontinued),
					Optional.ofNullable(companyId));
		}
		return Optional.ofNullable(computer ); 
	}


	class ComputerFactory{

		HashMap<Long, Company> companyPerID;
		@Autowired
		CompanyDaoSpring companyDaoSpring ;


		public ComputerFactory() {
			CompanyDaoSpring companyDaoSpring = new CompanyDaoSpring();
			ArrayList<Company> companies = companyDaoSpring.getAll();
			companyPerID = new HashMap<Long, Company>();
			for (Company company: companies) {
				companyPerID.put(company.getId(), company);
			}
		}



		Computer createComputer(Optional<Integer> idOptionnal,Optional<String> nameOptional, Optional<Date> introducedOptionnal, Optional<Date> DiscontinuedOptionnal, Optional<Integer> companyOptionnal) {
			int id = idOptionnal.orElse(0);
			String name = nameOptional.orElse(null);
			Long companyId;
			Date introduced, discontinued;
			introduced = introducedOptionnal.orElse(null);
			discontinued = DiscontinuedOptionnal.orElse(null);
			companyId = new Long(companyOptionnal.orElse(new Integer(0)));
			return new Computer(id, name,companyPerID.get(companyId),introduced , discontinued);
		}

	}
	


}
