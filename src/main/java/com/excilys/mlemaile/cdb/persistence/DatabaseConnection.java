package com.excilys.mlemaile.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;

/**
 * This enum manage connections to the database.
 * @author Matthieu Lemaile
 *
 */
public enum DatabaseConnection {
    INSTANCE();
    private static final Logger                 LOGGER            = LoggerFactory
            .getLogger(DatabaseConnection.class);
    private static HikariDataSource             ds                = null;
    public static final ThreadLocal<Connection> THREAD_CONNECTION = new ThreadLocal<Connection>();

    /**
     * create the datasource for hikari.
     */
    private void createDatasource() {
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
    }

    /**
     * Create and return a database connection.
     * @return a Connection to the database
     */
    private Connection connect() {
        try {
            if (ds == null || ds.isClosed()) {
                createDatasource();
            }
            return ds.getConnection();
        } catch (SQLException | PoolInitializationException e) {
            LOGGER.error("Failed to connect to the database", e);
            throw new DaoException("Error while connecting to the database", e);
        }
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