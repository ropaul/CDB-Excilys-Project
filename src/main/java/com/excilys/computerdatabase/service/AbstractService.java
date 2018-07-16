package com.excilys.computerdatabase.service;

public abstract class AbstractService {

	private static CompanyService companyService;
	private static ComputerService computerService;
	
	
	public static CompanyService getCompanyService() {
		return companyService;
	}
	public static void setCompanyService(CompanyService companyService) {
		AbstractService.companyService = companyService;
	}
	public static ComputerService getComputerService() {
		return computerService;
	}
	public static void setComputerService(ComputerService computerService) {
		AbstractService.computerService = computerService;
	}
	
}
