package com.excilys.mlemaile.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
				Class.forName("com.mysql.jdbc.Driver");
				dbConnection = DriverManager
                        .getConnection("jdbc:mysql://localhost/computer-database-db?"
                                        + "user=admincdb&password=TJeWkg5equo1mpOIWVUI"
                                        + "&zeroDateTimeBehavior=convertToNull");
			} catch (ClassNotFoundException | SQLException e) {
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
