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
    public static final Logger LOGGER = LoggerFactory.getLogger(ServiceCompany.class);

    /**
     * restourne une liste de number companies, commençant par la numéro idFirst, s'il y en a
     * suffisamment après idFirst.
     * @param number le nombre de company à retourner
     * @param idFirst le numéro de la première à retourner
     * @return une List de company
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
     * retourne la company d'id id.
     * @param id le numéro de la company à retourner
     * @return une Company
     */
    public Company getCompany(long id) {
        Company company = new Company.Builder().build();
        try {
            company = DaoFactory.INSTANCE.getCompanyDao().getCompany(id);
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
}
