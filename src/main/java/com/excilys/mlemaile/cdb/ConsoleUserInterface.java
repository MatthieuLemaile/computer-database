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
	
	/**
	 * Display the menu and call the function to do the choosen action
	 * @return a boolean which is true as long as the user don't choose to leave
	 */
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
				listComputers(br);
				break;
			case 2:
				listCompanies(br);
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
	
	/**
	 * Display the list of computers 
	 * @param br BufferedReader to read the user's entry
	 */
	private static void listComputers(BufferedReader br){
		ArrayList<Computer> computers = new ArrayList<Computer>();
		String entry;
		int pageNumber = 0;
		do{
			System.out.println("Enter a page number, 0 to leave.");
			try {
				entry = br.readLine();
				pageNumber = Integer.parseInt(entry);
				if(pageNumber>0){
					Page<Computer> page = new Page<Computer>(pageNumber);
					int indexMin =(pageNumber-1)*Page.number_per_page;
					computers = (ArrayList<Computer>) ComputerDao.listSomecomputer(Page.number_per_page, indexMin);
					page.setList(computers);
					page.displayPage();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e){
				System.out.println("You must enter an integer");
			}
		}while(pageNumber>0); //Le cas négatif n'est pa géré ici, mais c'est un bug mineur.
	}
	
	/**
	 * Display the list of companies
	 * @param br BufferedReader to read the user's entry
	 */
	private static void listCompanies(BufferedReader br){
		ArrayList<Company> companies = new ArrayList<Company>();
		String entry;
		int pageNumber = 0;
		do{
			System.out.println("Enter a page number, 0 to leave.");
			try {
				entry = br.readLine();
				pageNumber = Integer.parseInt(entry);
				if(pageNumber>0){
					Page<Company> page = new Page<Company>(pageNumber);
					int indexMin =(pageNumber-1)*Page.number_per_page;
					companies = (ArrayList<Company>) CompanyDao.listSomeCompanies(Page.number_per_page, indexMin);
					page.setList(companies);
					page.displayPage();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e){
				System.out.println("You must enter an integer");
			}
		}while(pageNumber>0); //Le cas négatif n'est pa géré ici, mais c'est un bug mineur.
	}
	
	/**
	 * Display details of a computer
	 * @param br BufferedReader to read the user's entry
	 */
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
	
	/**
	 * This function allow the user to create a computer
	 * @param br BufferedReader to read the user's entry
	 */
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
	
	/**
	 * This function allow the user to update a computer
	 * @param br BufferedReader to read the user's entry
	 */
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
	
	/**
	 * This function allow  the user to delete a computer
	 * @param br BufferedReader to read the user's entry
	 */
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
