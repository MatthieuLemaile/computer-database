package com.excilys.mlemaile.cdb.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represent a computer as in the database
 * @author Matthieu Lemaile
 * 
 */
public class Computer {
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private int company_id;
	
	private Computer(){};
	
	private Computer(String name,LocalDate introduced, LocalDate discontinued,int id, int companyId){
		setName(name);
		setIntroduced(introduced);
		setDiscontinued(discontinued);
		setId(id);
		setCompany_id(companyId);
	}
	
	private Computer(String name,LocalDate introduced, LocalDate discontinued, int companyId){
		setName(name);
		setIntroduced(introduced);
		setDiscontinued(discontinued);
		setCompany_id(companyId);
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
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	
	@Override
	public String toString(){
		return "ID : "+getId()+" name : "+getName()+" manufacturer : "+getCompany_id()+" introduced : "+getIntroduced()+" Discontinued : "+getDiscontinued();
	}
	
	@Override
	public boolean equals(Object computer){
		boolean equal = false;
		if(computer!=null){
			Computer c = (Computer)computer;
			boolean nameEqual =false;
			boolean introEqual = false;
			boolean discoEqual = false;
			
			if((name!=null && name.equals(c.getName())) || name==null && c.getName()==null){
				nameEqual = true;
			}
			if((this.getIntroduced()!=null && this.getIntroduced().equals(c.getIntroduced())) || this.getIntroduced()==null &&  c.getIntroduced()==null){
				introEqual = true;
			}
			if((this.getDiscontinued()!=null && this.getDiscontinued().equals(c.getDiscontinued())) || this.getDiscontinued()==null &&  c.getDiscontinued()==null){
				discoEqual = true;
			}
			if(nameEqual && introEqual && discoEqual && id == c.getId() && company_id == c.getCompany_id()){
				equal = true;
			}
		}
		return equal;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(id,name,introduced,discontinued,company_id);
	}
	
	public static class Builder{
		private int id;
		private LocalDate introduced;
		private LocalDate discontinued;
		private String name;
		private int companyIdBuilder;
		
		public Builder(String name){
			this.name = name;
		}
		
		public Builder id(int id){
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
		
		public Builder companyId(int companyId){
			this.companyIdBuilder = companyId;
			return this;
		}
		
		public Computer build(){
			if(id!=0){
				return new Computer(name,introduced,discontinued,id,companyIdBuilder  );
			}else{
				return new Computer(name,introduced,discontinued,companyIdBuilder  );
			}
		}
	}
}