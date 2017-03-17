package com.excilys.mlemaile.cdb.persistence;

import java.util.List;

import com.excilys.mlemaile.cdb.model.Company;

public interface CompanyDao {

    /**
     * This method return one company, finding it by the id.
     * @param id the id of the company to retrieve
     * @return the company identified by the id
     */
    Company getCompany(long id);

    /**
     * Retourne number Companies, dans l'ordre des index, triés par ordre
     * ascendant d'index.
     * @param number le nombre de Company à retourner
     * @param idFirst l'index du premier à retourner
     * @return une List de Company
     */
    List<Company> listSomeCompanies(int number, long idFirst);

}