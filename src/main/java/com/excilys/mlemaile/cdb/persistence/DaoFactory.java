package com.excilys.mlemaile.cdb.persistence;

public enum DaoFactory {
	INSTANCE();
	public CompanyDao getCompanyDao(){
		return CompanyDaoSql.INSTANCE;
	}
	
	public ComputerDao getComputerDao(){
		return ComputerDaoSql.INSTANCE;
	}
}
