package com.excilys.mlemaile.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * This enum manage connections to the database.
 * @author Matthieu Lemaile
 *
 */
public enum DatabaseConnection {
    INSTANCE();
    private static HikariDataSource             ds                = null;
    public static final ThreadLocal<Connection> THREAD_CONNECTION = new ThreadLocal<Connection>();

    /**
     * Create and return a database connection.
     * @return a Connection to the database
     */
    public Connection connect() {
        Connection connection = null;
        try {
            if (ds == null || ds.isClosed()) {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                Properties props = new Properties();
                try (InputStream resourceStream = loader.getResourceAsStream("hikari.properties")) {
                  props.load(resourceStream);
                  HikariConfig config = new HikariConfig(props);
                  ds = new HikariDataSource(config);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection = ds.getConnection();
        } catch (SQLException e) {
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