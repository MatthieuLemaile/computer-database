package com.excilys.mlemaile.cdb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;
import com.excilys.mlemaile.cdb.service.ServiceCompany;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring.xml" })
public class ServiceCompanyTest {

    @Mock
    private ComputerDao    mockComputerDao;

    @Mock
    private CompanyDao     mockCompanyDao;

    @Autowired
    @InjectMocks
    private ServiceCompany serviceCompany;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListcompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company());
        ReflectionTestUtils.setField(serviceCompany, "companyDao", mockCompanyDao);
        Mockito.when(mockCompanyDao.listNumberCompaniesStartingAt(10, 0L)).thenReturn(companies);
        List<Company> companiesReturned = serviceCompany.listCompanies(10, 0L);
        Mockito.verify(mockCompanyDao).listNumberCompaniesStartingAt(10, 0L);
        assertEquals("List Companies does not work as intended", companies, companiesReturned);

    }

    @Test
    public void testGetCompany() {
        Company company = new Company();
        Optional<Company> opt = Optional.ofNullable(company);
        Mockito.when(mockCompanyDao.getCompanyById(1)).thenReturn(opt);
        assertEquals("Get Company does not work as intended", company,
                serviceCompany.getCompanyById(1));
    }

    @Test
    public void testDeleteCompanyId() {
        Company c = new Company();
        Mockito.when(mockCompanyDao.getCompanyById(1)).thenReturn(Optional.ofNullable(c));
        assertTrue("Delete company does not work as intended", serviceCompany.deleteCompany(1));
    }
}
