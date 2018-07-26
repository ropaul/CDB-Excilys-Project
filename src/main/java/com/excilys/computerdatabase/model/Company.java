package com.excilys.computerdatabase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Company {
	
	private String name;
	
	@Id
	@GeneratedValue
	private long id;
	
	public Company() {
		
	}

	public Company(int id, String name) {
		super();
		this.id =  id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Companie [name=" + name + ", id=" + id + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

}
