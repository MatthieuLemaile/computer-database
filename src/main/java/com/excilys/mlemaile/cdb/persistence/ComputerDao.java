package com.excilys.mlemaile.cdb.persistence;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import com.excilys.mlemaile.cdb.service.model.Computer;

public interface ComputerDao {

    /**
     * This method store the given computer in the database.
     * @param computer the computer to store
     */
    void createComputer(Computer computer);

    /**
     * This method list a number of computer.
     * @param number The number of computer to list
     * @param idFirst The id of the first computer to list
     * @param sort The field to sort computer by
     * @param search The string that companuter and company must contains
     * @return a List of Computer
     */
    List<Computer> listSortSearchNumberComputer(int number, long idFirst, FieldSort sort,
            String search);

    /**
     * This method return the computer identified by the id. If it doesn't exist, it return a
     * computer with an empty name.
     * @param id l'id du computer.
     * @return un Computer
     */
    Optional<Computer> getComputerById(long id);

    /**
     * This method count the total number of computer in the database.
     * @param search The string that Company and Computer must contains
     * @return The number of computer
     */
    int countSearchedComputer(String search);

    /**
     * This method change all the attribute of the computer identified by the id to those in the
     * given computer.
     * @param computer The id is the computer to change, other field are value to be stored
     */
    void updateComputer(Computer computer);

    /**
     * This method delete the computer identified by the id of the given computer.
     * @param id The id of the computer to delete
     */
    void deleteComputerById(long id);

    /**
     * Delete Computer by company Id.
     * @param id the id of the company of computer to delete
     * @param connection the connection to use
     */
    void deleteComputerByCompanyId(long id, Connection connection);

}