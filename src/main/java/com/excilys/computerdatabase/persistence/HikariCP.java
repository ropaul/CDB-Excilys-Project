package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class HikariCP {



	static Logger logger;
	private static HikariCP INSTANCE = null;
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;
	String url = "jdbc:mysql://localhost:3306/computer-database-db";
	
	private String NOT_ROLLBACK = "Can't do the rollback: " +"\nfirst error:";
	private String ROLLBACK           = "Can't do the rollback: ";


	private HikariCP(){

		logger = LoggerFactory.getLogger(HikariCP.class);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		
		config.setJdbcUrl( url );
		config.setUsername( "root" );
		config.setPassword( "root" );
		config.addDataSourceProperty( "cachePrepStmts" , "true" );
		config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
		config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
		config.setAutoCommit(false);
		ds = new HikariDataSource( config );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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




	public Connection getConnection()  {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			logger.error("Doesn't be able to get a connection");
		}
		return null;
	}


	public boolean commit(PreparedStatement query,Connection conn, boolean commit) {
		try {
			query.execute();
			if (commit) {
				conn.commit();
				conn.close();
			}
			
			
			return true;
		} catch (SQLException e) {
			try {
				conn.rollback();
				conn.close();
				logger.error(ROLLBACK + e.getMessage());
				return false;

			} catch (SQLException e1) {
				logger.error( NOT_ROLLBACK + e.getMessage()+"\nsecond error:"+ e1.getMessage());
				return false;
			}
		}
	}


}
