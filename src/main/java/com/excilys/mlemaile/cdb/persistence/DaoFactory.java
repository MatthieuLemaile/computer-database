package com.excilys.mlemaile.cdb.persistence;

public enum DaoFactory {
	INSTANCE();
	/**
	 * cette méthoderetourne une instance d'un CompanyDao
	 * @return une instance de CompanyDao
	 */
	public CompanyDao getCompanyDao(){
		return CompanyDaoSql.INSTANCE;
	}
	
	/**
	 * Cette méthode retourne une instance de ComputerDao
	 * @return une instance de ComputerDao
	 */
	public ComputerDao getComputerDao(){
		return ComputerDaoSql.INSTANCE;
	}
}
