package com.excilys.mlemaile.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.persistence.DaoFactory;

public enum ServiceCompany {
	INSTANCE();
	private static final Logger logger = LoggerFactory.getLogger(ServiceCompany.class);
	public List<Company> listcompanies(int number,long idFirst){
		List<Company> companies = new ArrayList<Company>();
		try{
			companies = DaoFactory.INSTANCE.getCompanyDao().listSomeCompanies(number, idFirst);
		}catch(DaoException e){
			logger.warn("Can't list companies",e);
		}
		return companies;
	}
	
	public Company getCompany(long id){
		Company company = new Company.Builder().build();
		try{
			company = DaoFactory.INSTANCE.getCompanyDao().getCompany(id);
		}catch(DaoException e){
			logger.warn("Can't find company",e);
		}
		return company;
	}
}
