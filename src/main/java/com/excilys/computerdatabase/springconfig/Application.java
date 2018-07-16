package com.excilys.computerdatabase.springconfig;

import javax.inject.Scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.HikariCP;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@Configuration
@ComponentScan
public class Application {



	@Bean("companyDao")
	public CompanyDao getCompanyDao()
	{
		return CompanyDao.getInstance();
	}

	@Bean("computerDao")
	public ComputerDao getComputerDao()
	{
		return ComputerDao.getInstance();
	}

	@Bean("companyService")
	public CompanyService getCompanyService()
	{
		return CompanyService.getInstance();
	}

	@Bean("computerService")
	public ComputerService getComputerService()
	{
		return ComputerService.getInstance();
	}
	
	
	@Bean("HikariCp")
	public HikariCP getDataSource()
	{
		HikariCP dataSource = HikariCP.getInstance();

		return dataSource;
	}
}

