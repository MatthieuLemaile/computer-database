package com.excilys.mlemaile.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static Connection dbConnection;
	
	public static Connection getConnection(){
		if(dbConnection==null){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				dbConnection = DriverManager
                        .getConnection("jdbc:mysql://localhost/computer-database-db?"
                                        + "user=admincdb&password=TJeWkg5equo1mpOIWVUI");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return dbConnection;
	}
	
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
