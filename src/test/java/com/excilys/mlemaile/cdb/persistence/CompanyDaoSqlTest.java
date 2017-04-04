package com.excilys.mlemaile.cdb.persistence;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
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

import com.excilys.mlemaile.cdb.service.model.Company;

public class CompanyDaoSqlTest {
	private CompanyDao companyDao;
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
		companyDao = DaoFactory.INSTANCE.getCompanyDao();
		databaseTester.onSetup();
	}
	
	@After
	public void tearDown() throws Exception{
		companyDao = null;
		databaseTester.onTearDown();
	}
	
	@Test
	public void testListSomeCompanies() {
		List<Company> companies = companyDao.listNumberCompaniesStartingAt(10, 0);
		assertEquals("Read method is not correct", 5, companies.size());
		assertEquals("Read method is not correct", "ID : 1 name : company1", companies.get(0).toString());
		assertEquals("Read method is not correct", "ID : 2 name : company2", companies.get(1).toString());
		assertEquals("Read method is not correct", "ID : 3 name : company3", companies.get(2).toString());
		assertEquals("Read method is not correct", "ID : 4 name : company4", companies.get(3).toString());
		assertEquals("Read method is not correct", "ID : 5 name : company5", companies.get(4).toString());
	}
	
	@Test
	public void testGetCompany() {
		Company company = companyDao.getCompanyById(2).get();
		assertEquals("Read method is not correct", "ID : 2 name : company2",company.toString());
	}
	
	@Test
	public void testDeleteCompany(){
	    DaoFactory.INSTANCE.getCompanyDao().deleteCompanyById(5, DatabaseConnection.INSTANCE.getConnection());
	    assertFalse("deleteCompany does not work",DaoFactory.INSTANCE.getCompanyDao().getCompanyById(5).isPresent());
	}

}
