package com.excilys.mlemaile.cdb.model;

/**
 * This class represent a company as in database
 * @author Matthieu Lemaile
 *
 */
public class Company {
	private int id;
	private String name;
	
	private Company(){}
	
	private Company(int id,String name){
		setId(id);
		setName(name);
	}
	
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
	
	public static class Builder{
		private int id;
		private String name;
		
		public Builder id(int id){
			this.id = id;
			return this;
		}
		
		public Builder name(String name){
			this.name = name;
			return this;
		}
		
		public Company build(){
			return new Company(id,name);
		}
	}
}
