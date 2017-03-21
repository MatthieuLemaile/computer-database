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
     * Permet de créer un computer à partir des informations suivantes.
     * @param name son nom
     * @param introduced sa date de mise sur le marché
     * @param discontinued sa date d'arrêt
     * @param companyId l'identifiant de la company fabricante.
     * @return un booléen indiquant si l'opération s'est bien passée.
     */
    public boolean createComputer(String name, LocalDate introduced, LocalDate discontinued,
            long companyId) {
        boolean computerCreated = false;
        Company company = null;
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
        } catch (IllegalArgumentException | DaoException e) {
            LOGGER.warn("Can't create the computer :", e);
        }
        return computerCreated;
    }

    /**
     * Permet de mettre à jour un Computer. L'id est la référence de l'ordinateur, il ne doit pas
     * changer.
     * @param c le computer à mettreà jour
     * @return un boolééen indiquant si l'opération s'est bien déroulée (true);
     */
    public boolean updatecomputer(Computer c) {
        boolean execution = false;
        try {
            execution = DaoFactory.INSTANCE.getComputerDao().updateComputer(c);
            LOGGER.info("updated computer to : " + c.toString());
        } catch (DaoException e) {
            LOGGER.warn("cant' update the computer", e);
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
        }
        return execution;
    }

    /**
     * liste number Computer à partir du computer idFirst, s'il y en a suffisamment.
     * @param number le nombre de computer à lister
     * @param idFirst l'id du premier computer à lister
     * @return un List de Computer
     */
    public List<Computer> listComputer(int number, long idFirst) {
        List<Computer> computers = new ArrayList<>();
        try {
            computers = DaoFactory.INSTANCE.getComputerDao().listSomecomputer(number, idFirst);
        } catch (DaoException e) {
            LOGGER.warn("cant' list computers", e);
        }
        return computers;
    }

    /**
     * Retourne le computer d'id id.
     * @param id l'id du computer à retourner
     * @return un Computer
     */
    public Computer getComputer(long id) {
        Computer computer = new Computer.Builder("").build();
        try {
            computer = DaoFactory.INSTANCE.getComputerDao().getComputer(id);
        } catch (DaoException e) {
            LOGGER.warn("cant' find the computer", e);
        }
        return computer;
    }

    /**
     * Supprime un computer.
     * @param c le Computer à supprimer
     * @return un booléen indiquant si l'opération s'est bien passé (true).
     */
    public boolean deleteComputer(Computer c) {
        boolean execution = false;
        try {
            execution = DaoFactory.INSTANCE.getComputerDao().deleteComputer(c.getId());
            LOGGER.info("computer deleted : " + c.toString());
        } catch (DaoException e) {
            LOGGER.warn("cant' delete the computer", e);
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
            if (DaoFactory.INSTANCE.getComputerDao().deleteComputer(id)) {
                execution = true;
                LOGGER.info("computer deleted : computer n°" + id);
            } else {
                LOGGER.warn("cant' delete the computer" + id);
            }
        } catch (DaoException e) {
            LOGGER.warn("cant' delete the computer", e);
        }
        return execution;
    }

    /**
     * Cette méthode permet de compter le nombre d'ordinateur.
     * @return le nombre d'ordinateur
     */
    public int countComputers() {
        int numberOfComputers = 0;
        try {
            numberOfComputers = DaoFactory.INSTANCE.getComputerDao().countComputer();
        } catch (DaoException e) {
            LOGGER.warn("can't count computers", e);
        }
        return numberOfComputers;
    }
}
