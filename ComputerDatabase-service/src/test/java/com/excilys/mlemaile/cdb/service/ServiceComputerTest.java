package com.excilys.mlemaile.cdb.service;

import static org.junit.Assert.assertEquals;

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

import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.persistence.CompanyDao;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;
import com.excilys.mlemaile.cdb.persistence.FieldSort;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring.xml" })
public class ServiceComputerTest {

    @Mock
    private ComputerDao     mockComputerDao;

    @Mock
    private CompanyDao      mockCompanyDao;

    @Autowired
    @InjectMocks
    private ServiceComputer serviceComputer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateComputer() {
        assertEquals("create computer does not work as intended", true,
                serviceComputer.createComputer("", null, null, 0));
    }

    @Test
    public void testUpdatecomputer() {
        com.excilys.mlemaile.cdb.model.Computer computer = new Computer();
        assertEquals("Update Computer does not work as intended", true,
                serviceComputer.updatecomputer(computer));
    }

    @Test
    public void testListComputer() {
        List<Computer> computers = new ArrayList<>();
        computers.add(new Computer());
        Mockito.when(mockComputerDao.listSortSearchNumberComputer(10, 0, FieldSort.NAME, null))
                .thenReturn(computers);
        assertEquals("List computers does not work as intended", computers,
                serviceComputer.listSortSearchNumberComputer(10, 0, FieldSort.NAME, null));
    }

    @Test
    public void testGetComputer() {
        Computer computer = new Computer();
        Mockito.when(mockComputerDao.getComputerById(1)).thenReturn(Optional.ofNullable(computer));
        assertEquals("Get computer does not work as intended", computer,
                serviceComputer.getComputerById(1).get());

    }

    @Test
    public void testDeleteComputer() {
        Computer computer = new Computer();
        computer.setId(2);
        assertEquals("Update Computer does not work as intended", true,
                serviceComputer.deleteComputer(computer));
    }

}
