package com.excilys.mlemaile.cdb.persistence.sql;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.excilys.mlemaile.cdb.persistence.ComputerDao;
import com.excilys.mlemaile.cdb.persistence.FieldSort;
import com.excilys.mlemaile.cdb.service.model.Company;
import com.excilys.mlemaile.cdb.service.model.Computer;

/**
 * This enum communicate with the database to store, update and read computers in the database.
 * @author Matthieu Lemaile
 */
@Repository("computerDao")
public class ComputerDaoSql implements ComputerDao {
    public static final String  ID                    = "id";
    public static final String  NAME                  = "name";
    public static final String  INTRODUCED            = "introduced";
    public static final String  DISCONTINUED          = "discontinued";
    private static final String COMPANY_ID            = "company_id";
    public static final String  COMPANY_NAME          = "company_name";
    private static final String SQL_SEARCH            = "SELECT c.id as " + ID + " ,c.name as "
            + NAME + " ,c.introduced as " + INTRODUCED + " ,c.discontinued as " + DISCONTINUED
            + " ,company.id as " + COMPANY_ID + " ,company.name as " + COMPANY_NAME
            + " FROM computer as c LEFT JOIN company ON c.company_id=company.id WHERE c.name LIKE ? OR company.name like ? ORDER BY %s ASC LIMIT ?,?";
    private static final String SQL_SELECT_ID         = "SELECT c.id as " + ID + " ,c.name as "
            + NAME + " ,c.introduced as " + INTRODUCED + " ,c.discontinued as " + DISCONTINUED
            + " ,company.id as " + COMPANY_ID + " ,company.name as " + COMPANY_NAME
            + " FROM computer as c LEFT JOIN company ON c.company_id=company.id WHERE c.id=?";
    private static final String SQL_UPDATE            = "UPDATE computer SET " + NAME + "=?, "
            + INTRODUCED + "=?," + DISCONTINUED + "=?," + COMPANY_ID + "=? where id = ?";
    private static final String SQL_DELETE_BY_ID      = "DELETE FROM computer where id=?";
    private static final String SQL_DELETE_BY_COMPANY = "DELETE FROM computer WHERE company_id=?";
    @Autowired
    private JdbcTemplate        jdbcTemplate;

    //@formatter:off
    private RowMapper<Computer> rowMapper             =
            (resultSet, numRow) -> {
                Computer.Builder builder = new Computer.Builder(resultSet.getString(NAME)).id(resultSet.getLong(ID));
                Timestamp tsIntro = resultSet.getTimestamp(INTRODUCED);
                if (tsIntro != null) {
                    builder = builder.introduced(tsIntro.toLocalDateTime().toLocalDate());
                }
                Timestamp tsDiscontinued = resultSet.getTimestamp(DISCONTINUED);
                if (tsDiscontinued != null && tsIntro != null) {
                    if (tsDiscontinued.after(tsIntro) || tsDiscontinued.equals(tsIntro)) {
                        builder = builder.discontinued(tsDiscontinued.toLocalDateTime().toLocalDate());
                    } else {
                        throw new RuntimeException("discontinued date not used  it was before the introduced date.\n ID : "
                                + resultSet.getLong(ID) + "Introduced " + tsIntro.toLocalDateTime().toLocalDate()
                                + " discontinued :" + tsDiscontinued.toLocalDateTime().toLocalDate());
                    }
                } else if (tsDiscontinued != null) {
                    builder = builder.discontinued(tsDiscontinued.toLocalDateTime().toLocalDate());
                }
                Company company = new Company.Builder().id(resultSet.getLong(COMPANY_ID))
                        .name(resultSet.getString(COMPANY_NAME)).build();
                builder = builder.company(company);
                return builder.build();
            };
    //@formatter:on

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#createComputer(com.excilys.mlemaile.cdb.service.model.Computer)
     */
    @Override
    public void createComputer(Computer computer) {
        if (computer == null) {
            return;
        }
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("computer").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(NAME, computer.getName());
        if (computer.getIntroduced() != null) {
            parameters.put(INTRODUCED, Timestamp
                    .valueOf(LocalDateTime.of(computer.getIntroduced(), LocalTime.of(0, 0))));
        } else {
            parameters.put(INTRODUCED, null);
        }
        if (computer.getDiscontinued() != null) {
            parameters.put(DISCONTINUED, Timestamp
                    .valueOf(LocalDateTime.of(computer.getDiscontinued(), LocalTime.of(0, 0))));
        } else {
            parameters.put(DISCONTINUED, null);
        }
        if (computer.getCompany() != null) {
            parameters.put(COMPANY_ID, computer.getCompany().getId());
        } else {
            parameters.put(COMPANY_ID, null);
        }

        Number id = jdbcInsert.executeAndReturnKey(parameters);
        computer.setId(id.longValue());
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#listSortComputer(int, long)
     */
    @Override
    public List<Computer> listSortSearchNumberComputer(int number, long idFirst, FieldSort sort,
            String search) {
        String sql = String.format(SQL_SEARCH, sort.toString());
        String searchPattern = search != null ? search + "%" : "%";

        List<Computer> computers = jdbcTemplate.query(sql,
                new Object[] {searchPattern, searchPattern, idFirst, number }, rowMapper);
        return computers;
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#getComputerById(long)
     */
    @Override
    public Optional<Computer> getComputerById(long id) {
        Computer computer = null;
        try {
            computer = jdbcTemplate.queryForObject(SQL_SELECT_ID, new Object[] {id },
                    rowMapper);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            computer = null;
        }
        return Optional.ofNullable(computer);
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#updateComputer(com.excilys.mlemaile.cdb.service.model.Computer)
     */
    @Override
    public void updateComputer(Computer computer) {
        if (computer == null) {
            return;
        }
        Object[] param = new Object[5];
        param[0] = computer.getName();

        if (computer.getIntroduced() != null) {
            param[1] = Timestamp
                    .valueOf(LocalDateTime.of(computer.getIntroduced(), LocalTime.of(0, 0)));
        } else {
            param[1] = null;
        }
        if (computer.getDiscontinued() != null) {
            param[2] = Timestamp
                    .valueOf(LocalDateTime.of(computer.getDiscontinued(), LocalTime.of(0, 0)));
        } else {
            param[2] = null;
        }
        if (computer.getCompany() != null) {
            param[3] = computer.getCompany().getId();
        } else {
            param[3] = null;
        }
        param[4] = computer.getId();
        jdbcTemplate.update(SQL_UPDATE, param);
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#deleteComputerById(com.excilys.mlemaile.cdb.service.model.Computer)
     */
    @Override
    public void deleteComputerById(long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public int countSearchedComputer(String search) {
        int numberOfComputers = 0;
        String searchPattern = search != null ? search + "%" : "%";
        String sql = "SELECT count(computer.id) as numberOfComputers FROM computer LEFT JOIN company ON computer.company_id=company.id"
                + " WHERE computer.name LIKE ? OR company.name like ?";
        numberOfComputers = jdbcTemplate.queryForObject(sql,
                new Object[] {searchPattern, searchPattern }, Integer.class);
        return numberOfComputers;
    }

    @Override
    public void deleteComputerByCompanyId(long id) {
        jdbcTemplate.update(SQL_DELETE_BY_COMPANY, id);
    }
}