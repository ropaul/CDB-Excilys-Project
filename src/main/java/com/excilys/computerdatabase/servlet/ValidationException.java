package com.excilys.computerdatabase.servlet;

public class ValidationException extends Exception{
	
	/**
	 * 
	 */
	
	String message;
	
	private static final long serialVersionUID = 1L;

	public ValidationException() {
		super("Error during validation");
		
	}

	
	public ValidationException(String message) {
		super(message);
	}
	
}
