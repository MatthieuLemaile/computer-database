package com.excilys.mlemaile.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.mlemaile.cdb.persistence.CompanyComputerDao;
import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.service.model.Company;

@Service("serviceCompany")
public class ServiceCompany {
    public static final Logger LOGGER = LoggerFactory.getLogger(ServiceCompany.class);
    @Autowired
    private CompanyDao         companyDao;
    @Autowired
    private CompanyComputerDao companyComputerDao;

    public CompanyDao getCompanyDao() {
        return companyDao;
    }

    public void setCompanyDao(CompanyDao companyDao) {
        System.out.println("set company dao : " + companyDao.toString());
        this.companyDao = companyDao;
    }

    public CompanyComputerDao getCompanyComputerDao() {
        return companyComputerDao;
    }

    public void setCompanyComputerDao(CompanyComputerDao companyComputerDao) {
        this.companyComputerDao = companyComputerDao;
    }

    /**
     * return number companie, from idFirst, if there is enough.
     * @param number The number of companies to return.
     * @param idFirst The numero of the first company to return
     * @return a List of Company
     */
    public List<Company> listcompanies(int number, long idFirst) {
        List<Company> companies = new ArrayList<Company>();
        try {
            System.out.println(
                    companyDao.toString() + " list companies(" + number + "," + idFirst + ")");
            companies = companyDao.listNumberCompaniesStartingAt(number,
                    idFirst);
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
    public Company getCompanyById(long id) {
        Company company = new Company.Builder().build();
        try {
            Optional<Company> opt = companyDao.getCompanyById(id);
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
            companies = companyDao.listCompanies();
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
        return companyComputerDao.deleteCompany(id);
    }
}
