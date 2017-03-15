package com.excilys.mlemaile.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.model.Computer;
import com.mysql.jdbc.Statement;

/**
 * This enum communicate with the database to store, update and read computers
 * in the database.
 * 
 * @author Matthieu Lemaile
 *
 */
public enum ComputerDao {
	INSTANCE();
	//private final static Logger logger = LoggerFactory.getLogger(ComputerDao.class);

	/**
	 * this method map the result of a request (in the ResultSet) with the
	 * computer object
	 * 
	 * @param resultSet
	 *            the result of the request
	 * @return a List of computers
	 */
	private List<Computer> bindingComputer(ResultSet resultSet) {
		ArrayList<Computer> computers = new ArrayList<>();

		try {
			while (resultSet.next()) {
				Computer.Builder builder = new Computer.Builder(resultSet.getString("name"))
						.id(resultSet.getLong("id"));
				Timestamp tsIntro = resultSet.getTimestamp("introduced");
				if (tsIntro != null) {
					builder = builder.introduced(tsIntro.toLocalDateTime().toLocalDate());
				}
				Timestamp tsDiscontinued = resultSet.getTimestamp("discontinued");
				if (tsDiscontinued != null && tsIntro != null) {
					if (tsDiscontinued.after(tsIntro) || tsDiscontinued.equals(tsIntro)) {
						builder = builder.discontinued(tsDiscontinued.toLocalDateTime().toLocalDate());
					} else {
						throw new RuntimeException(
								"discontinued date not used  it was before the introduced date.\n ID : "
										+ resultSet.getLong("id") + "Introduced "
										+ tsIntro.toLocalDateTime().toLocalDate() + " discontinued :"
										+ tsDiscontinued.toLocalDateTime().toLocalDate());
					}
				} else if (tsDiscontinued != null) {
					builder = builder.discontinued(tsDiscontinued.toLocalDateTime().toLocalDate());
				}
				Company company = new Company.Builder().id(resultSet.getLong("company_id")).name(resultSet.getString("company_name")).build();
				builder = builder.company(company);
				computers.add(builder.build());
			}
		} catch (SQLException e) {
			throw new DaoException("can't dinf computer : ", e);
		}
		return computers;
	}

	/**
	 * This method store the given computer in the database
	 * 
	 * @param computer
	 *            the computer to store
	 * @return A boolean which is true if the execution went well
	 */
	public boolean createComputer(Computer computer) {
		boolean executed = false;
		Connection connection = DatabaseConnection.INSTANCE.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet generatedKey = null;
		try {
			preparedStatement = connection.prepareStatement(
					"INSERT INTO computer (name,introduced,discontinued,company_id) values(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, computer.getName());
			LocalDate introduced = computer.getIntroduced();
			if (introduced != null) {
				preparedStatement.setTimestamp(2,
						Timestamp.valueOf(LocalDateTime.of(computer.getIntroduced(), LocalTime.of(0, 0))));
			} else {
				preparedStatement.setNull(2, Types.TIMESTAMP);
			}
			LocalDate discontinued = computer.getDiscontinued();
			if (discontinued != null) {
				preparedStatement.setTimestamp(3,
						Timestamp.valueOf(LocalDateTime.of(computer.getDiscontinued(), LocalTime.of(0, 0))));
			} else {
				preparedStatement.setNull(3, Types.TIMESTAMP);
			}
			if (computer.getCompany() != null && computer.getCompany().getId() > 0) {
				preparedStatement.setLong(4, computer.getCompany().getId());
			} else {
				preparedStatement.setNull(4, Types.BIGINT);
			}
			if (preparedStatement.executeUpdate() > 0) {
				generatedKey = preparedStatement.getGeneratedKeys();
				if (generatedKey.next()) {
					computer.setId(generatedKey.getLong(1));
					executed = true;
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Error storing the computer : ", e);
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(connection);
			DatabaseConnection.INSTANCE.closeStatement(preparedStatement);
			DatabaseConnection.INSTANCE.closeResulSet(generatedKey);
		}
		return executed;
	}

	/**
	 * This method list a number of computer.
	 * 
	 * @param number
	 *            The number of computer to list
	 * @param idFirst
	 *            The id of the first computer to list
	 * @return a List of Computer
	 */
	public List<Computer> listSomecomputer(int number, long idFirst) {
		ArrayList<Computer> computers = new ArrayList<>(); // permet d'éviter de
															// retourner null
		Connection connection = DatabaseConnection.INSTANCE.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT c.id as id ,c.name as name ,c.introduced as introduced ,c.discontinued as discontinued ,company.id as company_id ,company.name as company_name FROM computer as c LEFT JOIN company ON c.company_id=company.id ORDER BY c.id ASC LIMIT ?,?");
			preparedStatement.setLong(1, idFirst);
			preparedStatement.setInt(2, number);
			resultSet = preparedStatement.executeQuery();
			computers = (ArrayList<Computer>) ComputerDao.INSTANCE.bindingComputer(resultSet);
		} catch (SQLException e) {
			throw new DaoException("Can't list computers : ", e);
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(connection);
			DatabaseConnection.INSTANCE.closeStatement(preparedStatement);
			DatabaseConnection.INSTANCE.closeResulSet(resultSet);
		}
		return computers;
	}

	/**
	 * This method return the computer identified by the id. If it doesn't
	 * exist, it return a computer with an empty name.
	 * 
	 * @param id
	 * @return
	 */
	public Computer getComputer(long id) {
		ArrayList<Computer> computers = new ArrayList<>(); // initialising
															// computers
		Connection connection = DatabaseConnection.INSTANCE.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT c.id as id ,c.name as name ,c.introduced as introduced ,c.discontinued as discontinued ,company.id as company_id ,company.name as company_name FROM computer as c LEFT JOIN company ON c.company_id=company.id WHERE c.id=?");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			computers = (ArrayList<Computer>) ComputerDao.INSTANCE.bindingComputer(resultSet);
		} catch (SQLException e) {
			throw new DaoException("Can't retrieve computer : ", e);
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(connection);
			DatabaseConnection.INSTANCE.closeStatement(preparedStatement);
			DatabaseConnection.INSTANCE.closeResulSet(resultSet);
		}
		Computer c = new Computer.Builder("").build();
		if (computers.size() == 1) {
			c = computers.get(0);
		}
		return c;
	}

	/**
	 * This method change all the attribute of the computer identified by the id
	 * to those in the given computer
	 * 
	 * @param computer
	 *            The id is the computer to change, other field are value to be
	 *            stored
	 * @return A boolean which is true if the execution went well
	 */
	public boolean updateComputer(Computer computer) {
		boolean executed = false;
		Connection connection = DatabaseConnection.INSTANCE.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(
					"UPDATE computer SET name=?, introduced=?,discontinued=?,company_id=? where id = ?");
			preparedStatement.setString(1, computer.getName());
			LocalDate introduced = computer.getIntroduced();
			if (introduced != null) {
				preparedStatement.setTimestamp(2,
						Timestamp.valueOf(LocalDateTime.of(computer.getIntroduced(), LocalTime.of(0, 0))));
			} else {
				preparedStatement.setNull(2, Types.TIMESTAMP);
			}
			LocalDate discontinued = computer.getDiscontinued();
			if (discontinued != null) {
				preparedStatement.setTimestamp(3,
						Timestamp.valueOf(LocalDateTime.of(computer.getDiscontinued(), LocalTime.of(0, 0))));
			} else {
				preparedStatement.setNull(3, Types.TIMESTAMP);
			}
			if (computer.getCompany() != null && computer.getCompany().getId() > 0) {
				preparedStatement.setLong(4, computer.getCompany().getId());
			} else {
				preparedStatement.setNull(4, Types.BIGINT);
			}
			preparedStatement.setLong(5, computer.getId());
			if (preparedStatement.executeUpdate() != 0) {
				executed = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Can't update computer : ", e);
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(connection);
			DatabaseConnection.INSTANCE.closeStatement(preparedStatement);
		}
		return executed;
	}

	/**
	 * This method delete the computer identified by the id of the given
	 * computer
	 * 
	 * @param computer
	 *            the computer to delete
	 * @return A boolean which is true if the execution went well
	 */
	public boolean deleteComputer(Computer computer) {
		boolean executed = false;
		Connection connection = DatabaseConnection.INSTANCE.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("DELETE FROM computer where id=?");
			preparedStatement.setLong(1, computer.getId());
			if (preparedStatement.executeUpdate() != 0) {
				executed = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Can't delete computer : ", e);
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(connection);
			DatabaseConnection.INSTANCE.closeStatement(preparedStatement);
		}
		return executed;
	}
}