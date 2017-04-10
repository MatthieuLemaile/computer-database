package com.excilys.mlemaile.cdb.persistence.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.persistence.DatabaseConnection;
import com.excilys.mlemaile.cdb.service.model.Company;

/**
 * This enum communicate with the database to store, update, and read companies. in the database
 * @author Matthieu Lemaile
 *
 */
public enum CompanyDaoSql implements CompanyDao {
    INSTANCE;
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

    /**
     * This method map the result of a request (in the result set) to a company object.
     * @param resultSet the ResultSet of the request
     * @return A List of companies
     */
    private List<Company> bindingCompany(ResultSet resultSet) {
        ArrayList<Company> companies = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Company company = new Company.Builder().id(resultSet.getLong(ID))
                        .name(resultSet.getString(NAME)).build();
                companies.add(company);
            }
        } catch (SQLException e) {
            LOGGER.warn("Failed to bind company :", e);
            throw new DaoException("Failed to bind company :", e);
        }
        return companies;
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#getCompanyById(long)
     */
    @Override
    public Optional<Company> getCompanyById(long id) {
        ArrayList<Company> companies = new ArrayList<>(); // initialising
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(SQL_SELECT_BY_ID);) {
            connection.setReadOnly(true);
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {

                companies = (ArrayList<Company>) CompanyDaoSql.INSTANCE.bindingCompany(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to SQL_DELETEfind company :", e);
            throw new DaoException("Failed to find company :", e);
        }

        Company c = null;
        if (companies.size() == 1) {
            c = companies.get(0);
        }
        return Optional.ofNullable(c);
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#listNumberCompaniesStartingAt(int, long)
     */
    @Override
    public List<Company> listNumberCompaniesStartingAt(int number, long idFirst) {
        ArrayList<Company> companies = new ArrayList<>(); // permet d'éviter de retourner null
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT);) {
            connection.setReadOnly(true);
            preparedStatement.setLong(1, idFirst);
            preparedStatement.setInt(2, number);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                companies = (ArrayList<Company>) CompanyDaoSql.INSTANCE.bindingCompany(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't list companies : ", e);
        }
        return companies;
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#listCompanies()
     */
    @Override
    public List<Company> listCompanies() {
        ArrayList<Company> companies = new ArrayList<>(); // permet d'éviter de
        // retourner null

        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);) {
            connection.setReadOnly(true);
            companies = (ArrayList<Company>) CompanyDaoSql.INSTANCE.bindingCompany(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Failed to list companies", e);
            throw new DaoException("Failed to list companies", e);
        }
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
