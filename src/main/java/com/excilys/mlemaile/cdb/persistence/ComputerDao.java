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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mlemaile.cdb.model.Computer;
import com.mysql.jdbc.Statement;

/**
 * This class communicate with the database to store, update and read computers in the database.
 * @author Matthieu Lemaile
 *
 */
public class ComputerDao {
	
	private final static Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	private ComputerDao(){} //we don't need any constructor
	
	/**
	 * this method map the result of a request (in the ResultSet) with the computer object
	 * @param resultSet the result of the request
	 * @return a List of computers
	 */
	private static List<Computer> bindingComputer(ResultSet resultSet){
		ArrayList<Computer> computers = new ArrayList<>();
		try{
			while(resultSet.next()){
				Computer.Builder builder = new Computer.Builder(resultSet.getString("name")).id(resultSet.getInt("id"));
				Timestamp tsIntro = resultSet.getTimestamp("introduced");
				if(tsIntro!=null){
					builder = builder.introduced(tsIntro.toLocalDateTime().toLocalDate());
				}
				Timestamp tsDiscontinued = resultSet.getTimestamp("discontinued");
				if(tsDiscontinued!=null && tsIntro!=null){
					if(tsDiscontinued.after(tsIntro) || tsDiscontinued.equals(tsIntro)){
						builder = builder.discontinued(tsDiscontinued.toLocalDateTime().toLocalDate());
					}else{
						throw new RuntimeException("discontinued date not used  it was before the introduced date.\n ID : "+resultSet.getInt("id")
								+ "Introduced "+tsIntro.toLocalDateTime().toLocalDate()+" discontinued :"+tsDiscontinued.toLocalDateTime().toLocalDate());
					}
				}else if(tsDiscontinued!=null){
					builder = builder.discontinued(tsDiscontinued.toLocalDateTime().toLocalDate());
				}
				builder = builder.companyId(resultSet.getInt("company_id"));
				computers.add(builder.build());
			}
		}catch(SQLException e){
			logger.error("can't dinf computer : ",e);
		}
		return computers;
	}
	
	/**
	 * This method store the given computer in the database
	 * @param computer the computer to store
	 * @return A boolean which is true if the execution went well
	 */
	public static boolean createComputer(Computer computer){
		boolean executed = false;
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO computer (name,introduced,discontinued,company_id) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, computer.getName());
			LocalDate introduced = computer.getIntroduced();
			if(introduced!=null){
				preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.of(computer.getIntroduced(), LocalTime.of(0, 0))));
			}else{
				preparedStatement.setNull(2, Types.TIMESTAMP);
			}
			LocalDate discontinued = computer.getDiscontinued();
			if(discontinued!=null){
				preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(computer.getDiscontinued(), LocalTime.of(0, 0))));
			}else{
				preparedStatement.setNull(3, Types.TIMESTAMP);
			}
			if(computer.getCompany_id()>0){
				preparedStatement.setInt(4, computer.getCompany_id());
			}else{
				preparedStatement.setNull(4, Types.BIGINT);
			}
			if(preparedStatement.executeUpdate()>0){
				ResultSet generatedKey = preparedStatement.getGeneratedKeys();
				if(generatedKey.next()){
					computer.setId((int)generatedKey.getLong(1));
					executed = true;
					logger.info("Computer created : "+computer.toString());
				}
			}
		} catch (SQLException e) {
			logger.error("Error storing the computer : ",e);
		} finally{
			DatabaseConnection.closeConnection();
		}
		return executed;
	}
	
	/**
	 * This method list all computers on the database
	 * @return A List of all computers
	 */
	public static List<Computer> listComputers(){
		ArrayList<Computer> computers = new ArrayList<>(); //permet d'éviter de retourner null
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement("select * from computer");
			ResultSet resultSet = preparedStatement.executeQuery();
			computers = (ArrayList<Computer>) bindingComputer(resultSet);
		} catch (SQLException e) {
			logger.error("Can't list computers : ",e);
		} finally{
			DatabaseConnection.closeConnection();
		}
		return computers;
	}
	
	/**
	 * This method list a number of computer.
	 * @param number The number of computer to list
	 * @param idFirst The id of the first computer to list
	 * @return a List of Computer
	 */
	public static List<Computer> listSomecomputer(int number,int idFirst){
		ArrayList<Computer> computers = new ArrayList<>(); //permet d'éviter de retourner null
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement("SELECT * FROM computer ORDER BY id ASC LIMIT ?,?");
			preparedStatement.setInt(1, idFirst);
			preparedStatement.setInt(2, number);
			ResultSet resultSet = preparedStatement.executeQuery();
			computers = (ArrayList<Computer>) bindingComputer(resultSet);
		} catch (SQLException e) {
			logger.error("Can't list computers : ",e);
		} finally{
			DatabaseConnection.closeConnection();
		}
		return computers;
	}
	
	/**
	 * This method return the computer identified by the id. If it doesn't exist, it return a computer with an empty name.
	 * @param id
	 * @return
	 */
	public static Computer getComputer(int id){
		ArrayList<Computer> computers = new ArrayList<>(); //initialising computers
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("select * from computer where id=?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			computers = (ArrayList<Computer>) bindingComputer(resultSet);
		} catch (SQLException e) {
			logger.error("Can't retrieve computer : ",e);
		} finally{
			DatabaseConnection.closeConnection();
		}
		Computer c = new Computer.Builder("").build();
		if(computers.size()==1){
			c = computers.get(0);
		}
		return c;
	}
	
	/**
	 * This method change all the attribute of the computer identified by the id to those in the given computer
	 * @param computer The id is the computer to change, other field are value to be stored
	 * @return A boolean which is true if the execution went well
	 */
	public static boolean updateComputer(Computer computer){
		boolean executed = false;
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE computer SET name=?, introduced=?,discontinued=?,company_id=? where id = ?");
			preparedStatement.setString(1, computer.getName());
			LocalDate introduced = computer.getIntroduced();
			if(introduced!=null){
				preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.of(computer.getIntroduced(), LocalTime.of(0, 0))));
			}else{
				preparedStatement.setNull(2, Types.TIMESTAMP);
			}
			LocalDate discontinued = computer.getDiscontinued();
			if(discontinued!=null){
				preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(computer.getDiscontinued(), LocalTime.of(0, 0))));
			}else{
				preparedStatement.setNull(3, Types.TIMESTAMP);
			}
			if(computer.getCompany_id()>0){
				preparedStatement.setInt(4, computer.getCompany_id());
			}else{
				preparedStatement.setNull(4, Types.BIGINT);
			}
			preparedStatement.setInt(5, computer.getId());
			if(preparedStatement.executeUpdate()!=0){
				executed = true;
				logger.info("updated computer to : "+computer.toString());
			}
		} catch (SQLException e) {
			logger.error("Can't update computer : ",e);
		} finally{
			DatabaseConnection.closeConnection();
		}
		return executed;
	}
	
	/**
	 * This method delete the computer identified by the id of the given computer
	 * @param computer the computer to delete
	 * @return A boolean which is true if the execution went well
	 */
	public static boolean deleteComputer(Computer computer){
		boolean executed = false;
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM computer where id=?");
			preparedStatement.setInt(1, computer.getId());
			if(preparedStatement.executeUpdate()!=0){
				executed = true;
				logger.info("computer deleted : "+computer.toString());
			}
		} catch (SQLException e) {
			logger.error("Can't delete computer : ",e);
		} finally{
			DatabaseConnection.closeConnection();
		}
		return executed;
	}
}
