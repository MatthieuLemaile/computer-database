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

//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;

/**
 * This enum manage connections to the database.
 * @author Matthieu Lemaile
 *
 */
public enum DatabaseConnection {
    INSTANCE();
    // private static HikariDataSource ds = null;

    /////////////////////////////////// Hikari CP
    /*
     * Create and return a database connection.
     * @return a Connection to the database
     */
    // public Connection getConnection() {
    // Connection connection = null;
    // try {
    // if (ds == null || ds.isClosed()) {
    // Parameters params = new Parameters();
    // FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new
    // FileBasedConfigurationBuilder<FileBasedConfiguration>(
    // PropertiesConfiguration.class)
    // .configure(params.properties().setFileName("database.properties"));
    // Configuration config = builder.getConfiguration();
    // String startUrl = config.getString("start-url");
    // String host = config.getString("host");
    // String database = config.getString("database");
    // String user = config.getString("user");
    // String password = config.getString("password");
    // String zeroDataTimeBehavior = config.getString("zeroDateTimeBehavior");
    // Class.forName("com.mysql.jdbc.Driver");
    // HikariConfig configHikari = new HikariConfig();
    // // +"?zeroDateTimeBehavior"+zeroDataTimeBehavior
    // configHikari.setJdbcUrl(startUrl + host + "/" + database + "?zeroDateTimeBehavior="
    // + zeroDataTimeBehavior);
    // configHikari.setUsername(user);
    // configHikari.setPassword(password);
    // configHikari.addDataSourceProperty("cachePrepStmts", "true");
    // configHikari.addDataSourceProperty("prepStmtCacheSize", "250");
    // configHikari.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    // configHikari.setMaximumPoolSize(2);
    // configHikari.setLeakDetectionThreshold(300);
    // ds = new HikariDataSource(configHikari);
    // }
    // connection = ds.getConnection();
    // } catch (SQLException | ClassNotFoundException | ConfigurationException e) {
    // throw new DaoException("Exception while connecting to the database", e);
    // }
    // return connection;
    // }

    /////////////////////////// ThreadLocal

    public static final ThreadLocal<Connection> THREAD_CONNECTION = new ThreadLocal<Connection>();

    /**
     * Create and return a database connection.
     * @return a Connection to the database
     */
    public Connection connect() {
        Connection connection = null;
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class)
                        .configure(params.properties().setFileName("database.properties"));
        Configuration config;
        try {
            config = builder.getConfiguration();
            String startUrl = config.getString("start-url");
            String host = config.getString("host");
            String database = config.getString("database");
            String user = config.getString("user");
            String password = config.getString("password");
            String zeroDataTimeBehavior = config.getString("zeroDateTimeBehavior");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager
                    .getConnection(startUrl + host + "/" + database + "?user=" + user + "&password="
                            + password + "&zeroDateTimeBehavior=" + zeroDataTimeBehavior);
        } catch (ConfigurationException | ClassNotFoundException | SQLException e) {
            throw new DaoException("Exception while connecting to the database", e);
        }
        return connection;
    }

    /**
     * Return an opened database connection.
     * @return a Connection
     */
    public Connection getConnection() {
        try {
            if (THREAD_CONNECTION.get() == null || THREAD_CONNECTION.get().isClosed()) {
                Connection connection = connect();
                THREAD_CONNECTION.set(connection);
            }
        } catch (SQLException e) {
            throw new DaoException("Exception while connecting to the database", e);
        }
        return THREAD_CONNECTION.get();
    }

}