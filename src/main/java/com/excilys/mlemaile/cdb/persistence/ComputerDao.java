package com.excilys.mlemaile.cdb.persistence;

import java.util.List;

import com.excilys.mlemaile.cdb.model.Computer;

public interface ComputerDao {

    /**
     * This method store the given computer in the database.
     * @param computer the computer to store
     * @return A boolean which is true if the execution went well
     */
    boolean createComputer(Computer computer);

    /**
     * This method list a number of computer.
     * @param number The number of computer to list
     * @param idFirst The id of the first computer to list
     * @return a List of Computer
     */
    List<Computer> listSomecomputer(int number, long idFirst);

    /**
     * This method return the computer identified by the id. If it doesn't
     * exist, it return a computer with an empty name.
     * @param id l'id du computer.
     * @return un Computer
     */
    Computer getComputer(long id);

    /**
     * This method change all the attribute of the computer identified by the id
     * to those in the given computer.
     * @param computer The id is the computer to change, other field are value
     *            to be stored
     * @return A boolean which is true if the execution went well
     */
    boolean updateComputer(Computer computer);

    /**
     * This method delete the computer identified by the id of the given
     * computer.
     * @param computer the computer to delete
     * @return A boolean which is true if the execution went well
     */
    boolean deleteComputer(Computer computer);

}