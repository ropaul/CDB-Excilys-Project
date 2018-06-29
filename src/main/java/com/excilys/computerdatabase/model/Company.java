package com.excilys.computerdatabase.model;

public class Company {
	
	private String name;
	private int id;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
