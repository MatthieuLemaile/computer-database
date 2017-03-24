package com.excilys.mlemaile.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * This enum manage connections to the database.
 * @author Matthieu Lemaile
 *
 */
public enum DatabaseConnection {
    INSTANCE();
    private static HikariDataSource ds = null;

    /**
     * Create and return a database connection.
     * @return a Connection to the database
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            if (ds == null || ds.isClosed()) {
                Parameters params = new Parameters();
                FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                        PropertiesConfiguration.class)
                                .configure(params.properties().setFileName("database.properties"));
                Configuration config = builder.getConfiguration();
                String startUrl = config.getString("start-url");
                String host = config.getString("host");
                String database = config.getString("database");
                String user = config.getString("user");
                String password = config.getString("password");
                String zeroDataTimeBehavior = config.getString("zeroDateTimeBehavior");
                Class.forName("com.mysql.jdbc.Driver");
                HikariConfig configHikari = new HikariConfig();
                // +"?zeroDateTimeBehavior"+zeroDataTimeBehavior
                configHikari.setJdbcUrl(startUrl + host + "/" + database + "?zeroDateTimeBehavior"
                        + zeroDataTimeBehavior);
                configHikari.setUsername(user);
                configHikari.setPassword(password);
                configHikari.addDataSourceProperty("cachePrepStmts", "true");
                configHikari.addDataSourceProperty("prepStmtCacheSize", "250");
                configHikari.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                configHikari.setMaximumPoolSize(2);
                configHikari.setLeakDetectionThreshold(300);
                ds = new HikariDataSource(configHikari);
            }
            connection = ds.getConnection();
            /*
             * connection = DriverManager.getConnection(startUrl + host + "/" + database + "?user="
             * + user + "&password=" + password + "&zeroDateTimeBehavior=" + zeroDataTimeBehavior);
             */
        } catch (SQLException | ClassNotFoundException | ConfigurationException e) {
            throw new DaoException("Exception while connecting to the database", e);
        }
        return connection;
    }
}