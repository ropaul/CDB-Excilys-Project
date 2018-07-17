package com.excilys.computerdatabase.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.HikariCP;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@Configuration
@ComponentScan(basePackages ="{com.excilys.computerdatabase.persistence,"
		+ "com.excilys.computerdatabase.service,"
		+ "com.excilys.computerdatabase.model,"
		+ "com.excilys.computerdatabase.servlet}")
public class Application {



	@Bean("companyDao")
	public CompanyDao getCompanyDao()
	{
		return new CompanyDao();
	}

	@Bean("computerDao")
	public ComputerDao getComputerDao()
	{
		return new ComputerDao();
	}

	@Bean("companyService")
	public CompanyService getCompanyService()
	{
		return new CompanyService();
	}

	@Bean("computerService")
	public ComputerService getComputerService()
	{
		return new ComputerService();
	}
	
	
	@Bean("HikariCp")
	public HikariCP getDataSource()
	{
		HikariCP dataSource = HikariCP.getInstance();

		return dataSource;
	}
}

