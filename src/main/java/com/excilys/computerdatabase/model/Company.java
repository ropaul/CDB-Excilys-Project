package com.excilys.computerdatabase.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.ui.ModelMap;



@Entity
@Table (name= "company" , schema = "computer-database-db")
public class Company implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1118096811410171303L;

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
