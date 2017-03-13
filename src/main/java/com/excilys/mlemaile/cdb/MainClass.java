package com.excilys.mlemaile.cdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;
import com.excilys.mlemaile.cdb.persistence.DatabaseConnection;

public class MainClass {

	private MainClass(){} //we don't want to instantiate this class
	
	public static void main(String [] args){
		
		System.out.println("Hello, what do you want to do ?");
		while(ConsoleUserInterface.menu());
		DatabaseConnection.closeConnection();
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
