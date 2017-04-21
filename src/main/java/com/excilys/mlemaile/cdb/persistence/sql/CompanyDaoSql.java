package com.excilys.mlemaile.cdb.persistence.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.service.model.Company;

/**
 * This enum communicate with the database to store, update, and read companies. in the database
 * @author Matthieu Lemaile
 *
 */
@Repository("companyDao")
public class CompanyDaoSql implements CompanyDao {
    private static final Logger LOGGER           = LoggerFactory.getLogger(CompanyDaoSql.class);
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
    public void deleteCompanyById(long companyId, Connection connection) {
        try (PreparedStatement deleteCompanyStatement = connection.prepareStatement(SQL_DELETE)) {
            connection.setReadOnly(false);
            deleteCompanyStatement.setLong(1, companyId);
            deleteCompanyStatement.executeUpdate();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("error manipulating the connection from:" + e.getMessage(), e1);
                throw new DaoException("error manipulating the connection from:" + e.getMessage(),
                        e1);
            }
            LOGGER.error("Failed to delete a company", e);
            throw new DaoException("Failed to delete a company", e);
        }
    }
}
