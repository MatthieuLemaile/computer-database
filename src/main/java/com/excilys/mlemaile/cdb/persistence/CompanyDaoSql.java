package com.excilys.mlemaile.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.mlemaile.cdb.model.Company;

/**
 * This enum communicate with the database to store, update, and read companies
 * in the database
 * 
 * @author Matthieu Lemaile
 *
 */
enum CompanyDaoSql implements CompanyDao{
	INSTANCE;
	/**
	 * This method map the result of a request (in the result set) to a company
	 * object
	 * 
	 * @param resultSet
	 *            the ResultSet of the request
	 * @return A List of companies
	 */
	private List<Company> bindingCompany(ResultSet resultSet) {
		ArrayList<Company> companies = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Company company = new Company.Builder().id(resultSet.getLong("id")).name(resultSet.getString("name"))
						.build();
				companies.add(company);
			}
		} catch (SQLException e) {
			throw new DaoException("Can't bind company :", e);
		}
		return companies;
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#getCompany(long)
	 */
	@Override
	public Company getCompany(long id) {
		ArrayList<Company> companies = new ArrayList<>(); // initialising
		Connection connection = DatabaseConnection.INSTANCE.getConnection();													// companies
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {preparedStatement = connection.prepareStatement("select * from company where id=?");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			companies = (ArrayList<Company>) CompanyDaoSql.INSTANCE.bindingCompany(resultSet);
		} catch (SQLException e) {
			throw new DaoException("Can't find company :", e);
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(connection);
			DatabaseConnection.INSTANCE.closeStatement(preparedStatement);
			DatabaseConnection.INSTANCE.closeResulSet(resultSet);
		}
		Company c = new Company.Builder().build();
		if (companies.size() == 1) {
			c = companies.get(0);
		}
		return c;
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.mlemaile.cdb.persistence.CompanyDao#listSomeCompanies(int, long)
	 */
	@Override
	public List<Company> listSomeCompanies(int number, long idFirst) {
		ArrayList<Company> companies = new ArrayList<>(); // permet d'éviter de
															// retourner null
		Connection connection = DatabaseConnection.INSTANCE.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {preparedStatement = connection.prepareStatement("SELECT * FROM company ORDER BY id ASC LIMIT ?,?");
			preparedStatement.setLong(1, idFirst);
			preparedStatement.setInt(2, number);
			resultSet = preparedStatement.executeQuery();
			companies = (ArrayList<Company>) CompanyDaoSql.INSTANCE.bindingCompany(resultSet);
		} catch (SQLException e) {
			throw new DaoException("Can't list companies : ", e);
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(connection);
			DatabaseConnection.INSTANCE.closeStatement(preparedStatement);
			DatabaseConnection.INSTANCE.closeResulSet(resultSet);
		}
		return companies;
	}
}
