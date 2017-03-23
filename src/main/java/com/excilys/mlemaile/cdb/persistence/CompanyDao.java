package com.excilys.mlemaile.cdb.persistence;

import java.util.List;

import com.excilys.mlemaile.cdb.service.model.Company;

public interface CompanyDao {

    /**
     * This method return one company, finding it by the id.
     * @param id the id of the company to retrieve
     * @return the company identified by the id
     */
    Company getCompany(long id);

    /**
     * This method return number companies, ordered by id, in ascendent order.
     * @param number The number of companies to return
     * @param idFirst The id of the first company to return
     * @return a List of Company
     */
    List<Company> listSomeCompanies(int number, long idFirst);

    /**
     * List all companies in the database.
     * @return a List of Company
     */
    List<Company> listCompanies();

}