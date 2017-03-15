package com.excilys.mlemaile.cdb.model;

/**
 * This class represent a company as in database
 * @author Matthieu Lemaile
 *
 */
public class Company {
	private long id;
	private String name;
	
	private Company(){}
	
	private Company(long id,String name){
		setId(id);
		setName(name);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
		private long id;
		private String name;
		
		public Builder id(long id){
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
