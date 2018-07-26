package com.excilys.computerdatabase.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;






@Entity
public class Computer {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private Company company;
	private Date introduced;
	private Date discontinued;

	
	public Computer() {
	}

	public Computer(long id, String name, Company companie, Date introduced, Date discotinued) {
		super();
		this.id = id;
		this.name = name;
		this.company = companie;
		this.introduced = introduced;
		this.discontinued = discotinued;
		
	}

	public Computer( String name, Company companie, Date introduced, Date discotinued) {
		this.id = 0;
		this.name = name;
		this.company = companie;
		this.introduced = introduced;
		this.discontinued = discotinued;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", companie=" + company + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
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
