package com.excilys.computerdatabase.model;

import java.sql.Date;
import java.sql.SQLException;

import com.excilys.computerdatabase.service.SqlManager;



/**
 * @author Yann
 * @
 *
 */
public class Computer {
	private int id;
	private String name;
	private Company company;
	private Date introduced;
	private Date discontinued;
	
	private static Integer biggerID ;
	
	public Computer(int id, String name) {
		this.id = id;
		this.name = name;
		
		if (biggerID == null || biggerID < id ) {
			biggerID = id;
		}
	}
	
	
	public Computer() {
		
		
	}
	
	public Computer(int id, String name, Company companie) {
		super();
		this.id = id;
		this.name = name;
		this.company = companie;
		if (biggerID == null || biggerID < id ) {
			biggerID = id;
		}
	}



	public Computer(int id, String name, Date introduced) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		if (biggerID == null || biggerID < id ) {
			biggerID = id;
		}
	}



	public Computer(int id, String name, Date introduced, Date discotinued) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discotinued;
		if (biggerID == null || biggerID < id ) {
			biggerID = id;
		}
	}



	public Computer(int id, String name, Company companie, Date introduced) {
		super();
		this.id = id;
		this.name = name;
		this.company = companie;
		this.introduced = introduced;
		if (biggerID == null || biggerID < id ) {
			biggerID = id;
		}
	}



	public Computer(int id, String name, Company companie, Date introduced, Date discotinued) {
		super();
		this.id = id;
		this.name = name;
		this.company = companie;
		this.introduced = introduced;
		this.discontinued = discotinued;
		if (biggerID == null || biggerID < id ) {
			biggerID = id;
		}
	}

	public Computer( String name, Company companie, Date introduced, Date discotinued) {
//		super();
//		if (biggerID == null) {
//			SqlManager manager;
//			try {
//				manager = SqlManager.getInstance();
//				manager.getComputers();
//			} catch (SQLException e) {
//				System.err.println(e);
//			}
//			
//		}
//		this.id = biggerID +1;
//		this.id = 0;
		this.name = name;
		this.company = companie;
		this.introduced = introduced;
		this.discontinued = discotinued;
		
		biggerID = id;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company companie) {
		this.company = companie;
	}

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discotinued) {
		this.discontinued = discotinued;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", companie=" + company + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + "]";
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

}
