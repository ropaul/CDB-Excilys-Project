package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.SqlManager;

@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException{
		/* Création et initialisation du message. */

		


		SqlManager manager;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		try {
			manager = SqlManager.getInstance();
			computers = manager.getComputers();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute( "computers", computers );
		request.setAttribute( "nbComputers", computers.size() );



		this.getServletContext().getRequestDispatcher( "/static/jsp/addComputer.jsp" ).forward( request, response );
	}

}
