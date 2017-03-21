package com.excilys.mlemaile.cdb.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.persistence.DaoFactory;

public enum ServiceComputer {
    INSTANCE();
    public static final Logger LOGGER = LoggerFactory.getLogger(ServiceComputer.class);

    /**
     * Create a computer from following informations.
     * @param name his name
     * @param introduced his introduced date
     * @param discontinued his discontinued date
     * @param companyId the manufacturer id
     * @return a boolean, which is true if the execution went well
     */
    public boolean createComputer(String name, LocalDate introduced, LocalDate discontinued,
            long companyId) {
        boolean computerCreated = false;
        Company company = null;

        try {
            if (companyId > 0) {
                company = DaoFactory.INSTANCE.getCompanyDao().getCompany(companyId);
            }
        } catch (DaoException e) {
            throw new ServiceException("Can't find the company", e);
        }
        try {
            if (companyId > 0) {
                company = DaoFactory.INSTANCE.getCompanyDao().getCompany(companyId);
            }
            Computer c = new Computer.Builder(name).introduced(introduced)
                    .discontinued(discontinued).company(company).build();
            if (DaoFactory.INSTANCE.getComputerDao().createComputer(c)) {
                computerCreated = true;
                LOGGER.info("Computer created : " + c.toString());
            }
        } catch (DaoException e) {
            LOGGER.warn("Can't create the computer", e);
            throw new ServiceException("Can't create the computer", e);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Can't create the computer : " + e.getMessage(), e);
            throw new ServiceException("Can't create the computer : " + e.getMessage(), e);
        }
        return computerCreated;
    }

    /**
     * Update a computer. The id must not change.
     * @param c the computer to update
     * @return a boolean which is true if the execution went well.
     */
    public boolean updatecomputer(Computer c) {
        boolean execution = false;
        try {
            if (DaoFactory.INSTANCE.getComputerDao().updateComputer(c)) {
                execution = true;
                LOGGER.info("updated computer to : " + c.toString());
            }
        } catch (DaoException e) {
            LOGGER.warn("cant' update the computer", e);
            throw new ServiceException("cant' update the computer", e);
        }
        return execution;
    }

    /**
     * This method update the computer given the following details.
     * @param id the id of the computer to update
     * @param name The new name of the computer
     * @param introduced The new introduced date of the computer
     * @param discontinued The new discontinued date of the computer
     * @param companyId the new Company of the computer
     * @return a boolean, which is true of the execution went well
     */
    public boolean updatecomputer(long id, String name, LocalDate introduced,
            LocalDate discontinued, long companyId) {
        boolean execution = false;
        Computer c = DaoFactory.INSTANCE.getComputerDao().getComputer(id);
        c.setName(name);
        c.setDiscontinued(discontinued);
        c.setIntroduced(introduced);
        Company company = null;
        if (companyId > 0) {
            company = DaoFactory.INSTANCE.getCompanyDao().getCompany(companyId);
        }
        c.setCompany(company);
        try {
            execution = DaoFactory.INSTANCE.getComputerDao().updateComputer(c);
            LOGGER.info("updated computer to : " + c.toString());
        } catch (DaoException e) {
            LOGGER.warn("cant' update the computer", e);
            throw new ServiceException("cant' update the computer", e);
        }
        return execution;
    }

    /**
     * list number computer from idFirst, if there is enough in the db.
     * @param number The number of computer to list
     * @param idFirst the id of the first computer to list
     * @return a List of Computer
     */
    public List<Computer> listComputer(int number, long idFirst) {
        List<Computer> computers = new ArrayList<>();
        try {
            computers = DaoFactory.INSTANCE.getComputerDao().listSomecomputer(number, idFirst);
        } catch (DaoException e) {
            LOGGER.warn("cant' list computers", e);
            throw new ServiceException("cant' list computers", e);
        }
        return computers;
    }

    /**
     * return the computer whose id is id.
     * @param id The id of the computer to return
     * @return a Computer
     */
    public Computer getComputer(long id) {
        Computer computer = new Computer.Builder("").build();
        try {
            computer = DaoFactory.INSTANCE.getComputerDao().getComputer(id);
        } catch (DaoException e) {
            LOGGER.warn("cant' find the computer", e);
            throw new ServiceException("cant' find the computer", e);
        }
        return computer;
    }

    /**
     * Delete a computer.
     * @param c The computer to delete
     * @return a boolean which is true if the execution went well
     */
    public boolean deleteComputer(Computer c) {
        boolean execution = false;
        try {
            execution = DaoFactory.INSTANCE.getComputerDao().deleteComputer(c.getId());
            LOGGER.info("computer deleted : " + c.toString());
        } catch (DaoException e) {
            LOGGER.warn("cant' delete the computer", e);
            throw new ServiceException("cant' delete the computer", e);
        }
        return execution;
    }

    /**
     * Delete a computer.
     * @param id The id of the computer
     * @return a boolean which is true if the execution went well
     */
    public boolean deleteComputer(long id) {
        boolean execution = false;
        try {
            DaoFactory.INSTANCE.getComputerDao().deleteComputer(id);
            execution = true;
            LOGGER.info("computer deleted : computer n°" + id);
        } catch (DaoException e) {
            LOGGER.warn("cant' delete the computer", e);
            throw new ServiceException("cant' delete the computer", e);
        }
        return execution;
    }

    /**
     * This method count the number of computer in the database.
     * @return The number of computer.
     */
    public int countComputers() {
        int numberOfComputers = 0;
        try {
            numberOfComputers = DaoFactory.INSTANCE.getComputerDao().countComputer();
        } catch (DaoException e) {
            LOGGER.warn("can't count computers", e);
            throw new ServiceException("can't count computers", e);
        }
        return numberOfComputers;
    }
}
