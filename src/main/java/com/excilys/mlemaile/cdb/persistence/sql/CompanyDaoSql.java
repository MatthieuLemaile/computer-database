package com.excilys.mlemaile.cdb.persistence.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    private static final String ID   = "id";
    private static final String NAME = "name";

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
            throw new DaoException("Can't bind company :", e);
        }
        return companies;
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#getCompany(long)
     */
    @Override
    public Company getCompany(long id) {
        ArrayList<Company> companies = new ArrayList<>(); // initialising
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "select " + ID + "," + NAME + " from company where id=?");) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {

                companies = (ArrayList<Company>) CompanyDaoSql.INSTANCE.bindingCompany(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find company :", e);
        }
        Company c = new Company.Builder().build();
        if (companies.size() == 1) {
            c = companies.get(0);
        }
        return c;
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#listSomeCompanies(int, long)
     */
    @Override
    public List<Company> listSomeCompanies(int number, long idFirst) {
        ArrayList<Company> companies = new ArrayList<>(); // permet d'éviter de
                                                          // retourner null
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT " + ID + "," + NAME + " FROM company ORDER BY id ASC LIMIT ?,?");) {
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
                ResultSet resultSet = statement.executeQuery(
                        "SELECT " + ID + "," + NAME + " FROM company ORDER BY id ASC");) {
            companies = (ArrayList<Company>) CompanyDaoSql.INSTANCE.bindingCompany(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Can't list companies : ", e);
        }
        return companies;
    }
}
