package com.excilys.mlemaile.cdb.persistence.sql;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;
import com.excilys.mlemaile.cdb.persistence.FieldSort;

/**
 * This enum communicate with the database to store, update and read computers in the database.
 * @author Matthieu Lemaile
 */
@Repository
public class ComputerDaoSql implements ComputerDao {
    private static final Logger LOGGER                = LoggerFactory
            .getLogger(ComputerDaoSql.class);

    @PersistenceContext
    private EntityManager       session;

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#createComputer(com.excilys.mlemaile.cdb.service.model.Computer)
     */
    @Override
    public void createComputer(Computer computer) {
        if (computer == null) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("persisting in db");
        }
        this.session.persist(computer);
        this.session.flush();
        this.session.refresh(computer);
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#listSortComputer(int, long)
     */
    @Override
    public List<Computer> listSortSearchNumberComputer(int number, long idFirst, FieldSort sort,
            String search) {
        // String sql = String.format(SQL_SEARCH, sort.toString());
        String searchPattern = search != null ? search + "%" : "%";
        TypedQuery<Computer> query = this.session
                .createQuery(
                        "Select c From Computer as c left join c.company as company where c.name like :search or company.name like :search order by :order asc",
                        Computer.class)
                .setMaxResults(number);// .setFirstResult((int) idFirst);
        query.setParameter("search", searchPattern);
        query.setParameter("order", sort.toString());
        return query.getResultList();
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#getComputerById(long)
     */
    @Override
    public Optional<Computer> getComputerById(long id) {
        Computer computer = this.session.find(Computer.class, id);
        return Optional.ofNullable(computer);
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#updateComputer(com.excilys.mlemaile.cdb.service.model.Computer)
     */
    @Override
    public void updateComputer(Computer computer) {
        // this.session.joinTransaction();
        this.session
                .createQuery(
                        "UPDATE Computer c set c.name = :name, c.introduced = :introduced, c.discontinued = :discontinued, c.company = :company WHERE c.id = :id")
                .setParameter("name", computer.getName())
                .setParameter("introduced", computer.getIntroduced())
                .setParameter("discontinued", computer.getDiscontinued())
                .setParameter("company", computer.getCompany())
                .setParameter("id", computer.getId()).executeUpdate();
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#deleteComputerById(com.excilys.mlemaile.cdb.service.model.Computer)
     */
    @Override
    public void deleteComputerById(long id) {
        Computer computer = this.session.find(Computer.class, id);
        this.session.remove(computer);
    }

    @Override
    public int countSearchedComputer(String search) {
        TypedQuery<Long> query = this.session.createQuery(
                "Select COUNT(c) From Computer as c left join c.company as company where c.name like :search or company.name like :search",
                Long.class);
        String searchPattern = search != null ? search + "%" : "%";
        query.setParameter("search", searchPattern);
        return query.getResultList().get(0).intValue();

    }

    @Override
    public void deleteComputerByCompanyId(Company company) {
        this.session.createQuery("delete from Computer c where c.company = :company")
                .setParameter("company", company).executeUpdate();
    }
}