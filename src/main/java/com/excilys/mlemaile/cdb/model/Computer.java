package com.excilys.mlemaile.cdb.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represent a computer as in the database
 * @author Matthieu Lemaile
 * 
 */
public class Computer {
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	private Computer(){};
	
	private Computer(String name,LocalDate introduced, LocalDate discontinued,long id, Company company){
		this(name,introduced,discontinued,company);
		setId(id);
	}
	
	private Computer(String name,LocalDate introduced, LocalDate discontinued, Company company){
		setName(name);
		setIntroduced(introduced);
		setDiscontinued(discontinued);
		setCompany(company);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		if(id>0){
			this.id = id;
		}else{
			throw new IllegalArgumentException("The Id of a computer must be an integer greater than or equal to 1.");
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
	public void setDiscontinued			(LocalDate discontinued) {
		//Pour pouvoir remettre à zéro cette propriété
		if(discontinued==null){
			this.discontinued = discontinued;
		}else if(introduced!=null && (discontinued.isAfter(introduced) || discontinued.isEqual(introduced))){
			this.discontinued = discontinued;
		}else if(introduced==null){
			this.discontinued = discontinued;
		}else{
			throw new IllegalArgumentException("The discontinued date must be after the introduced date.");
		}
	}
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	@Override
	public String toString(){
		if(getCompany()!=null){
			return "ID : "+getId()+" name : "+getName()+" manufacturer ["+getCompany().toString()+"] introduced : "+getIntroduced()+" Discontinued : "+getDiscontinued();
		}else{
			return "ID : "+getId()+" name : "+getName()+" manufacturer [unknow] introduced : "+getIntroduced()+" Discontinued : "+getDiscontinued();
		}
	}
	
	@Override
	public boolean equals(Object computer){
		boolean equal = false;
		if(computer!=null && computer instanceof Computer){
			Computer c = (Computer)computer;
			boolean nameEqual =false;
			boolean introEqual = false;
			boolean discoEqual = false;
			boolean companyEqual = false;
			if((name!=null && name.equals(c.getName())) || name==null && c.getName()==null){
				nameEqual = true;
			}
			if((this.getIntroduced()!=null && this.getIntroduced().equals(c.getIntroduced())) || this.getIntroduced()==null &&  c.getIntroduced()==null){
				introEqual = true;
			}
			if((this.getDiscontinued()!=null && this.getDiscontinued().equals(c.getDiscontinued())) || this.getDiscontinued()==null &&  c.getDiscontinued()==null){
				discoEqual = true;
			}
			if( (this.getCompany()!=null && this.getCompany().equals(c.getCompany()) || this.getCompany()==null && c.getCompany()==null)){
				companyEqual = true;
			}
			if(nameEqual && introEqual && discoEqual && id == c.getId() && companyEqual){
				equal = true;
			}
		}
		return equal;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(id,name,introduced,discontinued,company);
	}

	public static class Builder{
		private long id;
		private LocalDate introduced;
		private LocalDate discontinued;
		private String name;
		private Company company;
		
		public Builder(String name){
			this.name = name;
		}
		
		public Builder id(long id){
			this.id = id;
			return this;
		}
		
		public Builder introduced(LocalDate introduced){
			this.introduced = introduced;
			return this;
		}
		
		public Builder discontinued(LocalDate discontinued){
			this.discontinued = discontinued;
			return this;
		}
		
		public Builder company(Company company){
			this.company = company;
			return this;
		}
		
		public Computer build(){
			if(id!=0){
				return new Computer(name,introduced,discontinued,id,company);
			}else{
				return new Computer(name,introduced,discontinued,company);
			}
		}
	}
}