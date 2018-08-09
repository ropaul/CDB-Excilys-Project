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
@RequestMapping("/editComputer")
public class editComputerController {



	public static final String VUE          = "/static/jsp/editComputer.jsp";
	public static final String NAME  = "name";
	public static final String INTRODUCED   = "introduced";
	public static final String DISCONTINUED = "discontinued";
	public static final String ID_COMPANY  = "companyId";
	public static final String ID_COMPUTER  = "computerId";
	public static final String ATT_ERREURS  = "erreurs";
	public static final String ATT_RESULTAT = "resultat";
	@Autowired
	CompanyService companyService;
	@Autowired
	ComputerService computerService;
	@Autowired
	Validation validation;

	@RequestMapping(method = RequestMethod.POST)
	public String home(ModelMap model,@RequestParam(name = NAME,required = false) String name,
			@RequestParam(name = ID_COMPUTER,required = false) String idComputer,
			@RequestParam(name = INTRODUCED,required = false) String introduced,
			@RequestParam(name =DISCONTINUED,required = false) String discontinued,
			@RequestParam(name = ID_COMPANY,required = false) String idCompany) {
		String resultat;






		HashMap<String, String> erreurs = validation.AddAndEditValidation(name, introduced, discontinued, idCompany);

		Company company = null;
		if ( erreurs.isEmpty() ) {

			if (idCompany != null && idCompany!= "") {
				long longIdCompany = Long.parseLong(idCompany);
				company = companyService.get(longIdCompany);			
			}
			Date dateIntroduced = null, dateDiscontinued = null;
			if (introduced != null && introduced != "" ) {
				dateIntroduced = Date.valueOf(introduced);
			}
			if (discontinued != null && discontinued != "") {
				dateDiscontinued = Date.valueOf(discontinued);
			}
			long longComputerID = Long.parseLong(idComputer);
			Computer computer = new Computer(longComputerID, name, company, dateIntroduced, dateDiscontinued);

			if(computerService.update(computer) ==false) {
				resultat = "error.";
				model.addAttribute( ATT_RESULTAT, resultat );
				return "error";
			}

			resultat = "Succès de l'inscription.";
			model.addAttribute(ATT_ERREURS, erreurs );
			model.addAttribute( ATT_RESULTAT, resultat );
			return "dashboard";
		} else {

			ArrayList<Company> companies = new ArrayList<>();
			companies = companyService.getAll();
			Computer computer =  new Computer();
			computer = computerService.get(Long.parseLong(idComputer));


			model.addAttribute( "computer", computer );
			model.addAttribute( "companies", companies );
			companies= companyService.getAll();
			model.addAttribute( "companies", companies );
			resultat = "Échec de l'inscription.";
			model.addAttribute( ATT_ERREURS, erreurs );
			model.addAttribute( ATT_RESULTAT, resultat );
			return "addComputer";
		}

	}


	@RequestMapping(method = RequestMethod.GET)
	public String home(ModelMap model, @RequestParam(name = ID_COMPUTER,required = false) String idComputer) {
		if (idComputer != null) {
		model.addAttribute( "computer", computerService.get(Long.parseLong(idComputer)));
		}
		return "editComputer";
	}

}
