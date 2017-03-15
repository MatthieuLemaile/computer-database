package com.excilys.mlemaile.cdb.service;

import java.util.List;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.persistence.CompanyDao;

public enum ServiceCompany {
	INSTANCE();
	
	public List<Company> listcompanies(int number,long idFirst){
		return CompanyDao.INSTANCE.listSomeCompanies(number, idFirst);
	}
	
	public Company getCompany(long id){
		return CompanyDao.INSTANCE.getCompany(id);
	}
}
