package com.excilys.mlemaile.cdb.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;

public class ServiceComputer {
	public static final Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
	
	public static boolean createComputer(String name,LocalDate introduced,LocalDate Discontinued,int company_id){
		boolean computerCreated = false;
		try{
			Computer c = new Computer(name);
			c.setIntroduced(introduced);
			c.setDiscontinued(Discontinued);
			c.setCompany_id(company_id);
			if(ComputerDao.createComputer(c)){
				computerCreated = true;
			}
		}catch(IllegalArgumentException e){
			logger.warn("can't create the computer :",e);
		}return computerCreated;
	}
}
