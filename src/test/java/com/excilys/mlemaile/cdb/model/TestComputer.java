package com.excilys.mlemaile.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.mlemaile.cdb.service.model.Company;
import com.excilys.mlemaile.cdb.service.model.Computer;

public class TestComputer {
	@Test
	public void testCreationComputer(){
		Computer computer = new Computer.Builder("").build();
		assertNotNull(computer);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreationComputerIllegalArgument(){
		Computer computer = new Computer.Builder("").build();
		computer.setId(-3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreationComputerIllegalArgument0(){
		Computer computer = new Computer.Builder("").build();
		computer.setId(0);
	}
	
	@Test
	public void testComputer(){
		String name = "name of the computer";
		LocalDate introduced = LocalDate.now();
		introduced.minusYears(1);
		LocalDate discontinued = introduced.plusMonths(4);
		Company company = new Company.Builder().id(8).build();
		
		Computer computer = new Computer.Builder(name).id(5).introduced(introduced).discontinued(discontinued).company(company).build();
		assertEquals("The introduced date is not set properly",introduced,computer.getIntroduced());
		assertEquals("The name is not set properly",name,computer.getName());
		assertEquals("The company id is not set properly",company,computer.getCompany());
		assertEquals("The discontinued date is not set properly",discontinued,computer.getDiscontinued());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testComputerDiscontinuedBeforeIntroduced(){
		Computer computer = new Computer.Builder("").build();
		LocalDate introduced = LocalDate.now();
		introduced.minusYears(1);
		LocalDate discontinued = introduced.minusMonths(4);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
	}
}
