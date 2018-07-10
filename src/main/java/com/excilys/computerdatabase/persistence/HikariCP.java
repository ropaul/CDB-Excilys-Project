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
		config.setAutoCommit(false);
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

	
	public boolean commit(String query) {
		Statement state;
		Connection conn = null;
		try {
			conn = HikariCP.getInstance().getConnection();;
			state = conn.createStatement();
			state.executeUpdate(query);
			state.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			try {
				conn.rollback();
				conn.close();
				logger.error("Can't commit but can do rollback: " + e.getMessage());
				return false;
				
			} catch (SQLException e1) {
				logger.error("Can't do the rollback: " + e1.getMessage());
				return false;
			}
		}
	}
	
	
}
