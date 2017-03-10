package com.excilys.mlemaile.cdb;

import com.excilys.mlemaile.cdb.dao.CompanyDao;
import com.excilys.mlemaile.cdb.pojo.Company;

public class MainClass {
	public static void main(String [] args){
		Company c = CompanyDao.getCompany(1);
		System.out.println(c.getId() + " : "+ c.getName());
	}
}
