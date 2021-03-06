package com.excilys.computerdatabase.servlet;

import java.sql.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.service.CompanyService;

public class Validation {

	private static Validation INSTANCE;
	private Logger logger;
	public static final String VUE          = "/static/jsp/addComputer.jsp";
	public static final String NAME  = "name";
	public static final String INTRODUCED   = "introduced";
	public static final String DISCONTINUED = "discontinued";
	public static final String ID_COMPANY  = "companyId";
	public static final String ATT_ERREURS  = "erreurs";
	public static final String ATT_RESULTAT = "resultat";
	CompanyService manager = CompanyService.getInstance();

	private Validation() 
	{
		logger = LoggerFactory.getLogger(Validation.class);
	}

	public static Validation getInstance() 
	{   
		if (INSTANCE == null){   
			synchronized(Validation.class){
				if (INSTANCE == null){
					INSTANCE = new Validation();
				}
			}
		}
		return INSTANCE;
	}



	public HashMap<String, String> AddAndEditValidation(String name, String introduced, String discontinued, String idCompany){
		HashMap<String, String> erreurs = new HashMap<String, String>();
		try {
			validationNom(name);
		} catch ( ValidationException e ) {
			erreurs.put( NAME, e.getMessage() );
			logger.error(e.getMessage());
		}

		try {
			validationDate(introduced );
		} catch ( ValidationException e ) {
			erreurs.put(INTRODUCED, e.getMessage() );
			logger.error(e.getMessage());
		}

		try {
			validationDate(discontinued);
		} catch ( ValidationException e ) {
			erreurs.put( DISCONTINUED, e.getMessage() );
			logger.error(e.getMessage());
		}

		try {
			validationCompany(idCompany);
		} catch ( ValidationException e ) {
			erreurs.put( ID_COMPANY, e.getMessage() );
			logger.error(e.getMessage());
		}

		return erreurs;
	}

	private void validationNom( String nom ) throws ValidationException {
		if ( nom != null && nom.length() <= 0 ) {
			throw new ValidationException( "Name must be defined" );
		}

	}

	private void validationDate(  String stringDate ) throws ValidationException {
		String errorMessage = "Date is not correct. Date = " + stringDate;
		try {
			if (stringDate == null || stringDate.replace(" ", "") == "") return;
			if ( stringDate != null && !( Date.valueOf(stringDate) instanceof Date)  ) {
				throw new ValidationException(errorMessage);
				
			}
		}
		catch (Exception e) {
			throw new ValidationException( errorMessage );
		}
	}



	private void validationCompany(  String idCompany ) throws ValidationException {
		if (idCompany == null || idCompany == "" || Long.parseLong(idCompany) == 0L) return;
		try {
			
			Company company = manager.get(Long.parseLong(idCompany));
			if ( company == null  ) {
				throw new ValidationException( "Company doesn't exists" );
			}
		}
		catch (Exception e) {
			throw new ValidationException( "Company doesn't exists" );
		}
	}

}
