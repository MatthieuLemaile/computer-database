package com.excilys.mlemaile.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.mlemaile.cdb.model.Company;

/**
 * This class communicate with the database to store, update, and read companies in the database
 * @author Matthieu Lemaile
 *
 */
public class CompanyDao {
	
	private CompanyDao(){} //we don't need any constructor
	
	/**
	 * This method map the result of a request (in the result set) to a company object
	 * @param resultSet the ResultSet of the request
	 * @return A List of companies
	 */
	private static List<Company> bindingCompany(ResultSet resultSet){
		ArrayList<Company> companies = new ArrayList<>();
		try{
			while(resultSet.next()){
				Company company = new Company();
				company.setId(resultSet.getInt("id"));
				company.setName(resultSet.getString("name"));
				companies.add(company);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return companies;
	}
	
	/**
	 * This method return one company, finding it by the id
	 * @param id the id of the company to retrieve
	 * @return the company identified by the id
	 */
	public static Company getCompany(int id){
		ArrayList<Company> companies = new ArrayList<>(); //initialising companies
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement("select * from company where id=?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			companies = (ArrayList<Company>) bindingCompany(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Company c = new Company();
		if(companies.size()==1){
			c = companies.get(0);
		}
		return c;
	}
	
	/**
	 * This method list all companies
	 * @return An ArrayList of all companies
	 */
	public static List<Company> listCompanies(){
		ArrayList<Company> companies = new ArrayList<>(); //permet d'éviter de retourner null
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement("select * from company");
			ResultSet resultSet = preparedStatement.executeQuery();
			companies = (ArrayList<Company>) bindingCompany(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companies;
	}
	
	public static List<Company> listSomeCompanies(int number,int idFirst){
		ArrayList<Company> companies = new ArrayList<>(); //permet d'éviter de retourner null
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement("SELECT * FROM company ORDER BY id ASC LIMIT ?,?");
			preparedStatement.setInt(1, idFirst);
			preparedStatement.setInt(2, number);
			ResultSet resultSet = preparedStatement.executeQuery();
			companies = (ArrayList<Company>) bindingCompany(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companies;
	}
	
}