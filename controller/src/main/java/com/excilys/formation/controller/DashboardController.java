package com.excilys.formation.controller;



import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.Constant;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.ComputerService;



@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	private ComputerPage page;
	private int nbPage = Constant.NB_PAGE;
	Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	ComputerService computerService ;
	
	@RequestMapping(value = "dashboard", method = RequestMethod.POST)
	public String homePage(ModelMap model, @RequestParam(name ="page",required = false) String paramPage, @RequestParam(name = "perpage",required = false) String paramNbPerPage,@RequestParam(name ="search",required = false) String paramSearch)
	{
		

		return home(model, paramPage, paramNbPerPage, paramSearch);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String home(ModelMap model, @RequestParam(name ="page",required = false) String paramPage, @RequestParam(name = "perpage",required = false) String paramNbPerPage,@RequestParam(name ="search",required = false) String paramSearch)
	{
		
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

		model.addAttribute( "computers", page.getPage() );
		model.addAttribute( "nbComputers", computers.size() );
		model.addAttribute( "numeropage", page.getNumberOfPage() );
		model.addAttribute( "search", paramSearch );
		return "dashboard";
	}
		
	
	
}