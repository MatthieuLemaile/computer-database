package com.excilys.mlemaile.cdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;
import com.excilys.mlemaile.cdb.persistence.DaoFactory;
import com.excilys.mlemaile.cdb.persistence.DatabaseConnection;
import com.excilys.mlemaile.cdb.service.model.Company;
import com.mysql.jdbc.Connection;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CompanyDao.class,DaoFactory.class,ComputerDao.class,DatabaseConnection.class})
public class ServiceCompanyTest {

	@Test
	public void testListcompanies() {
		List<Company> companies = new ArrayList<>();
		companies.add(new Company.Builder().build());
		CompanyDao mockCompanyDao = mock(CompanyDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getCompanyDao()).thenReturn(mockCompanyDao);
		when(mockCompanyDao.listNumberCompaniesStartingAt(10,0 )).thenReturn(companies);
		assertEquals("List Companies does not work as intended",companies,ServiceCompany.INSTANCE.listcompanies(10, 0));
	}

	@Test
	public void testGetCompany() {
		Company company = new Company.Builder().build();
		Optional<Company> opt = Optional.ofNullable(company);
		CompanyDao mockCompanyDao = mock(CompanyDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getCompanyDao()).thenReturn(mockCompanyDao);
		when(mockCompanyDao.getCompanyById(1)).thenReturn(opt);
		assertEquals("Get Company does not work as intended",company,ServiceCompany.INSTANCE.getCompanyById(1));
	}
	
	@Test
	public void testDeleteCompanyId(){
	    Company c = new Company.Builder().build();
	    DatabaseConnection mockDatabaseConnection = mock(DatabaseConnection.class);
	    Connection mockConnection = mock(Connection.class);
	    DaoFactory mockFactory = mock(DaoFactory.class);
	    CompanyDao mockCompanyDao = mock(CompanyDao.class);
	    ComputerDao mockComputerDao = mock(ComputerDao.class);
	    Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
	    Whitebox.setInternalState(DatabaseConnection.class, "INSTANCE", mockDatabaseConnection);
	    when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
	    when(mockFactory.getCompanyDao()).thenReturn(mockCompanyDao);
	    when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
	    when(mockCompanyDao.getCompanyById(1)).thenReturn(Optional.ofNullable(c));
	    assertTrue("Delete company does not work as intended",ServiceCompany.INSTANCE.deleteCompany(1));
	}
}
