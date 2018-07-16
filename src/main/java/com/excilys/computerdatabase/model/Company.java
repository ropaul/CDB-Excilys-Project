package com.excilys.computerdatabase.model;



import org.springframework.stereotype.Repository;


public class Company {
	
	private String name;
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
