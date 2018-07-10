package com.excilys.computerdatabase;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

import junit.framework.TestCase;

public class SqlManagerTest extends TestCase {


	Logger log;
	ComputerService computerService;
	CompanyService companyService;








	protected void setUp() {
		log = LoggerFactory.getLogger(this.getClass());
		computerService = ComputerService.getInstance();
		companyService = CompanyService.getInstance();

		//		MockitoAnnotations.initMocks(this);
	}

	protected void tearDown() {
		companyService = null;
		computerService = null;
		log = null;
	}

	public void testGetComputerTrue() {
		Computer computer = null;
		computer = computerService.get(1);
		assertTrue(computer != null);
	}

	public void testGetCompanyTrue() {
		Company company = null;

		company = companyService.get(1);

		assertTrue(company != null);
	}


	public void testGetCompanyFalse() {
		Company company = null;
		int id = 10000;

		company = companyService.get(id);

		assertTrue(company == null);
	}


	@Test(expected = SQLException.class, timeout = 1000)
	public void testGetComputerWithException() {
		Computer computer = null;

		computer = computerService.get(-1);
		assertTrue(computer == null);


	}


	public void testGetComputersTrue() {
		ArrayList<Computer> computers = null;
		computers = computerService.getAll();
		assertTrue(computers != null);
	}

	public void testGetComputerFalse() {
		int id = 10000;

		Computer computer = computerService.get(id);
		assertTrue(computer == null);


	}





	public void testGetCompaniesTrue() {
		ArrayList<Company> companies = null;
		companies = companyService.getAll();

		assertTrue(companies != null);
	}

	public void testCreateThenDeleteComputer() {
		int id = 10001;
		Computer computer = new Computer(id, "le test",null,Date.valueOf("1990-01-01"), Date.valueOf("1995-12-31"));

		computerService.add(computer);
		Computer testComputer = computerService.get(id);
		assertTrue(testComputer != null);
		assertEquals(computer,testComputer);
		computerService.delete(testComputer);



	}

	public void testUpdateComputer() {
		int id = 10002;

		Computer computer = new Computer(id, "le test",null,Date.valueOf("1990-01-01"), Date.valueOf("1995-12-31"));
		computerService.delete(id);
		computerService.add(computer);
		String name = "update du test";
		computer.setName(name);
		assertEquals(name, computer.getName());
		computerService.update(computer);
		Computer testComputer = computerService.get(computer.getId()); 
		assertEquals(name, testComputer.getName());
		computerService.delete(testComputer);


	}


	public void testUpdateComputerWhichDoesntExist() {
		int id = 10003;

		Computer computer = new Computer(id, "le test",null,Date.valueOf("1990-01-01"), Date.valueOf("1995-12-31"));
		String name = "update du test";
		computer.setName(name);
		assertEquals(name, computer.getName());
		computerService.update(computer);
		Computer testComputer = computerService.get(computer.getId()); 
		assertTrue( testComputer == null);


	}

}
