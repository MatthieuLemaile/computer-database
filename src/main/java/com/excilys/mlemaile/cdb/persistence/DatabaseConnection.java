package com.excilys.mlemaile.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * This class create the connection to the database
 * @author Matthieu Lemaile
 *
 */
public class DatabaseConnection {
	private static Connection dbConnection;
	
	/**
	 * This method return the connection to the database
	 * @return
	 */
	public static Connection getConnection(){
		if(dbConnection==null){
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
					
					dbConnection = DriverManager
					        .getConnection(startUrl+database+"?user="+user
					                        + "&password="+password
					                        + "&zeroDateTimeBehavior="+zeroDataTimeBehavior);
				} catch (SQLException | ClassNotFoundException | ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		return dbConnection;
	}
	
	/**
	 * This method close the connection to the database
	 */
	public static void closeConnection(){
		try {
			if(dbConnection!=null && !dbConnection.isClosed()){
				dbConnection.close();
				dbConnection = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
