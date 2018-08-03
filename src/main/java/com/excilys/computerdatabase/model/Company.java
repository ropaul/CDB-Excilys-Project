package com.excilys.computerdatabase.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@Table(name="company")
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	//bi-directional many-to-one association to Computer
	@OneToMany(mappedBy="company")
	private List<Computer> computers;

	public Company() {
	}

	public Company(int id, String name) {
	super();
	this.id =  id;
	this.name = name;
}
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Computer> getComputers() {
		return this.computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	public Computer addComputer(Computer computer) {
		getComputers().add(computer);
		computer.setCompany(this);

		return computer;
	}

	public Computer removeComputer(Computer computer) {
		getComputers().remove(computer);
		computer.setCompany(null);

		return computer;
	}

}