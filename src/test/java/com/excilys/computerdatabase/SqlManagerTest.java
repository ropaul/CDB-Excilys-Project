package com.excilys.computerdatabase;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.SqlManager;

import junit.framework.TestCase;

public class SqlManagerTest extends TestCase {

	SqlManager manager;
	Logger log;

	
	


 

	
	protected void setUp() {
		log = LoggerFactory.getLogger(this.getClass());
		try {
			manager = SqlManager.getInstance();
		} catch (SQLException e) {
			log.error("Error occrured:{}",e );
		}
//		MockitoAnnotations.initMocks(this);
	}

	protected void tearDown() {
		manager = null;
		log = null;
	}

	public void testGetComputerTrue() {
		Computer computer = null;
		try {
			computer = manager.getComputer(1);
		} catch (SQLException e) {
			log.error("Error occrured:{}",e );
		}
		assertTrue(computer != null);
	}
	
	public void testGetCompanyTrue() {
		Company company = null;
		try {
			company = manager.getCompany(1);
		} catch (SQLException e) {
			log.error("Error occrured:{}",e );
		}
		assertTrue(company != null);
	}
	
	
	public void testGetCompanyFalse() {
		Company company = null;
		int id = 10000;
		try {
			company = manager.getCompany(id);
		} catch (SQLException e) {
			log.error("Error occrured:{}",e );
		}
		assertTrue(company == null);
	}


	@Test(expected = SQLException.class, timeout = 1000)
	public void testGetComputerWithException() {
		Computer computer = null;

		try {
			computer = manager.getComputer(-1);
		} catch (SQLException e) {
			log.error("Error occrured:{}",e );

		}
		assertTrue(computer == null);


	}


	public void testGetComputersTrue() {
		ArrayList<Computer> computers = null;
		try {
			computers = manager.getComputers();
		} catch (SQLException e) {
			log.error("Error occrured:{}",e );
		}
		assertTrue(computers != null);
	}
	
	public void testGetComputerFalse() {
		int id = 10000;
		
		try {
			Computer computer = manager.getComputer(id);
			assertTrue(computer == null);
		} catch (SQLException e) {
			log.error("Error occrured:{}",e );
		}
		
	}
	
	
	


	public void testGetCompaniesTrue() {
		ArrayList<Company> companies = null;
		try {
			companies = manager.getCompanies();
		} catch (SQLException e) {
			log.error("Error occrured:{}",e );
		}
		assertTrue(companies != null);
	}

	public void testCreateThenDeleteComputer() {
		int id = 10001;
		Computer computer = new Computer(id, "le test",Date.valueOf("1990-01-01"), Date.valueOf("1995-12-31"));
		try {
			manager.createComputer(computer);
			Computer testComputer = manager.getComputer(id);
			assertTrue(testComputer != null);
			assertEquals(computer,testComputer);
			manager.deleteComputer(testComputer);

		}
		catch (SQLException e) {
			log.error("Error occrured:" +e );
		}

	}
	
	public void testUpdateComputer() {
		int id = 10002;
		
		Computer computer = new Computer(id, "le test",Date.valueOf("1990-01-01"), Date.valueOf("1995-12-31"));
		try {
			manager.deleteComputer(id);
			manager.createComputer(computer);
			String name = "update du test";
			computer.setName(name);
			assertEquals(name, computer.getName());
			manager.updateComputer(computer);
			Computer testComputer = manager.getComputer(computer.getId()); 
			assertEquals(name, testComputer.getName());
			manager.deleteComputer(testComputer);

		}
		catch (SQLException e) {
			log.error("Error occrured:" +e );
		}

	}
	
	
	public void testUpdateComputerWhichDoesntExist() {
		int id = 10003;
		
		Computer computer = new Computer(id, "le test",Date.valueOf("1990-01-01"), Date.valueOf("1995-12-31"));
		try {
			String name = "update du test";
			computer.setName(name);
			assertEquals(name, computer.getName());
			manager.updateComputer(computer);
			Computer testComputer = manager.getComputer(computer.getId()); 
			assertTrue( testComputer == null);
		}
		catch (SQLException e) {
			log.error("Error occrured:" +e );
		}

	}
	
}
