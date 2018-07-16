package com.excilys.computerdatabase.servlet;


import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VUE          = "/static/jsp/addComputer.jsp";
	public static final String NAME  = "name";
	public static final String INTRODUCED   = "introduced";
	public static final String DISCONTINUED = "discontinued";
	public static final String ID_COMPANY  = "companyId";
	public static final String ATT_ERREURS  = "erreurs";
	public static final String ATT_RESULTAT = "resultat";
	@Autowired
	CompanyService companyService;
	@Autowired
	ComputerService computerService;

	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
}
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException{
		ArrayList<Company> companies = new ArrayList<>();
		
		companies = companyService.getAll();
		request.setAttribute( "companies", companies );
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}


	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		String resultat;
		String name = request.getParameter( NAME);
		String introduced = request.getParameter( INTRODUCED );
		String discontinued = request.getParameter( DISCONTINUED );
		String idCompany = request.getParameter( ID_COMPANY );
		Validation validateur = Validation.getInstance();


		HashMap<String, String> erreurs = validateur.AddAndEditValidation(name, introduced, discontinued, idCompany);



		if ( erreurs.isEmpty() ) {
			Company company;
			
			company = companyService.get(Long.parseLong(idCompany));
			Date dateIntroduced = null, dateDiscontinued = null;
			if (introduced != null && introduced != "" ) {
				dateIntroduced = Date.valueOf(introduced);
			}
			if (discontinued != null && discontinued != "") {
				dateDiscontinued = Date.valueOf(discontinued);
			}
			Computer computer = new Computer(name, company, dateIntroduced, dateDiscontinued);
			
			computerService.add(computer);
			resultat = "Succès de l'inscription.";
			request.setAttribute( ATT_RESULTAT, resultat );
			RequestDispatcher rd = request.getRequestDispatcher("dashboard");
			rd.forward(request,response);
		} 
		else {
			ArrayList<Company> companies= CompanyService.getInstance().getAll();
			request.setAttribute( "companies", companies );
			resultat = "Échec de l'inscription.";
			request.setAttribute( ATT_ERREURS, erreurs );
			request.setAttribute( ATT_RESULTAT, resultat );
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

		}

	}



}
