package com.excilys.mlemaile.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.persistence.DaoFactory;
import com.excilys.mlemaile.cdb.service.model.Company;

public enum ServiceCompany {
    INSTANCE();
    public static final Logger LOGGER = LoggerFactory.getLogger(ServiceCompany.class);

    /**
     * return number companie, from idFirst, if there is enough.
     * @param number The number of companies to return.
     * @param idFirst The numero of the first company to return
     * @return a List of Company
     */
    public List<Company> listcompanies(int number, long idFirst) {
        List<Company> companies = new ArrayList<Company>();
        try {
            companies = DaoFactory.INSTANCE.getCompanyDao().listSomeCompanies(number, idFirst);
        } catch (DaoException e) {
            LOGGER.warn("can't list companies", e);
            throw new ServiceException("can't list companies", e);
        }
        return companies;
    }

    /**
     * Return the company whose id is id.
     * @param id The id of the company to return.
     * @return a Company
     */
    public Company getCompany(long id) {
        Company company = new Company.Builder().build();
        try {
            Optional<Company> opt = DaoFactory.INSTANCE.getCompanyDao().getCompany(id);
            if (opt.isPresent()) {
                company = opt.get();
            }
        } catch (DaoException e) {
            LOGGER.warn("can't find companies", e);
            throw new ServiceException("can't find companies", e);
        }
        return company;
    }

    /**
     * List all companies in the database.
     * @return a List of Companies
     */
    public List<Company> listCompanies() {
        List<Company> companies = new ArrayList<Company>();
        try {
            companies = DaoFactory.INSTANCE.getCompanyDao().listCompanies();
        } catch (DaoException e) {
            LOGGER.warn("can't list companies", e);
            throw new ServiceException("can't list companies", e);
        }
        return companies;
    }

    /**
     * This method delete a company, identified by the id.
     * @param id The id of the company to delete
     * @return a boolean which is true if the execution went well
     */
    public boolean deleteCompany(long id) {
        boolean execution = false;
        try {
            DaoFactory.INSTANCE.getCompanyDao().deleteCompany(id);
            execution = true;
            LOGGER.info("deleted company : " + id);
        } catch (DaoException e) {
            LOGGER.warn("can't delete the company", e);
            throw new ServiceException("cant' delete the company", e);
        }
        return execution;
    }
}
