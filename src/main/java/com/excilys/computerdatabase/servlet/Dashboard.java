package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.Constant;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.ComputerPage;
import com.excilys.computerdatabase.service.ComputerService;



@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	private ComputerPage page;
	private int nbPage = Constant.NB_PAGE;
	Logger logger = LoggerFactory.getLogger(Dashboard.class);
	@Autowired
	ComputerService computerService ;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
}
	

	public void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException{

	
		String paramPage = request.getParameter("page");
		String paramNbPerPage = request.getParameter("perpage");
		String paramSearch = request.getParameter("search");

		
		if (paramNbPerPage != null) {
			nbPage =  Integer.parseInt(paramNbPerPage);
		}


		ArrayList<Computer> computers = new ArrayList<Computer>();

		computers = computerService.getAll();

		if (paramPage != null) {
			page = new ComputerPage(paramSearch, paramSearch, nbPage, 0L, computerService);

			CHOICE: switch(paramPage) {
			case "n":
				if (page.getNextPage() != null){
					page = page.getNextPage();
				}
				break;
			case "p":

				if (page.getPreviousPage() != null){
					page = page.getPreviousPage();
				}
				break;

			default :
				try {
					int np = Integer.parseInt(paramPage);
					while (page.hasNext() && page.getNumberOfPage() != np ) {
						if(page.getNumberOfPage() < np) {
							if (page.getNextPage() == null) {
								break CHOICE;
							}
							page = page.getNextPage();
						}
						else if(page.getNumberOfPage() > np) {
							if (page.getPreviousPage() == null) {
								break CHOICE;
							}
							page = page.getPreviousPage();
						}
					}
				} catch (NumberFormatException e) {
					logger.error("Page is not a number. value of page = "+ paramPage);
				}

			}

		}
		else {
			page = new ComputerPage(paramSearch, paramSearch, nbPage, 0L, computerService);
		}

		request.setAttribute( "computers", page.getPage() );
		request.setAttribute( "nbComputers", computers.size() );
		request.setAttribute( "numeropage", page.getNumberOfPage() );
		request.setAttribute( "search", paramSearch );

		this.getServletContext().getRequestDispatcher( "/static/jsp/dashboard.jsp" ).forward( request, response );
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		this.doGet(request, response);
	}


}
