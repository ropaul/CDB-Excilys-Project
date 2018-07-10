package com.excilys.computerdatabase.model;

import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.computerdatabase.service.ComputerService;



public class ComputerPage {

	int numberPerPage;
	Long currentId;
	ArrayList<Computer> page;
	ComputerPage nextPage;
	ComputerPage previousPage;
	int numberOfPage;


	public ComputerPage() {
		
	}
	





	public ComputerPage(int numberPerPage, Long currentId) {
		this.numberPerPage = numberPerPage;
		this.currentId = currentId;
		page = nextPage();
		previousPage =  null;
		numberOfPage = 0;
	}

	public ComputerPage(int numberPerPage, Long currentId, ArrayList<Computer> page,int numberOfPage) {
		this.numberPerPage = numberPerPage;
		this.currentId = currentId;
		this.page = page;
		this.numberOfPage =  numberOfPage;
	}


	public ComputerPage getNextPage(){
		if (nextPage == null) {
			ArrayList<Computer> newPage = this.nextPage();
			if (!newPage.isEmpty()) {
				nextPage = new ComputerPage(this.numberPerPage,currentId, newPage, numberOfPage+1 );
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
			ComputerService computerService =  ComputerService.getInstance();
			ArrayList<Computer> resultPage = computerService.getAll(numberPerPage, currentId);
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
