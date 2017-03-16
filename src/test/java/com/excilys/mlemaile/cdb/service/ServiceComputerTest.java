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

import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.persistence.ComputerDao;
import com.excilys.mlemaile.cdb.persistence.DaoFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ComputerDao.class,DaoFactory.class,Computer.Builder.class})
public class ServiceComputerTest {

	@Test
	public void testCreateComputer() {
		//TODO comment réellement mocke le Builder ??
		Computer computer = new Computer.Builder("").build();
		//Computer.Builder computerBuilder = mock(Computer.Builder.class);
		ComputerDao mockComputerDao = mock(ComputerDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
		when(mockComputerDao.createComputer(computer)).thenReturn(true);
		//when(computerBuilder.build()).thenReturn(computer);
		assertEquals("create computer does not work as intended",true,ServiceComputer.INSTANCE.createComputer("", null, null, 0));
	}
	/*
	@Test
	public void testCreateComputerIllegalArgument() {
		Computer computer = new Computer.Builder("").build();
		Computer.Builder computerBuilder = mock(Computer.Builder.class);
		ComputerDao mockComputerDao = mock(ComputerDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
		when(mockComputerDao.createComputer(computer)).thenReturn(true);
		when(computerBuilder.build()).thenThrow(new IllegalArgumentException(""));
		assertEquals("create computer does not work as intended",false,ServiceComputer.INSTANCE.createComputer("", null, null, 0));
	}
	*/
	@Test
	public void testUpdatecomputer() {
		Computer computer = new Computer.Builder("").build();
		ComputerDao mockComputerDao = mock(ComputerDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
		when(mockComputerDao.updateComputer(computer)).thenReturn(true);
		assertEquals("Update Computer does not work as intended",true,ServiceComputer.INSTANCE.updatecomputer(computer));
	}

	@Test
	public void testListComputer() {
		List<Computer> computers = new ArrayList<>();
		computers.add(new Computer.Builder("").build());
		ComputerDao mockComputerDao = mock(ComputerDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
		when(mockComputerDao.listSomecomputer(10, 0)).thenReturn(computers);
		assertEquals("List computers does not work as intended",computers,ServiceComputer.INSTANCE.listComputer(10, 0));
	}

	@Test
	public void testGetComputer() {
		Computer computer = new Computer.Builder("").build();
		ComputerDao mockComputerDao = mock(ComputerDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
		when(mockComputerDao.getComputer(1)).thenReturn(computer);
		assertEquals("Get computer does not work as intended",computer,ServiceComputer.INSTANCE.getComputer(1));
		
	}

	@Test
	public void testDeleteComputer() {
		Computer computer = new Computer.Builder("").build();
		ComputerDao mockComputerDao = mock(ComputerDao.class);
		DaoFactory mockFactory = mock(DaoFactory.class);
		Whitebox.setInternalState(DaoFactory.class, "INSTANCE", mockFactory);
		when(mockFactory.getComputerDao()).thenReturn(mockComputerDao);
		when(mockComputerDao.deleteComputer(computer)).thenReturn(true);
		assertEquals("Update Computer does not work as intended",true,ServiceComputer.INSTANCE.deleteComputer(computer));
	}

}
