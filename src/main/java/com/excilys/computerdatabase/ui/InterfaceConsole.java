package com.excilys.computerdatabase.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.ComputerPage;
import com.excilys.computerdatabase.service.SqlManager;




/**
 * 
 * @author yann
 *
 */
public class InterfaceConsole {

	private static final int NUMPERPERPAGE = 10;
	private static Scanner inputScanner = new Scanner(System. in );


	/**
	 * Show the list of id and name of all computers. It show theses information 10 to 10
	 * @throws SQLException
	 */
	public static void showListComputer() throws SQLException {

		ComputerPage pageGenerator =  new ComputerPage(NUMPERPERPAGE, 0);

		boolean next =  true;
		String answer= "";
		while(next && pageGenerator.hasNext()) {

			ArrayList<Computer> computers = pageGenerator.nextPage();
			for (Computer computer : computers) {
				System.out.println("id = " + computer.getId() + ", name = " + computer.getName());
			}
			System.out.println("next page ? (yes or no)");

			if(inputScanner.hasNextLine()) {
				answer = inputScanner.nextLine();
			}
			CHOICE: switch (answer) {
			case "yes":
				break CHOICE;
			case "no":
			default:
				System.out.println("(You choose no)");
				next = false;

			}
		}
		//inputScanner.close();
		waitForNext();


	}

	/**
	 * show detailed information about one computer
	 * must ask the user for the id of the computer
	 * @throws SQLException
	 */
	public static void showComputer() throws SQLException {
		SqlManager manager = SqlManager.getInstance();
		System.out.println("Please, enter the ID of the computer:" );

		int id = inputScanner.nextInt();
		Computer computer  = manager .getComputer(id);

		System.out.println(computer);
		//inputScanner.close();
		waitForNext();

	}

	/**
	 * show list of all the existed companies
	 * @throws SQLException
	 */
	public static void showListCompanies() throws SQLException {
		SqlManager manager = SqlManager.getInstance();
		ArrayList<Company> companies  = manager .getCompanies();
		for (Company company : companies) {
			System.out.println("id = " + company.getId() + ", name = " + company.getName());
		}
		waitForNext();
	}



	/**
	 * function to create a computer with information given by the user.
	 * @return computer created by the user
	 * @throws SQLException
	 */
	private static Computer LittleQuest() throws SQLException {

		System.out.println("What is the name of the new computer ?");
		String name =  inputScanner.nextLine();
		SqlManager manager = SqlManager.getInstance();
		Date introduced, discontinued;

		System.out.println("What is the year of introduction ?(if no date, just press 0)");
		int introducedYear =  inputScanner.nextInt();
		if (introducedYear < 1000) {
			System.out.println("This is not a year...");
		}
		System.out.println("What is the month of introduction ?");
		int introducedMonth =  inputScanner.nextInt();
		if (introducedMonth > 12) {
			System.out.println("This is not a month...");	
		}
		System.out.println("What is the day of introduction ?");
		int introducedDay =  inputScanner.nextInt();
		if (introducedDay > 31) {
			System.out.println("This is not a day...");
		}
		if (introducedDay > 31 || introducedMonth > 12 || introducedYear < 1000) {
			introduced = null;
		}
		else {
			introduced = Date.valueOf(introducedYear+"-"+introducedMonth +"-"+introducedDay);
		}

		System.out.println("What is the year of discontinuation? (if no date, just press 0)");
		int discontinuedYear =  inputScanner.nextInt();
		if (discontinuedYear < 1000) {
			System.out.println("This is not a year...");
		}
		System.out.println("What is the month of discontinuation ?");
		int discontinuedMonth =  inputScanner.nextInt();
		if (discontinuedMonth > 12) {
			System.out.println("This is not a month...");	
		}
		System.out.println("What is the day of discontinuation ?");
		int discontinuedDay =  inputScanner.nextInt();
		if (discontinuedDay > 31) {
			System.out.println("This is not a day...");
		}
		if (discontinuedDay > 31 || discontinuedMonth > 12 || discontinuedYear < 1000) {
			discontinued = null;
		}
		else {
			discontinued =  Date.valueOf(discontinuedYear+"-"+discontinuedMonth+"-"+ discontinuedDay);
		}


		System.out.println("What is the id of the company ? If you don't know, press 0");
		int companyID =  Integer.parseInt(inputScanner.next());
		Company company  =  manager.getCompany(companyID);

		Computer newComputer = new Computer(  name, company,  introduced,  discontinued);
		return newComputer;
	}

	/**
	 * put a new computer in the database
	 * @throws SQLException
	 */
	public static void createNewComputer() throws SQLException {

		boolean isItok =  false;
		SqlManager manager = SqlManager.getInstance();
		while (isItok == false) {

			Computer newComputer = LittleQuest();

			System.out.println("Does all this information are ok ?(write yes or no or menu) \n" + newComputer);
			String response =  inputScanner.next();
			switch(response) {
			case "yes":
				isItok = true;
				manager.createComputer(newComputer);
				System.out.println(".\n.\n.\n Done.\n.\n.\n.\n");
				break;
			case "menu":
				isItok = true;

				break;
			case "no" :
			default :
				System.out.println("Please, try again.");
			}
		}
		//inputScanner.close();
		waitForNext();
	}

	/**
	 * delete a computer in the database. A ID must be given
	 * @throws SQLException
	 */
	public static void updateComputer() throws SQLException {
		boolean isItok =  false;
		SqlManager manager = SqlManager.getInstance();
		while (isItok == false) {

			System.out.println("Which is the id of the computer to update ? \n" );
			int computerID =  Integer.parseInt(inputScanner.next());

			Computer oldComputer = manager.getComputer(computerID);

			Computer newComputer = LittleQuest();
			newComputer.setId(oldComputer.getId());
			System.out.println("Does all this information are ok ?(write yes or no or menu) \n" + newComputer);
			String response =  inputScanner.next();
			switch(response) {
			case "yes":
				isItok = true;
				manager.updateComputer( newComputer);
				break;
			case "menu":
				isItok = true;

				break;
			case "no" :
			default :
				System.out.println("Please, try again.");
			}
		}
		//inputScanner.close();
		waitForNext();
	}


	/**
	 * delete a computer in the database. A id Must be given by the user
	 * @throws SQLException
	 */
	public static void deleteComputer() throws SQLException {
		boolean isItok =  false;
		SqlManager manager = SqlManager.getInstance();
		while (isItok == false) {

			System.out.println("Which is the id of the computer to delete ? \n" );
			int computerID =  Integer.parseInt(inputScanner.next());

			Computer oldComputer = manager.getComputer(computerID);

			System.out.println("Are you sure you want to delete this computer ?(write yes or no or menu) \n" + oldComputer);
			String response =  inputScanner.next();
			switch(response) {
			case "yes":
				isItok = true;
				manager.deleteComputer(oldComputer);
				break;
			case "menu":
				isItok = true;
				return;
			case "no" :
			default :
				System.out.println("Please, try again.");
			}
		}
		//inputScanner.close();
		waitForNext();
	}


	/**
	 * function which ask the user to press any touch to acces to the f
	 */
	public static void waitForNext() {
		System.out.println("Please, press any touch then enter to continue");
		if (inputScanner.hasNextLine()) {
			inputScanner.nextLine();
		}
		//inputScanner.close();
	}

	/**
	 * Main function of the CL-UI. Ask the user what it want do and call others functions.
	 * @throws SQLException
	 */
	public static void run() throws SQLException {
		String welcomeText= "Welcome to CDB, the best computers manager software around.\n Please, chose an option:"+
				"\n-1 show list computers"+
				"\n-2 show list companies"+
				"\n-3 create computer"+
				"\n-4 delete a computer"+
				"\n-5 update a computer"+
				"\n-6 show detailed information about a computer"+
				"\n-7 close";

		Scanner inputScanner = new Scanner(System.in );
		while(true) {

			System.out.println(welcomeText);
			int option = 7;
			if (inputScanner.hasNextInt()) {
				option =  inputScanner.nextInt();
			}
			CHOICE: switch(option) {
			case 1:
				try {
					showListComputer();
				}
				catch(Exception e){
					System.err.println(e);
				}
				break CHOICE;
			case 2 :
				showListCompanies();

				break CHOICE;
			case 3 :
				createNewComputer();
				break CHOICE;
			case 4 :
				deleteComputer();
				break CHOICE;
			case 5 :
				updateComputer();
				break CHOICE;
			case 6 :
				showComputer();
				break CHOICE;
			case 7 :
				System.out.println("Thank you for using CDB, the best computers manager software around. \nGood bye. ");
				inputScanner.close();
				return;
			default:
				System.out.println("This is not an option.\n" + welcomeText);
			}
		}

	}




	public static void main(String[] args) {
		try {
			InterfaceConsole.run();

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
		}



	}

}




