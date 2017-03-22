package com.excilys.mlemaile.cdb.persistence;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.model.Computer;

public class ComputerDaoSqlTest {
	private ComputerDao computerDao;
	private IDatabaseTester databaseTester;
	
	private IDataSet readDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new File("src/test/resources/databaseTest.xml"));
	}
	
	private void cleanlyInsertDataset(IDataSet dataSet) throws Exception {
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
				new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
				.configure(params.properties().setFileName("database.properties"));
		Configuration config = builder.getConfiguration();
		String startUrl =config.getString("start-url");
		String host = config.getString("host");
		String database =config.getString("database");
		String user = config.getString("user");
		String password = config.getString("password");
		String zeroDataTimeBehavior = config.getString("zeroDateTimeBehavior");
		String url = startUrl+host+"/"+database+"?user="+user
                + "&password="+password
                + "&zeroDateTimeBehavior="+zeroDataTimeBehavior;
		
		databaseTester = new JdbcDatabaseTester(
			"com.mysql.jdbc.Driver", url);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		
	}
	
	@Before
	public void setUp() throws Exception {
		IDataSet dataSet = readDataSet();
		cleanlyInsertDataset(dataSet);
		computerDao = DaoFactory.INSTANCE.getComputerDao();
		databaseTester.onSetup();
	}
	
	@After
	public void tearDown() throws Exception{
		computerDao = null;
		databaseTester.onTearDown();
	}
	
	@Test
	public void testCreateComputer() {
		LocalDate date = LocalDate.now();
		Company company = DaoFactory.INSTANCE.getCompanyDao().getCompany(1);
		Computer c = new Computer.Builder("Test").introduced(date).company(company).build();
		computerDao.createComputer(c);
		assertEquals("Creation of a computer is not working",c.toString(),computerDao.getComputer(c.getId()).toString());
	}

	@Test
	public void testListSomecomputer() {
		List<Computer> computers = computerDao.listSomecomputer(10, 0);
		assertEquals("Read method is not correct", 10, computers.size());
		assertEquals("Read method is not correct","ID : 1 name : computer1 manufacturer [ID : 1 name : company1] introduced : 2012-02-05 Discontinued : 2015-08-24",computers.get(0).toString());
		assertEquals("Read method is not correct","ID : 2 name : computer2 manufacturer [ID : 1 name : company1] introduced : 2014-05-10 Discontinued : 2016-04-09",computers.get(1).toString());
		assertEquals("Read method is not correct","ID : 3 name : computer3 manufacturer [ID : 3 name : company3] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(2).toString());
		assertEquals("Read method is not correct","ID : 4 name : computer4 manufacturer [ID : 1 name : company1] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(3).toString());
		assertEquals("Read method is not correct","ID : 5 name : computer5 manufacturer [ID : 2 name : company2] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(4).toString());
		assertEquals("Read method is not correct","ID : 6 name : computer6 manufacturer [ID : 2 name : company2] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(5).toString());
		assertEquals("Read method is not correct","ID : 7 name : computer7 manufacturer [ID : 1 name : company1] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(6).toString());
		assertEquals("Read method is not correct","ID : 8 name : computer8 manufacturer [ID : 3 name : company3] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(7).toString());
		assertEquals("Read method is not correct","ID : 9 name : computer9 manufacturer [ID : 3 name : company3] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(8).toString());
		assertEquals("Read method is not correct","ID : 10 name : computer10 manufacturer [ID : 2 name : company2] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(9).toString());
	}
	
	@Test
	public void testListSomecomputerLastPage() {
		List<Computer> computers = computerDao.listSomecomputer(10, 10);
		assertEquals("Read method is not correct", 3, computers.size());
		assertEquals("Read method is not correct","ID : 11 name : computer11 manufacturer [ID : 3 name : company3] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(0).toString());
		assertEquals("Read method is not correct","ID : 12 name : computer12 manufacturer [ID : 1 name : company1] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(1).toString());
		assertEquals("Read method is not correct","ID : 13 name : computer13 manufacturer [ID : 3 name : company3] introduced : 2016-04-09 Discontinued : 2017-02-18",computers.get(2).toString());
	}

	@Test
	public void testGetComputer() {
		Computer computer = computerDao.getComputer(2);
		assertEquals("Read method is not correct","ID : 2 name : computer2 manufacturer [ID : 1 name : company1] introduced : 2014-05-10 Discontinued : 2016-04-09",computer.toString());
	}

	@Test
	public void testUpdateComputer() {
		Computer computer = computerDao.getComputer(2);
		computer.setName("Test");
		computerDao.updateComputer(computer);
		assertEquals("Read method is not correct","ID : 2 name : Test manufacturer [ID : 1 name : company1] introduced : 2014-05-10 Discontinued : 2016-04-09",computer.toString());
	}

	@Test
	public void testDeleteComputer() {
		LocalDate date = LocalDate.now();
        Company company = DaoFactory.INSTANCE.getCompanyDao().getCompany(1);
        Computer c = new Computer.Builder("Test").introduced(date).company(company).build();
        computerDao.createComputer(c);
        computerDao.deleteComputer(c.getId());
        assertEquals("Deletion of a computer is not working","",computerDao.getComputer(c.getId()).getName());
	}

}
