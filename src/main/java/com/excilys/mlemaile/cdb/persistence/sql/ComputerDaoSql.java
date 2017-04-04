package com.excilys.mlemaile.cdb.persistence.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.mlemaile.cdb.persistence.ComputerDao;
import com.excilys.mlemaile.cdb.persistence.DaoException;
import com.excilys.mlemaile.cdb.persistence.DatabaseConnection;
import com.excilys.mlemaile.cdb.persistence.FieldSort;
import com.excilys.mlemaile.cdb.service.model.Company;
import com.excilys.mlemaile.cdb.service.model.Computer;

/**
 * This enum communicate with the database to store, update and read computers in the database.
 * @author Matthieu Lemaile
 */
public enum ComputerDaoSql implements ComputerDao {
    INSTANCE();
    public static final String  ID           = "id";
    public static final String  NAME         = "name";
    public static final String  INTRODUCED   = "introduced";
    public static final String  DISCONTINUED = "discontinued";
    private static final String COMPANY_ID   = "company_id";
    public static final String  COMPANY_NAME = "company_name";

    /**
     * this method map the result of a request (in the ResultSet) with the computer object.
     * @param resultSet the result of the request
     * @return a List of computers
     */
    private List<Computer> bindingComputer(ResultSet resultSet) {
        ArrayList<Computer> computers = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Computer.Builder builder = new Computer.Builder(resultSet.getString(NAME))
                        .id(resultSet.getLong(ID));
                Timestamp tsIntro = resultSet.getTimestamp(INTRODUCED);
                if (tsIntro != null) {
                    builder = builder.introduced(tsIntro.toLocalDateTime().toLocalDate());
                }
                Timestamp tsDiscontinued = resultSet.getTimestamp(DISCONTINUED);
                if (tsDiscontinued != null && tsIntro != null) {
                    if (tsDiscontinued.after(tsIntro) || tsDiscontinued.equals(tsIntro)) {
                        builder = builder
                                .discontinued(tsDiscontinued.toLocalDateTime().toLocalDate());
                    } else {
                        throw new RuntimeException(
                                "discontinued date not used  it was before the introduced date.\n ID : "
                                        + resultSet.getLong(ID) + "Introduced "
                                        + tsIntro.toLocalDateTime().toLocalDate()
                                        + " discontinued :"
                                        + tsDiscontinued.toLocalDateTime().toLocalDate());
                    }
                } else if (tsDiscontinued != null) {
                    builder = builder.discontinued(tsDiscontinued.toLocalDateTime().toLocalDate());
                }
                Company company = new Company.Builder().id(resultSet.getLong(COMPANY_ID))
                        .name(resultSet.getString(COMPANY_NAME)).build();
                builder = builder.company(company);
                computers.add(builder.build());
            }
        } catch (SQLException e) {
            throw new DaoException("can't find computer : ", e);
        }
        return computers;
    }

    /**
     * This method set parameter of the given prepared statement with the given computer.
     * @param preparedStatement The preparedStatement to set
     * @param computer The computer which contains params
     * @throws SQLException Throw an SQLException if misused
     */
    private void setPreparedStatementCreateUpdate(PreparedStatement preparedStatement,
            Computer computer) throws SQLException {
        preparedStatement.setString(1, computer.getName());
        LocalDate introduced = computer.getIntroduced();
        if (introduced != null) {
            preparedStatement.setTimestamp(2, Timestamp
                    .valueOf(LocalDateTime.of(computer.getIntroduced(), LocalTime.of(0, 0))));
        } else {
            preparedStatement.setNull(2, Types.TIMESTAMP);
        }
        LocalDate discontinued = computer.getDiscontinued();
        if (discontinued != null) {
            preparedStatement.setTimestamp(3, Timestamp
                    .valueOf(LocalDateTime.of(computer.getDiscontinued(), LocalTime.of(0, 0))));
        } else {
            preparedStatement.setNull(3, Types.TIMESTAMP);
        }
        if (computer.getCompany() != null && computer.getCompany().getId() > 0) {
            preparedStatement.setLong(4, computer.getCompany().getId());
        } else {
            preparedStatement.setNull(4, Types.BIGINT);
        }
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#createComputer(com.excilys.mlemaile.cdb.service.model.Computer)
     */
    @Override
    public void createComputer(Computer computer) {
        if (computer == null) {
            return;
        }
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO computer (" + NAME + "," + INTRODUCED + "," + DISCONTINUED
                                + "," + COMPANY_ID + ") values(?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);) {
            setPreparedStatementCreateUpdate(preparedStatement, computer);
            if (preparedStatement.executeUpdate() > 0) {
                try (ResultSet generatedKey = preparedStatement.getGeneratedKeys();) {
                    if (generatedKey.next()) {
                        computer.setId(generatedKey.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error storing the computer : ", e);
        }
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#listSortComputer(int, long)
     */
    @Override
    public List<Computer> listSortSearchNumberComputer(int number, long idFirst, FieldSort sort,
            String search) {
        ArrayList<Computer> computers = new ArrayList<>(); // permet d'éviter de retourner null
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();) {
            String sql = "SELECT c.id as " + ID + " ,c.name as " + NAME + " ,c.introduced as "
                    + INTRODUCED + " ,c.discontinued as " + DISCONTINUED + " ,company.id as "
                    + COMPANY_ID + " ,company.name as " + COMPANY_NAME
                    + " FROM computer as c LEFT JOIN company ON c.company_id=company.id WHERE c.name LIKE ? OR company.name like ? ORDER BY %s ASC LIMIT ?,?";
            sql = String.format(sql, sort.toString());
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                String searchPattern = search != null ? "%" + search + "%" : "%";
                preparedStatement.setString(1, searchPattern);
                preparedStatement.setString(2, searchPattern);
                preparedStatement.setLong(3, idFirst);
                preparedStatement.setInt(4, number);
                try (ResultSet resultSet = preparedStatement.executeQuery();) {
                    computers = (ArrayList<Computer>) ComputerDaoSql.INSTANCE
                            .bindingComputer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't list computers : ", e);
        }
        return computers;
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#getComputerById(long)
     */
    @Override
    public Optional<Computer> getComputerById(long id) {
        ArrayList<Computer> computers = new ArrayList<>(); // initialising computers
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.id as "
                        + ID + " ,c.name as " + NAME + " ,c.introduced as " + INTRODUCED
                        + " ,c.discontinued as " + DISCONTINUED + " ,company.id as " + COMPANY_ID
                        + " ,company.name as " + COMPANY_NAME
                        + " FROM computer as c LEFT JOIN company ON c.company_id=company.id WHERE c.id=?");) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                computers = (ArrayList<Computer>) ComputerDaoSql.INSTANCE
                        .bindingComputer(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't retrieve computer : ", e);
        }
        Computer c = null;
        if (computers.size() == 1) {
            c = computers.get(0);
        }
        return Optional.ofNullable(c);
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#updateComputer(com.excilys.mlemaile.cdb.service.model.Computer)
     */
    @Override
    public void updateComputer(Computer computer) {
        if (computer == null) {
            return;
        }
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("UPDATE computer SET " + NAME + "=?, " + INTRODUCED
                                + "=?," + DISCONTINUED + "=?," + COMPANY_ID + "=? where id = ?");) {
            setPreparedStatementCreateUpdate(preparedStatement, computer);
            preparedStatement.setLong(5, computer.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new DaoException("No operation executed");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update computer : ", e);
        }
    }

    /**
     * @see com.excilys.mlemaile.cdb.persistence.ComputerDao#deleteComputerById(com.excilys.mlemaile.cdb.service.model.Computer)
     */
    @Override
    public void deleteComputerById(long id) {
        if (id <= 0) {
            throw new DaoException("The id " + id + " is not valid");
        }
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("DELETE FROM computer where id=?");) {
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                throw new DaoException("No operation executed");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't delete computer : ", e);
        }
    }

    @Override
    public int countSearchedComputer(String search) {
        int numberOfComputers = 0;
        try (Connection connection = DatabaseConnection.INSTANCE.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "SELECT count(computer.id) as numberOfComputers FROM computer LEFT JOIN company ON computer.company_id=company.id"
                        + " WHERE computer.name LIKE ? OR company.name like ?");) {
            String searchPattern = search != null ? "%" + search + "%" : "%";
            st.setString(1, searchPattern);
            st.setString(2, searchPattern);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    numberOfComputers = rs.getInt("numberOfComputers");
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't count computer : ", e);
        }
        return numberOfComputers;
    }
}