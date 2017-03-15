package com.excilys.mlemaile.cdb;

import com.excilys.mlemaile.cdb.presentation.ConsoleUserInterface;

public class MainClass {

	private MainClass(){} //we don't want to instantiate this class
	
	public static void main(String [] args){
		
		System.out.println("Hello, what do you want to do ?");
		while(ConsoleUserInterface.menu());
		
		/*
		Company c = CompanyDao.getCompany(1);
		System.out.println(c.getId() + " : "+ c.getName());
		ArrayList<Company> companies = (ArrayList<Company>) CompanyDao.listCompanies();
		for(Company comp : companies){
			System.out.println(comp.getId() + " : "+ comp.getName());
		}
		*/
		/*
		ArrayList<Computer> computers = (ArrayList<Computer>) ComputerDao.listComputers();
		for(Computer computer : computers){
			System.out.println(computer.getId()+" "+computer.getCompany_id()+" "+computer.getName()+" "+computer.getIntroduced()+" "+computer.getDiscontinued());
		}
		*/
		/*Computer c = new Computer("name of my test computer");
		c.setCompany_id(1);
		c.setIntroduced(LocalDate.now().minusYears(2));
		c.setDiscontinued(LocalDate.now().minusYears(1));
		ComputerDao.createComputer(c);
		System.out.println(c.getId());
		*/
	}
	
	
}
