package com.excilys.mlemaile.cdb.model;

/**
 * This class represent a company as in database
 * @author Matthieu Lemaile
 *
 */
public class Company {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "ID : "+getId()+" name : "+getName();
	}
}
