package com.excilys.computerdatabase.model;

import java.util.ArrayList;

import com.excilys.computerdatabase.service.ComputerService;



public class ComputerPage {

	int numberPerPage;
	Long currentId;
	ArrayList<Computer> page;
	ComputerPage nextPage;
	ComputerPage previousPage;
	int numberOfPage;
	String searchComputer;
	String searchCompany;
	
	ComputerService computerService;


	public ComputerPage() {

	}






	public ComputerPage(String searchComputer, String searchCompany, int numberPerPage, Long currentId, ComputerService computerService) {
		this.numberPerPage = numberPerPage;
		this.currentId = currentId;
		this.searchCompany = searchCompany;
		this.searchComputer =  searchComputer;
		previousPage =  null;
		numberOfPage = 0;
		this.computerService = computerService;

		page = nextPage();
		
	}

	public ComputerPage(String searchComputer, String searchCompany, int numberPerPage, Long currentId, ArrayList<Computer> page,int numberOfPage, ComputerService computerService) {
		this.numberPerPage = numberPerPage;
		this.currentId = currentId;
		this.page = page;
		this.numberOfPage =  numberOfPage;
		this.searchCompany = searchCompany;
		this.searchComputer =  searchComputer;

		this.computerService = computerService;

	}


	public ComputerPage getNextPage(){
		if (nextPage == null) {
			ArrayList<Computer> newPage = this.nextPage();
			if (!newPage.isEmpty()) {
				nextPage = new ComputerPage(this.searchComputer, searchCompany, this.numberPerPage,currentId, newPage, numberOfPage+1 ,this.computerService);
				nextPage.setPreviousPage(this);
				return nextPage;
			}
			else {
				return null;
			}
		}
		else {
			return nextPage;
		}
	}


	public int getNumberOfPage() {
		return numberOfPage;
	}



	public ComputerPage getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(ComputerPage previousPage) {
		this.previousPage = previousPage;
	}


	private ArrayList<Computer> nextPage() {
		ArrayList<Computer> resultPage = computerService.search(searchComputer, searchCompany, numberPerPage, currentId);
		for (Computer c : resultPage) {
			if (currentId < c.getId())
				currentId = c.getId();
		}
		return resultPage;
	}


	public boolean hasNext(){
		getNextPage();
		return nextPage != null;
	}

	public ArrayList<Computer> getPage() {
		return page;
	}




}
