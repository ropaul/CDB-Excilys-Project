package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.excilys.computerdatabase.Constant;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

//@Repository
public class ComputerDao {
	String url = "jdbc:mysql://localhost:3306/computer-database-db";
	Logger logger =LoggerFactory.getLogger(ComputerDao.class);

	@Autowired
	CompanyDao companyDao;

	HikariCP hikariCP = HikariCP.getInstance();

	private String ERROR_DURING_QUERY = "Error during creation of a query.";
	private String ROLLBACK           = "Can't do the rollback: ";
	private String SELECT_ALL         = "SELECT * FROM computer";
	private String SELECT_ID          = "SELECT * FROM computer WHERE " + Constant.ID + " > ? LIMIT  ?";
	private String SELECT_ONE         = "SELECT * FROM computer Where id = ?";
	private String SELECT_SEARCH      = "SELECT * FROM computer WHERE " + Constant.NAME + " LIKE '%?%' AND"  + Constant.ID + " > ? LIMIT ?";
	private String ADD                = "INSERT INTO computer  VALUES (?, '?',? ,?, ? )";
	private String ADD_WHITHOUT_ID    = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES ( '?',? ,?, ? )";
	private String DELETE             = "DELETE FROM computer  WHERE id = ?";
	private String UPDATE = "UPDATE  computer  SET " + Constant.NAME +" = ?, "+ 
			Constant.INTRODUCED +" = ?, " +
			Constant.DISCONTINUED +" = ?, " +
			Constant.COMPAGNYID +" = ? " +
			"WHERE " +  Constant.ID +" in (?);";



	private ArrayList<Computer> getAll(PreparedStatement query, Connection conn)   {
		ArrayList<Computer>  computersList = new ArrayList<Computer>();
		ArrayList<Company> companies;


		try {
			companies = companyDao.getAll();
			HashMap<Long, Company> companyPerID = new HashMap<Long, Company>();
			for (Company company: companies) {
				companyPerID.put(company.getId(), company);
			}



			ResultSet result =  query.executeQuery();
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
		Connection conn = hikariCP.getConnection();
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement(SELECT_ALL);
		} catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
		}
		return getAll(query, conn);
	}


	public ArrayList<Computer> getAll(int number, Long idBegin) {
		Connection conn = hikariCP.getConnection();
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement(SELECT_ID);
			query.setString(1, Constant.ID);
			query.setLong(2, idBegin);
			query.setLong(3, number);
		} catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
		}

		return getAll(query, conn);
	}



	public ArrayList<Computer> search(String name, int number, Long idBegin) throws SQLException{

		Connection conn = hikariCP.getConnection();
		PreparedStatement query = conn.prepareStatement(SELECT_SEARCH);
		query.setString(1, name);
		query.setString(2, Constant.ID);
		query.setLong(3, idBegin);
		query.setLong(4, number);
		return getAll(query, conn);
	}

	public ArrayList<Computer> search(String nameComputer, String nameCompany, int number, Long idBegin) {
		Connection conn = hikariCP.getConnection();
		String query = SELECT_ALL;
		if ((nameCompany != null && nameCompany != "") ||
				(nameComputer != null && nameComputer != "")||
				(number != 0 || idBegin != 0)) {
			query+=" WHERE " + Constant.ID + " > " + idBegin ;
		}
		else {
			try {
				return getAll(conn.prepareStatement(query), conn);
			} catch (SQLException e) {
				logger.error(ERROR_DURING_QUERY);
			}
		}
		if ((nameCompany != null && nameCompany != "")) {
			ArrayList<Company> companies = companyDao.search(nameCompany);
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
		try {
			return getAll(conn.prepareStatement(query), conn);
		} catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
		}
		return new ArrayList<Computer>();
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
		Connection conn = hikariCP.getConnection();
		try {
			if (id > 0) { 

				query = conn.prepareStatement(ADD );
				query.setLong(1, id);
				query.setString(2, name);
				query.setString(3, introduced);
				query.setString(4, discontinued);
				query.setLong(5, companyId);

			}
			else { 
				query = conn.prepareStatement(ADD_WHITHOUT_ID );
				query.setString(1, name);
				query.setString(2, introduced);
				query.setString(3, discontinued);
				query.setLong(4, companyId);

			}
		} catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
			return false;
		}


		return hikariCP.commit(query,conn, commit);


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
		Connection conn = hikariCP.getConnection();
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement(UPDATE );
			query.setString(1, name);
			query.setString(2, introduced);
			query.setString(3, discontinued);
			query.setLong(4, companyId);
			query.setLong(5, oldId);

		} catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
		}
		return hikariCP.commit(query,conn,  commit);


	}


	public Boolean delete(Computer c, boolean commit, Connection conn) {
		return delete(c.getId(),  commit, conn);
	}

	public Boolean delete(long id, boolean commit,Connection conn) {
		if (conn == null) {
			conn = hikariCP.getConnection();
		}
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement(DELETE );
			query.setLong(1, id);

		} catch (SQLException e) {
			logger.error(ERROR_DURING_QUERY);
		}
		return hikariCP.commit(query, conn, commit);
	}


	public Optional<Computer> get(long computerID){

		Connection conn;
		try {
			conn = hikariCP.getConnection();;
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			PreparedStatement query = conn.prepareStatement(SELECT_ONE);
			query.setLong(1, computerID);

			ResultSet result =  query.executeQuery();
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
		
		@Autowired
		CompanyDaoSpring companyDao ;


		public ComputerFactory() {
			CompanyDao companyDaoSpring = new CompanyDao();
			ArrayList<Company> companies = companyDaoSpring.getAll();
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
