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

public class ConsoleUserInterface {
	public static boolean menu(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String entry;
		int optionNumber=0;
		boolean continuProgramm = true;
		do{
			System.out.println("1 : List computers");
			System.out.println("2 : List companies");
			System.out.println("3 : show computer details");
			System.out.println("4 : create a computer");
			System.out.println("5 : update a computer");
			System.out.println("6 : delete a computer");
			System.out.println("7 : Exit");
			try {
				entry = br.readLine();
				optionNumber = Integer.parseInt(entry);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e){
				System.out.println("You must enter an integer");
			}
		}while(optionNumber<1 || optionNumber>7);
		
		switch(optionNumber){
			case 1:
				listComputers();
				break;
			case 2:
				listCompanies();
				break;
			case 3:
				showComputerDetails(br);
				break;
			case 4:
				createComputer(br);
				break;
			case 5:
				updateComputer(br);
				break;
			case 6:
				deleteComputer(br);
				break;
			case 7:
				continuProgramm = false;
				
		}
		return continuProgramm;
		
	}
	
	private static void listComputers(){
		ArrayList<Computer> computers = (ArrayList<Computer>) ComputerDao.listComputers();
		for(Computer computer : computers){
			System.out.println("\tid : "+computer.getId()+" name : "+computer.getName());
		}
	}
	
	private static void listCompanies(){
		ArrayList<Company> companies = (ArrayList<Company>) CompanyDao.listCompanies();
		for(Company company : companies){
			System.out.println("\tid : "+company.getId()+" name : "+company.getName());
		}
	}
	
	private static void showComputerDetails(BufferedReader br){
		System.out.println("enter the id of the computer of which you want to see the details");
		String entry;
		int id = 0;
		do{
			System.out.println("Enter a number greater or equal to 1");
			try {
				entry = br.readLine();
				id = Integer.parseInt(entry);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e){
				System.out.println("You must enter an integer");
			}
		}while(id<1);
		Computer c = ComputerDao.getComputer(id);
		Company company = CompanyDao.getCompany(c.getCompany_id());
		System.out.println("\tid : "+c.getId()+" name : "+c.getName());
		System.out.println("\tintroduced : "+c.getIntroduced()+" discontinued : "+c.getDiscontinued());
		System.out.println("\tmanufacturer : "+company.getName());
	}
	
	private static void createComputer(BufferedReader br){
		System.out.println("Enter the following details. You can just type enter to let a field blank, except for the name.");
		try {
			LocalDate defaultDate = LocalDate.now();
			System.out.print("please enter the name of the computer :");
			String name = br.readLine();
			System.out.print("date of introduction (yyyy-mm-dd)?");
			String dateIntroduced = br.readLine();
			LocalDate ldIntro = defaultDate;
			if(dateIntroduced!=null && !dateIntroduced.trim().isEmpty()){
				ldIntro = LocalDate.parse(dateIntroduced);
			}
			System.out.print("date of discontinuation (yyyy-mm-dd) ?");
			String dateDiscontinued = br.readLine();
			LocalDate ldDisco = defaultDate;
			if(dateDiscontinued!=null && !dateDiscontinued.trim().isEmpty()){
				ldDisco = LocalDate.parse(dateDiscontinued);
			}
			System.out.print("Id of the manufacturer ?");
			String str_company_id = br.readLine();
			int company_id = 0;
			if(str_company_id!=null && !str_company_id.trim().isEmpty()){
				company_id = Integer.parseInt(str_company_id);
				if(company_id<1){
					System.out.println("The id of the company must be greater or equals to 1.");
					return;
				}
			}
			Computer c = new Computer(name);
			if(!ldIntro.isEqual(defaultDate)){
				c.setIntroduced(ldIntro);
			}
			if(!ldDisco.isEqual(defaultDate)){
				c.setDiscontinued(ldDisco);
			}
			if(company_id!=0){
				c.setCompany_id(company_id);
			}
			if(ComputerDao.createComputer(c)){
				System.out.println("Computer successfully created. ID :"+c.getId());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DateTimeParseException e){
			System.out.println("please try again formatting your date the way indicated");
		} catch (NumberFormatException e){
			System.out.println("please try again entering a good company id");
		}
	}
	
	private static void updateComputer(BufferedReader br){
		System.out.println("Enter the id of the computer you want to update.");
		String entry;
		int id = 0;
		do{
			System.out.println("Enter a number greater or equal to 1");
			try {
				entry = br.readLine();
				id = Integer.parseInt(entry);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e){
				System.out.println("You must enter an integer");
			}
		}while(id<1);
		Computer c = ComputerDao.getComputer(id); 
		System.out.println("Do you want to change the name (y/n) ?");
		try {
			if("y".equals(br.readLine())){
				System.out.println("enter the new name");
				String name = br.readLine();
				if(name!=null && !name.trim().isEmpty()){
					c.setName(name);
				}
			}
			System.out.println("Do you want to change the introduced date (y/n) ?");
			if("y".equals(br.readLine())){
				System.out.println("enter the new date (yyyy-mm-dd).");
				String date = br.readLine();
				if(date!=null && !date.trim().isEmpty()){
					c.setIntroduced(LocalDate.parse(date));
				}
			}
			System.out.println("Do you want to change the discontinued date (y/n) ?");
			if("y".equals(br.readLine())){
				System.out.println("enter the new date (yyyy-mm-dd).");
				String date = br.readLine();
				if(date!=null && !date.trim().isEmpty()){
					c.setDiscontinued(LocalDate.parse(date));
				}
			}
			System.out.println("Do you want to change the company id (y/n) ?");
			if("y".equals(br.readLine())){
				System.out.println("enter the new company id.");
				String strId = br.readLine();
				if(strId!=null && !strId.trim().isEmpty()){
					int companyId = Integer.parseInt(strId);
					if(companyId>1){
						c.setCompany_id(companyId);
					}
				}
			}
			ComputerDao.updateComputer(c);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DateTimeParseException e){
			System.out.println("please try again formatting your date the way indicated");
		}
	}
	
	private static void deleteComputer(BufferedReader br){
		System.out.println("Enter the id of the computer you want to delete.");
		String entry;
		int id = 0;
		do{
			System.out.println("Enter a number greater or equal to 1");
			try {
				entry = br.readLine();
				id = Integer.parseInt(entry);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e){
				System.out.println("You must enter an integer");
			}
		}while(id<1);
		Computer c = ComputerDao.getComputer(id); 
		if(ComputerDao.deleteComputer(c)){
			System.out.println("Computer successfully deleted !");
		}
	}
}
