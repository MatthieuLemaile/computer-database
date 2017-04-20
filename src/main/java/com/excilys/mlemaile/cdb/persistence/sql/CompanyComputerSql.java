package com.excilys.mlemaile.cdb.persistence.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.mlemaile.cdb.persistence.CompanyComputerDao;
import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;
import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.service.ServiceException;

@Repository("companyComputerDao")
public class CompanyComputerSql implements CompanyComputerDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(CompanyComputerSql.class);

    private DataSource         dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    private CompanyDao  companyDao;

    @Autowired
    private ComputerDao computerDao;

    @Override
    public boolean deleteCompany(long id) {
        boolean execution = false;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            try {
                connection.setAutoCommit(false);
                computerDao.deleteComputerByCompanyId(id, connection);
                companyDao.deleteCompanyById(id, connection);
                execution = true;
                connection.commit();
                LOGGER.info("deleted company : " + id);
            } catch (DaoException e) {
                LOGGER.warn("can't delete the company", e);
                throw new ServiceException("cant' delete the company", e);
            } catch (SQLException e) {
                throw new ServiceException("Error with the connection", e);
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new ServiceException("Error with the connection", e);
                }
            }
        } catch (SQLException e1) {
            LOGGER.error("Unable to get the connection.", e1);
            throw new DaoException("Unable to get the connection.", e1);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error while closing connection :", e);
        }

        return execution;
    }

}
