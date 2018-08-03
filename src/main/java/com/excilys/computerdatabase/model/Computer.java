package com.excilys.computerdatabase.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the computer database table.
 *  
 */
@Entity
@Table(name="computer")
@NamedQuery(name="Computer.findAll", query="SELECT c FROM Computer c")
public class Computer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true)
	private long id;

	@Temporal(TemporalType.DATE)
	
	@Column(name = "discontinued")
	private Date discontinued;

	@Temporal(TemporalType.DATE)
	@Column(name = "introduced")
	private Date introduced;

	
	@Column(name = "name")
	private String name;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

	public Computer() {
	}

	public Computer(long id, String name, Company companie, Date introduced, Date discotinued) {
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

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDiscontinued() {
		return this.discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Date getIntroduced() {
		return this.introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}