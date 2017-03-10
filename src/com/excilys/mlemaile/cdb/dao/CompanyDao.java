package com.excilys.mlemaile.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.excilys.mlemaile.cdb.pojo.Company;

public class CompanyDao {
	public static Company getCompany(int id){
		Company company = new Company();
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement("select * from company where id=?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			company.setId(resultSet.getInt("id"));
			company.setName(resultSet.getString("name"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}
}
