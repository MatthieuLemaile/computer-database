package com.excilys.mlemaile.cdb.persistence;

public interface CompanyComputerDao {

    /**
     * This method delete a company, identified by the id.
     * @param id The id of the company to delete
     * @return a boolean which is true if the execution went well
     */
    boolean deleteCompany(long id);
}
