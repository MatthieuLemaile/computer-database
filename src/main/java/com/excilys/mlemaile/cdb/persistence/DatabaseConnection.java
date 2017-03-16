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
	INSTANCE();
	/**
	 * Créé et retourne une connecion à la base de donnée
	 * @return une Connection à la base de donnée
	 */
	public Connection getConnection(){
		Connection connection = null;
		try {
			Parameters params = new Parameters();
			FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
					new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
					.configure(params.properties().setFileName("database.properties"));
			Configuration config = builder.getConfiguration();
			String startUrl =config.getString("start-url");
			String host = config.getString("host");
			String database =config.getString("database");
			String user = config.getString("user");
			String password = config.getString("password");
			String zeroDataTimeBehavior = config.getString("zeroDateTimeBehavior");
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager
			        .getConnection(startUrl+host+"/"+database+"?user="+user
			                        + "&password="+password
			                        + "&zeroDateTimeBehavior="+zeroDataTimeBehavior);
		} catch (SQLException | ClassNotFoundException | ConfigurationException e) {
			throw new DaoException("Exception while connecting to the database",e);
		}
		return connection;
	}
	
	/**
	 * Cette méthode ferme la Connection à la base de données
	 * @param connection la connection à fermer
	 */
	public void closeConnection(Connection connection){
		try {
			if(connection!=null && !connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e) {
			throw new DaoException("Exception while closing the connection",e);
		}
	}
	
	/**
	 * Cette méthode ferme le Statement
	 * @param st le Statement à fermer
	 */
	public void closeStatement(Statement st){
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				throw new DaoException("Exception while closing a statement",e);
			}
		}
	}
	
	/**
	 * Cette méthode ferme le ResultSet
	 * @param rs le ResultSet à fermer
	 */
	public void closeResulSet(ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DaoException("Exception while closing a result set",e);
			}
		}
	}
}