package com.excilys.computerdatabase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;



public class DeleteController {

	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	ComputerService computerService;

	Logger logger = LoggerFactory.getLogger(DeleteController.class);
	
	
	@RequestMapping(method = RequestMethod.GET)
	public RedirectView home()
	{	
		
		
		return new RedirectView("dashboard");
		}
	
	
	@RequestMapping(value = "dashboard", method = RequestMethod.POST)
	public RedirectView homePage(ModelMap model, @RequestParam(name ="selection",required = false) String paramSelected, 
			@RequestParam(name ="selectionCompany",required = false) String paramSelectedCompany) {
		
		if(paramSelected != null && paramSelected != "") {
			deleteComputer(paramSelected, logger);
		} 
		if(paramSelectedCompany != null && paramSelectedCompany != "") {
			deleteCompany(Long.parseLong(paramSelectedCompany), logger);
		}
		return new RedirectView("dashboard");
		}

	public void deleteCompany(Long idCompany, Logger logger) {
		
		Company company = companyService.get(idCompany);
		companyService.delete(company);
		logger.info("Company delete:" + company);
	}
	
	public void deleteComputer(String selection, Logger logger) {
		for (String value: selection.split(",")) {
			computerService.delete(Long.parseLong(value));
			logger.info("Computer delete. id =" + value);
		}
	}
}