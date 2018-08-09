package com.excilys.formation.controller;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;




@Controller
@RequestMapping("/addComputer")
public class AddComputerController {

	
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
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String home(ModelMap model,@RequestParam(name = NAME,required = false) String name,
			@RequestParam(name = INTRODUCED,required = false) String introduced,
			@RequestParam(name =DISCONTINUED,required = false) String discontinued,
			@RequestParam(name = ID_COMPANY,required = false) String idCompany) {
		String resultat;
		
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
			
			if (computerService.add(computer )== false){
				resultat = "error";
				model.addAttribute( ATT_RESULTAT, resultat );
				
				return "error";
			}
			resultat = "Succès de l'inscription.";
			model.addAttribute( ATT_RESULTAT, resultat );
			
			
			return "/dashboard";
		} 
		else {
			ArrayList<Company> companies= companyService.getAll();
			model.addAttribute( "companies", companies );
			resultat = "Échec de l'inscription.";
			model.addAttribute( ATT_ERREURS, erreurs );
			model.addAttribute( ATT_RESULTAT, resultat );
			return "addComputer";
		}
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String home(ModelMap model) {
		return "addComputer";
	}
	
}
