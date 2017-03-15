package com.excilys.mlemaile.cdb.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;

public class ServiceComputer {
	public static final Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
	
	public static boolean createComputer(String name,LocalDate introduced,LocalDate discontinued,int company_id){
		boolean computerCreated = false;
		try{
			Computer c = new Computer.Builder(name).introduced(introduced).discontinued(discontinued).companyId(company_id).build();
			if(ComputerDao.INSTANCE.createComputer(c)){
				computerCreated = true;
			}
		}catch(IllegalArgumentException e){
			logger.warn("can't create the computer :",e);
		}return computerCreated;
	}
	
	public static boolean updatecomputer(Computer c){
		return ComputerDao.INSTANCE.updateComputer(c);
	}
}
