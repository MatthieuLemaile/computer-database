package com.excilys.mlemaile.cdb.persistence;

public enum DaoFactory {
    INSTANCE();
    /**
     * This method return an instance of CompanyDao.
     * @return an instance of CompanyDao
     */
    public CompanyDao getCompanyDao() {
        return CompanyDaoSql.INSTANCE;
    }

    /**
     * This method return an instance of ComputerDao.
     * @return an instance of ComputerDao
     */
    public ComputerDao getComputerDao() {
        return ComputerDaoSql.INSTANCE;
    }
}
