package com.excilys.mlemaile.cdb.pojo;

import java.time.LocalDate;

public class Computer {
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private int company_id;
	
	private Computer(){}
	
	public Computer(int id){
		this();
		this.setId(id);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		if(id>0){
			this.id = id;
		}else{
			throw new IllegalArgumentException("The Id of a computer must be an interger greater than or equal to 1.");
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		//Pour pouvoir remettre à zéro cette propriété
		if(discontinued==null){
			this.discontinued = discontinued;
		}else if(introduced!=null && discontinued.isAfter(introduced)){
			this.discontinued = discontinued;
		}else if(introduced==null){
			this.discontinued = discontinued;
		}else{
			throw new IllegalArgumentException("The discontinued date must be after the introduced date.");
		}
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	} 
}
