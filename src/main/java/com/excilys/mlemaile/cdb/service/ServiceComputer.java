package com.excilys.mlemaile.cdb.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;

public enum ServiceComputer{
	INSTANCE();
	public static final Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
	
	public boolean createComputer(String name,LocalDate introduced,LocalDate discontinued,int company_id){
		boolean computerCreated = false;
		try{
			Company company = CompanyDao.INSTANCE.getCompany(company_id);
			Computer c = new Computer.Builder(name).introduced(introduced).discontinued(discontinued).company(company).build();
			if(ComputerDao.INSTANCE.createComputer(c)){
				computerCreated = true;
			}
		}catch(IllegalArgumentException e){
			logger.warn("can't create the computer :",e);
		}return computerCreated;
	}
	
	public boolean updatecomputer(Computer c){
		return ComputerDao.INSTANCE.updateComputer(c);
	}
	
	public List<Computer> listComputer(int number,long idFirst){
		return ComputerDao.INSTANCE.listSomecomputer(number, idFirst);
	}
	
	public Computer getComputer(long id){
		return ComputerDao.INSTANCE.getComputer(id);
	}
	
	public boolean deleteComputer(Computer c){
		return ComputerDao.INSTANCE.deleteComputer(c);
	}
}
