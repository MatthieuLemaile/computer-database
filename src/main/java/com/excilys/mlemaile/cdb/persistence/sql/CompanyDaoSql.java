package com.excilys.mlemaile.cdb.persistence.sql;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.service.model.Company;

/**
 * This enum communicate with the database to store, update, and read companies. in the database
 * @author Matthieu Lemaile
 *
 */
@Repository("companyDao")
public class CompanyDaoSql implements CompanyDao {
    private static final String ID               = "id";
    private static final String NAME             = "name";
    private static final String SQL_SELECT_BY_ID = "select " + ID + "," + NAME
            + " from company where id=?";
    private static final String SQL_SELECT       = "SELECT " + ID + "," + NAME
            + " FROM company ORDER BY id ASC LIMIT ?,?";
    private static final String SQL_SELECT_ALL   = "SELECT " + ID + "," + NAME
            + " FROM company ORDER BY id ASC";
    private static final String SQL_DELETE       = "DELETE FROM company WHERE id=?";
    @Autowired
    private JdbcTemplate        jdbcTemplate;

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#getCompanyById(long)
     */
    @Override
    public Optional<Company> getCompanyById(long id) {
        Company company = null;
        try {
            company = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[] {id },
                    new BeanPropertyRowMapper<>(Company.class));
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            company = null;
        }
        return Optional.ofNullable(company);
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#listNumberCompaniesStartingAt(int, long)
     */
    @Override
    public List<Company> listNumberCompaniesStartingAt(int number, long idFirst) {
        List<Company> companies = jdbcTemplate.query(SQL_SELECT, new Object[] {idFirst, number },
                new BeanPropertyRowMapper<>(Company.class));
        return companies;
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#listCompanies()
     */
    @Override
    public List<Company> listCompanies() {
        List<Company> companies = jdbcTemplate.query(SQL_SELECT_ALL,
                new BeanPropertyRowMapper<>(Company.class));
        return companies;
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#deleteCompany()
     */
    @Override
    public void deleteCompanyById(long companyId) {
        jdbcTemplate.update(SQL_DELETE, companyId);
    }
}
