package com.excilys.computerdatabase.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;

import com.excilys.computerdatabase.Constant;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

;


/**
 * 
 * @author Yann
 *
 */
public class SqlManager {



	private Connection conn;


	private SqlManager() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/computer-database-db";

	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, "root", "root");
	} catch (ClassNotFoundException e) {
		System.err.println(e);
	}

		
//		conn = DriverManager.getConnection(url, "admincdb", "qwerty1234");
	}


	private static SqlManager INSTANCE = null;

	/** Access point to a new instance of SqlManager 
	 * @throws SQLException 
	 * @throws ClassNotFoundException */
	public static SqlManager getInstance() throws SQLException
	{   
		if (INSTANCE == null)
		{   
			synchronized(SqlManager.class)
			{
				if (INSTANCE == null)
				{   INSTANCE = new SqlManager();
				}
			}
		}
		return INSTANCE;
	}

	/**
	 * take a query which must ask for computer and give them as an array of Computer class
	 * @param query
	 * @return list a computer
	 * @throws SQLException
	 */
	private ArrayList<Computer> getComputers(String query) throws SQLException{
		ArrayList<Computer>  computersList = new ArrayList<Computer>();
		ArrayList<Company> companies = this.getCompanies();
		HashMap<Integer, Company> companyPerID = new HashMap<Integer, Company>();
		for (Company company: companies) {
			companyPerID.put(company.getId(), company);
		}
		Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet result =  state.executeQuery(query);
		result.first();


		do{
			int id = result.getInt(Constant.ID);
			String name = result.getString(Constant.NAME);
			int company_id;

			Date introduced, discontinued;
			try {
				introduced = result.getDate(Constant.INTRODUCED);
			}
			catch (SQLException e) {
				introduced = null;
			}
			try {
				discontinued = result.getDate(Constant.DISCONTINUED);
			}
			catch (SQLException e) {
				discontinued = null;
			}
			try {
				company_id = result.getInt(Constant.COMPAGNYID);
			}
			catch (SQLException e) {
				company_id = 0;
			}

			computersList.add(new Computer(id, name,companyPerID.get(company_id),introduced , discontinued));
		}while (result.next());
		return computersList;
	}

	/**
	 * ass for all the computers
	 * @return array of computer as Computer class
	 * @throws SQLException
	 */
	public ArrayList<Computer> getComputers() throws SQLException{
		String query = "SELECT * FROM computer";
		return getComputers(query);
	}

	/**
	 * ask for number of computer with computer which as at least and id superior to idBegin
	 * @param number
	 * @param idBegin
	 * @return array of computers
	 * @throws SQLException
	 */
	public ArrayList<Computer> getComputers(int number, int idBegin) throws SQLException{
		String query = "SELECT * FROM computer WHERE " + Constant.ID + " >" + idBegin + " LIMIT " + number  ;
		return getComputers(query);
	}


	public ArrayList<Company> getCompanies() throws SQLException{
		ArrayList<Company>  companyList = new ArrayList<Company>();
		Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM company";
		ResultSet result =  state.executeQuery(query);
		result.first();
		do{

			companyList.add(new Company(result.getInt(Constant.ID), result.getString(Constant.NAME)));
		}while (result.next());
		return companyList;
	}

	public Company getCompany(int companyID) throws SQLException{
		Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM company Where id =" + companyID;
		ResultSet result =  state.executeQuery(query);
		if (result.first()) {
			result.first();
			return new Company(result.getInt(Constant.ID), result.getString(Constant.NAME));
		}
		else {
			return null;
		}
	}

	public Computer getComputer(int computerID) throws SQLException{
		Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM computer Where id =" + computerID;
		ResultSet result =  state.executeQuery(query);
		ArrayList<Company> companies = this.getCompanies();
		HashMap<Integer, Company> companyPerID = new HashMap<Integer, Company>();
		for (Company company: companies) {
			companyPerID.put(company.getId(), company);
		}
		if (result.first()) {
			result.first();
			int id = result.getInt(Constant.ID);
			String name = result.getString(Constant.NAME);
			int company_id;

			Date introduced, discontinued;
			try {
				introduced = result.getDate(Constant.INTRODUCED);
			}
			catch (SQLException e) {
				introduced = null;
			}
			try {
				discontinued = result.getDate(Constant.DISCONTINUED);
			}
			catch (SQLException e) {
				discontinued = null;
			}
			try {
				company_id = result.getInt(Constant.COMPAGNYID);
			}
			catch (SQLException e) {
				company_id = 0;
			}
			return new Computer(id, name,companyPerID.get(company_id),introduced , discontinued);
		}
		else {
			return null;
		}
	}


	public Boolean createComputer(Computer c) throws SQLException {
		int id = c.getId();
		String name = c.getName();
		String introduced;
		try {
			introduced = "'" +c.getIntroduced().toString()+"'";
		} catch (Exception e) {
			introduced = null;
		}
		String discontinued;
		try {
			discontinued = "'" +c.getDiscontinued().toString()+"'";
		} catch (Exception e) {
			discontinued = null;
		}

		Integer companyId = null;
		if (c.getCompany() != null){
			companyId =  c.getCompany().getId();
		}
		String query = "INSERT INTO computer  VALUES (" + id + ", '" + name + "'," + introduced  + "," + discontinued + "," + companyId + ")";
		Statement state = conn.createStatement();
		state.executeUpdate(query);
		return true;
	}

	public Boolean updateComputer( Computer newComputer) throws SQLException {
		int oldId = newComputer.getId();
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
		Integer companyId = null;
		if (newComputer.getCompany() != null){
			companyId =  newComputer.getCompany().getId();
		}
		String query = "UPDATE  computer  SET " + Constant.NAME +" = "  + name + ", "+ 
				Constant.INTRODUCED +" = "  + introduced  + ", " +
				Constant.DISCONTINUED +" = "  + discontinued + ", " +
				Constant.COMPAGNYID +" = "  + companyId +" " +
				"WHERE " +  Constant.ID +" in ("  + oldId +");";
		Statement state = conn.createStatement();
		state.executeUpdate(query);
		return true;
	}


	public Boolean deleteComputer(Computer c) throws SQLException {
		return deleteComputer(c.getId());
	}
	
	public Boolean deleteComputer(int id) throws SQLException {
		String query = "DELETE FROM computer  WHERE id = " + id ;
		Statement state = conn.createStatement();
		state.executeUpdate(query);
		return true;
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException  {
		
		SqlManager manager = new SqlManager();
//		ArrayList<Computer> computers = manager.getComputers(10,0);
//		Computer c1 =  new Computer (1002, "testy");
//		manager.createComputer(c1);
//		Computer c2 =  new Computer (1002, "L update a fonctionner");
//		manager.updateComputer( c2);
		Computer c = manager.getComputer(1002);
		System.out.println(c);
//


	}

}
