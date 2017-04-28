package com.excilys.mlemaile.cdb.persistence.sql;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.persistence.CompanyDao;

/**
 * This enum communicate with the database to store, update, and read companies. in the database
 * @author Matthieu Lemaile
 *
 */
@Repository("companyDao")
public class CompanyDaoSql implements CompanyDao {

    @PersistenceContext
    private EntityManager session;

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#getCompanyById(long)
     */
    @Override
    public Optional<Company> getCompanyById(long id) {
        Company company = (Company) session.find(Company.class, id);
        return Optional.ofNullable(company);
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#listNumberCompaniesStartingAt(int, long)
     */
    @Override
    public List<Company> listNumberCompaniesStartingAt(int number,
    long                                                                           idFirst)
    {
        List<Company> companies = (List<Company>) session
                .createQuery("from Company", Company.class).setMaxResults(number)
                .setFirstResult((int) idFirst).getResultList();
        return companies;
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#listCompanies()
     */
    @Override
    public List<Company> listCompanies() {
        List<Company> companies = (List<Company>) this.session
                .createQuery("from Company", Company.class)
                .getResultList();
        return companies;
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#deleteCompany()
     */
    @Override
    public void deleteCompanyById(Company company) {
        this.session.remove(company);
    }
}
