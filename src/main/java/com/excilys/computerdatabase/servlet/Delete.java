package com.excilys.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;


@WebServlet("/delete")
public class Delete extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CompanyService cs = CompanyService.getInstance();

	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		
		Logger logger = LoggerFactory.getLogger(Dashboard.class);
		String paramSelected = request.getParameter( "selection" );
		logger.info(paramSelected);
		RequestDispatcher rd = request.getRequestDispatcher("dashboard");
		rd.forward(request,response);
		}
	
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		Logger logger = LoggerFactory.getLogger(Dashboard.class);
		String paramSelected = request.getParameter( "selection" );
		String paramSelectedCompany = request.getParameter( "selectionCompany" );
		if(paramSelected != null && paramSelected != "") {
			deleteComputer(paramSelected, logger);
		}
		if(paramSelectedCompany != null && paramSelectedCompany != "") {
			deleteCompany(Long.parseLong(paramSelectedCompany), logger);
		}
		RequestDispatcher rd = request.getRequestDispatcher("dashboard");
		rd.forward(request,response);
		}

	public void deleteCompany(Long idCompany, Logger logger) {
		
		Company company = cs.get(idCompany);
		cs.delete(company);
		logger.info("Company delete:" + company);
	}
	
	public void deleteComputer(String selection, Logger logger) {
		ComputerService computerService = ComputerService.getInstance();
		for (String value: selection.split(",")) {
			computerService.delete(Long.parseLong(value));
			logger.info("Computer delete. id =" + value);
		}
	}
}
