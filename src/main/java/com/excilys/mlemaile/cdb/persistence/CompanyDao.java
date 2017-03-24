package com.excilys.mlemaile.cdb.persistence;

import java.util.List;
import java.util.Optional;

import com.excilys.mlemaile.cdb.service.model.Company;

public interface CompanyDao {

    /**
     * This method return one company, finding it by the id.
     * @param id the id of the company to retrieve
     * @return the company identified by the id
     */
    Optional<Company> getCompany(long id);

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

    /**
     * This method delete a company.
     * @param company the company to delete
     */
    void deleteCompany(Company company);
}