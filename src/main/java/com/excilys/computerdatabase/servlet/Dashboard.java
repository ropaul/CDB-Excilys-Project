package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.Constant;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.ComputerPage;
import com.excilys.computerdatabase.service.SqlManager;




@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComputerPage page;

	public void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException{
		/* Création et initialisation du message. */

		String paramPage = request.getParameter( "page" );
		
		


		SqlManager manager;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		
		try {
			manager = SqlManager.getInstance();
			computers = manager.getComputers();
			if (page == null) {
			page = new ComputerPage(Constant.NB_PAGE, 0);
			
			if (paramPage != null) {
				int np = Integer.parseInt(paramPage);
				while (page != null && page.getNumberOfPage() != np ) {
					if(page.getNumberOfPage() < np) {
						page = page.getNextPage();
					}
					else if(page.getNumberOfPage() > np) {
						page = page.getPreviousPage();
					}
				}
				
			}
			}

		} catch (SQLException e) {
			System.err.println(e);
		}
		
		
		
		request.setAttribute( "computers", page.getPage() );
		request.setAttribute( "nbComputers", computers.size() );



		this.getServletContext().getRequestDispatcher( "/static/jsp/dashboard.jsp" ).forward( request, response );
	}

}
