package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.Constant;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

public class ComputerDao {
	String url = "jdbc:mysql://localhost:3306/computer-database-db";
	Logger logger;
	private static ComputerDao INSTANCE = null;


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
		try {
			companies = companySql.getAll();
			HashMap<Long, Company> companyPerID = new HashMap<Long, Company>();
			for (Company company: companies) {
				companyPerID.put(company.getId(), company);
			}
			Connection conn = HikariCP.getInstance().getConnection();;
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			logger.info("Connection to get all the computers");

			ResultSet result =  state.executeQuery(query);
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
			logger.error("can't find all the computers");
		}

		return computersList;


	}

	public ArrayList<Computer> getAll(){
		String query = "SELECT * FROM computer";
		return getAll(query);
	}


	public ArrayList<Computer> getAll(int number, Long idBegin){
		String query = "SELECT * FROM computer WHERE " + Constant.ID + " >" + idBegin + " LIMIT " + number  ;
		return getAll(query);
	}

	public ArrayList<Computer> search(String name){
		String query = "SELECT * FROM computer WHERE " + Constant.NAME + " LIKE '%" + name + "%' " ;
		return getAll(query);
	}
	
	public ArrayList<Computer> search(String name, int number, Long idBegin){
		String query = "SELECT * FROM computer WHERE " + Constant.NAME + " LIKE '%" + name + "%' AND"  + Constant.ID + " >" + idBegin + " LIMIT " + number ;
		return getAll(query);
	}
		
	public ArrayList<Computer> search(String nameComputer, String nameCompany, int number, Long idBegin){
		CompanyDao computerDAO = CompanyDao.getInstance();
		ArrayList<Company> companies = computerDAO.search(nameCompany);
		int[] idCompany = new int[companies.size()];
		for (int i = 0;  i< companies.size(); i++) {
			idCompany[i] = (int) companies.get(i).getId();
		}
		System.out.println(idCompany);
		String query = "SELECT * FROM computer WHERE " + 
				Constant.NAME + " LIKE '%" + nameComputer + "%'"+
				" AND"  + Constant.ID + " >" + idBegin + " LIMIT " + number +
				"AND company_id in" + idCompany + " LIMIT " + number;
		return getAll(query);
	}
	public Boolean add(Computer c){
		long id = c.getId();
		String name = c.getName();
		String introduced;
		try {
			introduced = "'" +c.getIntroduced().toString()+"'";
		} catch (Exception e) {
			introduced = "null";
		}
		String discontinued;
		try {
			discontinued = "'" +c.getDiscontinued().toString()+"'";
		} catch (Exception e) {
			discontinued = "null";
		}

		Long companyId = null;
		if (c.getCompany() != null){
			companyId =  c.getCompany().getId();
		}
		String query;
		if (id > 0) {  query = "INSERT INTO computer  VALUES (" +
				id + ", '" + name + "'," + introduced  + "," + discontinued + "," + companyId + ")";
		}
		else { query = "INSERT INTO computer  (name, introduced, discontinued, company_id) VALUES ( '" 
				+ name + "'," + introduced  + "," + discontinued + "," + companyId + ")";
		}
		Statement state;
		try {
			Connection conn = HikariCP.getInstance().getConnection();;
			state = conn.createStatement();
			logger.info("Connection to add the computer id ="+ id);
			state.executeUpdate(query);
			state.close();
			conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		return true;
	}

	
	public Boolean update( Computer newComputer)  {
		long oldId = newComputer.getId();
		String name = "'" +newComputer.getName() +"'" ;
		String introduced ;
		try {
			introduced = "'" + newComputer.getIntroduced().toString()+"'";
		} catch (Exception e) {
			introduced = null;
		}
		String discontinued;
		try {
			discontinued = "'" +newComputer.getDiscontinued().toString()+"'";
		} catch (Exception e) {
			discontinued = null;
		}
		Long companyId = null;
		if (newComputer.getCompany() != null){
			companyId =  newComputer.getCompany().getId();
		}
		String query = "UPDATE  computer  SET " + Constant.NAME +" = "  + name + ", "+ 
				Constant.INTRODUCED +" = "  + introduced  + ", " +
				Constant.DISCONTINUED +" = "  + discontinued + ", " +
				Constant.COMPAGNYID +" = "  + companyId +" " +
				"WHERE " +  Constant.ID +" in ("  + oldId +");";
		Statement state;
		try {
			Connection conn = HikariCP.getInstance().getConnection();;
			state = conn.createStatement();
			logger.info("Connection to create the computer id = " + companyId);
			state.executeUpdate(query);
			state.close();
			conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		return true;
	}


	public Boolean delete(Computer c) {
		return delete(c.getId());
	}

	public Boolean delete(long id) {
		String query = "DELETE FROM computer  WHERE id = " + id ;
		try {
			Connection conn = HikariCP.getInstance().getConnection();;
			Statement state;
			state = conn.createStatement();
			logger.info("Connection to delete the computer id = " + id);
			state.executeUpdate(query);
			state.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}


	public Optional<Computer> get(long computerID){

		Connection conn;
		try {
			conn = HikariCP.getInstance().getConnection();;
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT * FROM computer Where id =" + computerID;
			ResultSet result =  state.executeQuery(query);
			logger.info("Connection to get the computer id = " + computerID);
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
	
	
public static void main(String[] args) {
	ComputerDao computerd = ComputerDao.getInstance();
	System.out.println(computerd.getAll(10, 1000L));
	}

}
