package com.excilys.mlemaile.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * This enum manage connections to the database
 * @author Matthieu Lemaile
 *
 */
enum DatabaseConnection{
	getManager();
	public Connection getConnection(){
		Connection connection = null;
		try {
			Parameters params = new Parameters();
			FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
					new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
					.configure(params.properties().setFileName("src/main/resources/database.properties"));
			Configuration config = builder.getConfiguration();
			String startUrl =config.getString("start-url");
			String database =config.getString("database");
			String user = config.getString("user");
			String password = config.getString("password");
			String zeroDataTimeBehavior = config.getString("zeroDateTimeBehavior");
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager
			        .getConnection(startUrl+database+"?user="+user
			                        + "&password="+password
			                        + "&zeroDateTimeBehavior="+zeroDataTimeBehavior);
		} catch (SQLException | ClassNotFoundException | ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public void closeConnection(Connection connection){
		try {
			if(connection!=null && !connection.isClosed()){
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeStatement(Statement st){
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeResulSet(ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}