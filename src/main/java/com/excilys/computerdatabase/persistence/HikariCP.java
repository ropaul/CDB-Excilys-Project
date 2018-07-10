package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.Constant;
import com.excilys.computerdatabase.model.Company;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCP {

	
	
	static Logger logger;
	private static HikariCP INSTANCE = null;
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;
	String url = "jdbc:mysql://localhost:3306/computer-database-db";


	private HikariCP(){
		
		logger = LoggerFactory.getLogger(HikariCP.class);
		
		config.setJdbcUrl( url );
		config.setUsername( "root" );
		config.setPassword( "root" );
		config.addDataSourceProperty( "cachePrepStmts" , "true" );
		config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
		config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
		ds = new HikariDataSource( config );
	
	}
	
	public static HikariCP getInstance() 
	{   
		if (INSTANCE == null){   
			synchronized(HikariCP.class){
				if (INSTANCE == null){
					INSTANCE = new HikariCP();
				}
			}
		}
		return INSTANCE;
	}

	


	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public static void main(String[] args) throws SQLException {
		HikariCP hikari =  HikariCP.getInstance();
		int  companyID = 1;


		Connection conn = hikari.getConnection();
		Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String query = "SELECT * FROM company Where id =" + companyID;
		ResultSet result =  state.executeQuery(query);
		Company companyResult;
		if (result.first()) {
			System.out.println(result);
			companyResult = new Company(result.getInt(Constant.ID), result.getString(Constant.NAME));
			System.out.println(companyResult);
			state.close();
			conn.close();
		}
	}
}
