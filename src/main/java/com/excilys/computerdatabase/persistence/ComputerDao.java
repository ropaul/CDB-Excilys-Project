package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.Constant;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

@Repository("computerDao")
public class ComputerDao {
	String url = "jdbc:mysql://localhost:3306/computer-database-db";
	Logger logger;
	private static ComputerDao INSTANCE = null;
	
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

	private ComputerDao() 
	{

		logger = LoggerFactory.getLogger(ComputerDao.class);


		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
	}

	public static ComputerDao getInstance() 
	{   
		if (INSTANCE == null){   
			synchronized(ComputerDao.class){
				if (INSTANCE == null){
					INSTANCE = new ComputerDao();
				}
			}
		}
		return INSTANCE;
	}

	private ArrayList<Computer> getAll(String query)   {
		ArrayList<Computer>  computersList = new ArrayList<Computer>();
		ArrayList<Company> companies;
		CompanyDao companySql = CompanyDao.getInstance();
		Connection conn = null;
		try {
			companies = companySql.getAll();
			HashMap<Long, Company> companyPerID = new HashMap<Long, Company>();
			for (Company company: companies) {
				companyPerID.put(company.getId(), company);
			}
			 conn = HikariCP.getInstance().getConnection();
			 
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ResultSet result =  state.executeQuery(query);
			conn.commit();
			result.first();

			ComputerFactory usineChinoise = new ComputerFactory();
			do{
				Computer computer = usineChinoise.createComputer(Optional.ofNullable(result.getInt(Constant.ID)),
						Optional.ofNullable(result.getString(Constant.NAME)),
						Optional.ofNullable(result.getDate(Constant.INTRODUCED)),
						Optional.ofNullable(result.getDate(Constant.DISCONTINUED)),
						Optional.ofNullable(result.getInt(Constant.COMPAGNYID)));

				computersList.add(computer);
			}while (result.next());
			state.close();
			conn.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e1) {
				logger.error(ROLLBACK + e.getMessage());
			}
			logger.error(e.getMessage());
			
		}

		return computersList;


	}

	public ArrayList<Computer> getAll(){
		String query = SELECT_ALL;
		return getAll(query);
	}


	public ArrayList<Computer> getAll(int number, Long idBegin){
		StringBuilder sbuf = new StringBuilder();
		Formatter fmt = new Formatter(sbuf);
		fmt.format(SELECT_ID, Constant.ID,idBegin,number );
		String query = sbuf.toString() ;
		return getAll(query);
	}


	
	public ArrayList<Computer> search(String name, int number, Long idBegin){
		StringBuilder sbuf = new StringBuilder();
		Formatter fmt = new Formatter(sbuf);
		
		fmt.format(SELECT_SEARCH,name, Constant.ID,idBegin,number );
		String query = sbuf.toString() ;
		return getAll(query);
	}
		
	public ArrayList<Computer> search(String nameComputer, String nameCompany, int number, Long idBegin){
		CompanyDao computerDAO = CompanyDao.getInstance();
		String query = "SELECT * FROM computer";
		if ((nameCompany != null && nameCompany != "") ||
				(nameComputer != null && nameComputer != "")||
				(number != 0 || idBegin != 0)) {
			query+=" WHERE " + Constant.ID + " > " + idBegin ;
		}
		else {
			return getAll(query);
		}
		if ((nameCompany != null && nameCompany != "")) {
		ArrayList<Company> companies = computerDAO.search(nameCompany);
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
		
			introduced = c.getIntroduced()!= null ? "'" +c.getIntroduced().toString()+"'" : "null" ;
		
		
		String discontinued;
		
			discontinued = c.getDiscontinued() != null ? "'" +c.getDiscontinued().toString()+"'" : "null" ;
		

		Long companyId = null;
		if (c.getCompany() != null && c.getCompany().getId() != 0){
			companyId =  c.getCompany().getId();
		}
		PreparedStatement query = null;
		Connection conn = HikariCP.getInstance().getConnection();
		try {
		if (id > 0) { 
			StringBuilder sbuf = new StringBuilder();
			Formatter fmt = new Formatter(sbuf);
			
			fmt.format(ADD, id, name, introduced, discontinued, companyId );
			query = conn.prepareStatement(sbuf.toString() );
			
		}
		else { 
			StringBuilder sbuf = new StringBuilder();
			Formatter fmt = new Formatter(sbuf);
			fmt.format(ADD_WHITHOUT_ID,  name, introduced, discontinued, companyId );
			
			query = conn.prepareStatement(sbuf.toString() );
			
			}
		} catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
		}
		
		
		return HikariCP.getInstance().commit(query,conn, commit);
		
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
		Connection conn = HikariCP.getInstance().getConnection();
		PreparedStatement query = null;
		try {
			StringBuilder sbuf = new StringBuilder();
			Formatter fmt = new Formatter(sbuf);
			fmt.format(UPDATE, name, introduced, discontinued, companyId, oldId );
			
			query = conn.prepareStatement(sbuf.toString() );
			
		} catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
		}
		return HikariCP.getInstance().commit(query,conn,  commit);

	
	}


	public Boolean delete(Computer c, boolean commit) {
		return delete(c.getId(),  commit);
	}

	public Boolean delete(long id, boolean commit) {
		Connection conn = HikariCP.getInstance().getConnection();
		PreparedStatement query = null;
		try {
			StringBuilder sbuf = new StringBuilder();
			Formatter fmt = new Formatter(sbuf);
			fmt.format(DELETE, id);
			query = conn.prepareStatement(sbuf.toString() );
		} catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
		}
		return HikariCP.getInstance().commit(query, conn, commit);
	}


	public Optional<Computer> get(long computerID){

		Connection conn;
		try {
			conn = HikariCP.getInstance().getConnection();;
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			StringBuilder sbuf = new StringBuilder();
			Formatter fmt = new Formatter(sbuf);
			fmt.format(SELECT_ONE, computerID);
			
			String query = sbuf.toString() ;
			ResultSet result =  state.executeQuery(query);
			logger.info(sbuf.toString());
			ComputerFactory usineChinoise = new ComputerFactory();

			if (result.first()) {
				result.first();
				Computer computer = usineChinoise.createComputer(Optional.ofNullable(result.getInt(Constant.ID)),
						Optional.ofNullable(result.getString(Constant.NAME)),
						Optional.ofNullable(result.getDate(Constant.INTRODUCED)),
						Optional.ofNullable(result.getDate(Constant.DISCONTINUED)),
						Optional.ofNullable(result.getInt(Constant.COMPAGNYID)));
				state.close();
				conn.close();
				return Optional.ofNullable(computer ); //new Computer(id, name,companyPerID.get(company_id),introduced , discontinued));
			}
			else {
				return Optional.empty();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return Optional.empty();

	}


	private class ComputerFactory{

		HashMap<Long, Company> companyPerID;

		public ComputerFactory() {
			CompanyDao companySql = CompanyDao.getInstance();
			ArrayList<Company> companies = companySql.getAll();
			companyPerID = new HashMap<Long, Company>();
			for (Company company: companies) {
				companyPerID.put(company.getId(), company);
			}
		}

		private  Computer createComputer(Optional<Integer> idOptionnal,Optional<String> nameOptional, Optional<Date> introducedOptionnal, Optional<Date> DiscontinuedOptionnal, Optional<Integer> companyOptionnal) {
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
