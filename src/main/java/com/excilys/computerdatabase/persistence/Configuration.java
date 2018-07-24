package com.excilys.computerdatabase.persistence;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Configuration {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	public static final String USER_NAME = "admincdb";
public static final String PASSWORD = "qwerty1234";

public static DriverManagerDataSource getDataSource() {
	String DRIVER = "com.mysql.jdbc.Driver";
	String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	String USER_NAME = "admincdb";
	String PASSWORD = "qwerty1234";
	DriverManagerDataSource dataSource=  new DriverManagerDataSource();
	dataSource.setDriverClassName(DRIVER);
	dataSource.setUsername(USER_NAME);
	dataSource.setUrl(URL);
	dataSource.setPassword(PASSWORD);
	return dataSource;
}
}
