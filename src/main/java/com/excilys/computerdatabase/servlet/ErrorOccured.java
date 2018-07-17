package com.excilys.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/error")
public class ErrorOccured extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	
	

	public void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException{
		this.getServletContext().getRequestDispatcher( "/static/views/500.html" ).forward( request, response );
		
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		this.doGet(request, response);
	}
}