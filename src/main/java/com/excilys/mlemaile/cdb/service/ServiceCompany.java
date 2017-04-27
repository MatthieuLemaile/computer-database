package com.excilys.mlemaile.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;
import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.service.model.Company;

@Service
public class ServiceCompany {
    public static final Logger LOGGER = LoggerFactory.getLogger(ServiceCompany.class);
    @Autowired
    private CompanyDao         companyDao;
    @Autowired
    private ComputerDao        computerDao;
    
    
    /**
     * return number companie, from idFirst, if there is enough.
     * @param number The number of companies to return.
     * @param idFirst The numero of the first company to return
     * @return a List of Company
     */
    public List<Company> listCompanies(int number, long idFirst) {
        List<Company> companies = new ArrayList<Company>();
        try {
            companies = companyDao.listNumberCompaniesStartingAt(number, idFirst);
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
        Company company = new Company();
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
    @Transactional
    public boolean deleteCompany(long id) {
        Optional<Company> c = companyDao.getCompanyById(id);
        if (c.isPresent()) {
            computerDao.deleteComputerByCompanyId(c.get());
            companyDao.deleteCompanyById(c.get());
        }

        return true;
    }
}
