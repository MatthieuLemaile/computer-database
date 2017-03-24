package com.excilys.mlemaile.cdb.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.persistence.DaoFactory;
import com.excilys.mlemaile.cdb.persistence.FieldSort;
import com.excilys.mlemaile.cdb.service.model.Company;
import com.excilys.mlemaile.cdb.service.model.Computer;

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
                Optional<Company> opt = DaoFactory.INSTANCE.getCompanyDao().getCompany(companyId);
                if (opt.isPresent()) {
                    company = opt.get();
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Can't find the company", e);
        }
        try {
            if (companyId > 0) {
                Optional<Company> opt = DaoFactory.INSTANCE.getCompanyDao().getCompany(companyId);

                if (opt.isPresent()) {
                    company = opt.get();
                }
            }
            Computer c = new Computer.Builder(name).introduced(introduced)
                    .discontinued(discontinued).company(company).build();
            DaoFactory.INSTANCE.getComputerDao().createComputer(c);
            computerCreated = true;
            LOGGER.info("Computer created : " + c.toString());
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
     * Create the given computer in the database.
     * @param c the Computer to create
     * @return a boolean which is true if the execution went well
     */
    public boolean createComputer(Computer c) {
        if (c.getCompany() != null) {
            return this.createComputer(c.getName(), c.getIntroduced(), c.getDiscontinued(),
                    c.getCompany().getId());
        } else {
            return this.createComputer(c.getName(), c.getIntroduced(), c.getDiscontinued(), 0);
        }
    }

    /**
     * Update a computer. The id must not change.
     * @param c the computer to update
     * @return a boolean which is true if the execution went well.
     */
    public boolean updatecomputer(Computer c) {
        boolean execution = false;
        try {
            DaoFactory.INSTANCE.getComputerDao().updateComputer(c);
            execution = true;
            LOGGER.info("updated computer to : " + c.toString());
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
        Optional<Computer> optComputer = DaoFactory.INSTANCE.getComputerDao().getComputer(id);

        if (optComputer.isPresent()) {
            Computer c = optComputer.get();
            c.setName(name);
            c.setDiscontinued(discontinued);
            c.setIntroduced(introduced);
            Company company = null;
            if (companyId > 0) {
                Optional<Company> opt = DaoFactory.INSTANCE.getCompanyDao().getCompany(companyId);
                if (opt.isPresent()) {
                    company = opt.get();
                }
            }
            c.setCompany(company);
            try {
                DaoFactory.INSTANCE.getComputerDao().updateComputer(c);
                LOGGER.info("updated computer to : " + c.toString());
                execution = true;
            } catch (DaoException e) {
                LOGGER.warn("cant' update the computer", e);
                throw new ServiceException("cant' update the computer", e);
            }
        }

        return execution;
    }

    /**
     * list number computer from idFirst, if there is enough in the db.
     * @param number The number of computer to list
     * @param idFirst the id of the first computer to list
     * @param search The String that computer and company name must contains
     * @param sort the field to sort the result according to
     * @return a List of Computer
     */
    public List<Computer> listComputer(int number, long idFirst, FieldSort sort, String search) {
        List<Computer> computers = new ArrayList<>();
        try {
            computers = DaoFactory.INSTANCE.getComputerDao().listSortSearchComputer(number, idFirst,
                    sort, search);
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
    public Optional<Computer> getComputer(long id) {
        Optional<Computer> optComputer;
        try {
            optComputer = DaoFactory.INSTANCE.getComputerDao().getComputer(id);
        } catch (DaoException e) {
            LOGGER.warn("cant' find the computer", e);
            throw new ServiceException("cant' find the computer", e);
        }
        return optComputer;
    }

    /**
     * Delete a computer.
     * @param c The computer to delete
     * @return a boolean which is true if the execution went well
     */
    public boolean deleteComputer(Computer c) {
        boolean execution = false;
        try {
            DaoFactory.INSTANCE.getComputerDao().deleteComputer(c.getId());
            LOGGER.info("computer deleted : " + c.toString());
            execution = true;
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
     * This method count the number of computer in the database whose name match the search string.
     * @param search the String to search in the database
     * @return The number of computer.
     */
    public int countComputers(String search) {
        int numberOfComputers = 0;
        try {
            numberOfComputers = DaoFactory.INSTANCE.getComputerDao().countComputer(search);
        } catch (DaoException e) {
            LOGGER.warn("can't count computers", e);
            throw new ServiceException("can't count computers", e);
        }
        return numberOfComputers;
    }
}
