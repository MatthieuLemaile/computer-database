package com.excilys.mlemaile.cdb.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.persistence.DaoFactory;

public enum ServiceComputer{
	INSTANCE();
	public static final Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
	
	public boolean createComputer(String name,LocalDate introduced,LocalDate discontinued,int company_id){
		boolean computerCreated = false;
		try{
			Company company = DaoFactory.INSTANCE.getCompanyDao().getCompany(company_id);
			Computer c = new Computer.Builder(name).introduced(introduced).discontinued(discontinued).company(company).build();
			if(DaoFactory.INSTANCE.getComputerDao().createComputer(c)){
				computerCreated = true;
				logger.info("Computer created : " + c.toString());
			}
		}catch(IllegalArgumentException | DaoException e){
			logger.warn("Can't create the computer :",e);
		}return computerCreated;
	}
	
	public boolean updatecomputer(Computer c){
		boolean execution = false;
		try{
			execution =  DaoFactory.INSTANCE.getComputerDao().updateComputer(c);
			logger.info("updated computer to : " + c.toString());
		}catch(DaoException e){
			logger.warn("cant' update the computer",e);
		}
		return execution;
	}
	
	public List<Computer> listComputer(int number,long idFirst){
		List<Computer> computers = new ArrayList<>();
		try{
			computers =  DaoFactory.INSTANCE.getComputerDao().listSomecomputer(number, idFirst);
		}catch(DaoException e){
			logger.warn("cant' list computers",e);
		}
		return computers;
	}
	
	public Computer getComputer(long id){
		Computer computer = new Computer.Builder("").build();
		try{
			computer =  DaoFactory.INSTANCE.getComputerDao().getComputer(id);
		}catch(DaoException e){
			logger.warn("cant' find the computer",e);
		}
		return computer;
	}
	
	public boolean deleteComputer(Computer c){
		boolean execution = false;
		try{
			execution =  DaoFactory.INSTANCE.getComputerDao().deleteComputer(c);
			logger.info("computer deleted : " + c.toString());
		}catch(DaoException e){
			logger.warn("cant' update the computer",e);
		}
		return execution;
	}
}
