package com.excilys.computerdatabase.model;

import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.computerdatabase.service.SqlManager;


public class ComputerPage {

	int numberPerPage;
	int currentId;
	ArrayList<Computer> page;
	ComputerPage nextPage;
	ComputerPage previousPage;
	int numberOfPage;


	public ComputerPage() {
		
	}
	

	public int getNumberOfPage() {
		return numberOfPage;
	}




	public ComputerPage(int numberPerPage, int currentId) throws SQLException {
		this.numberPerPage = numberPerPage;
		this.currentId = currentId;
		page = nextPage();
		previousPage =  null;
		numberOfPage = 0;
	}

	public ComputerPage(int numberPerPage, int currentId, ArrayList<Computer> page,int numberOfPage) {
		this.numberPerPage = numberPerPage;
		this.currentId = currentId;
		this.page = page;
		this.numberOfPage =  numberOfPage;
	}


	public ComputerPage getNextPage() throws SQLException {
		if (nextPage == null) {
			ArrayList<Computer> newPage = this.nextPage();
			if (nextPage != null) {
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

	public void setNextPage(ComputerPage nextPage) {
		this.nextPage = nextPage;
	}

	public ComputerPage getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(ComputerPage previousPage) {
		this.previousPage = previousPage;
	}

	/**
	 * return 10 computers. If nextPage had been already call, return 10 computer after the previous 10. And so on.
	 * @return 10 computers
	 * @throws SQLException
	 */
	public ArrayList<Computer> nextPage() throws SQLException {
		if (page == null) {
			SqlManager manager = SqlManager.getInstance();
			page = manager.getComputers(numberPerPage, currentId);
		}
		ArrayList<Computer> resultPage =page;
		page = null;
		for (Computer c : resultPage) {
			if (currentId < c.getId())
				currentId = c.getId();
		}
		return resultPage;
	}


	/**
	 * give true is is exist computers which are not been given. If true, stock the 10 first computers in page
	 * @return true if computer exist
	 * @throws SQLException
	 */
	public boolean hasNext() throws SQLException {
		SqlManager manager = SqlManager.getInstance();
		page = manager.getComputers(numberPerPage, currentId);
		return  page != null;
	}

	public ArrayList<Computer> getPage() {
		return page;
	}

	


}
