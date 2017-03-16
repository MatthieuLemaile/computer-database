package com.excilys.mlemaile.cdb.service;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.DaoFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CompanyDao.class,DaoFactory.class})
public class ServiceCompanyTest {

	@Test
	public void testListcompanies() {
		List<Company> companies = new ArrayList<>();
		companies.add(new Company.Builder().build());
		CompanyDao mockCompanyDao = mock(CompanyDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getCompanyDao()).thenReturn(mockCompanyDao);
		when(mockCompanyDao.listSomeCompanies(10,0 )).thenReturn(companies);
		assertEquals("List Companies does not work as intended",companies,ServiceCompany.INSTANCE.listcompanies(10, 0));
	}

	@Test
	public void testGetCompany() {
		Company company = new Company.Builder().build();
		CompanyDao mockCompanyDao = mock(CompanyDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getCompanyDao()).thenReturn(mockCompanyDao);
		when(mockCompanyDao.getCompany(1)).thenReturn(company);
		assertEquals("Get Company does not work as intended",company,ServiceCompany.INSTANCE.getCompany(1));
	}

}
