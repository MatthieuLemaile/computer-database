package com.excilys.mlemaile.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.mlemaile.cdb.model.Computer;

public class TestComputer {
	@Test
	public void testCreationComputer(){
		Computer computer = new Computer("");
		assertNotNull(computer);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreationComputerIllegalArgument(){
		Computer computer = new Computer("");
		computer.setId(-3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreationComputerIllegalArgument0(){
		Computer computer = new Computer("");
		computer.setId(0);
	}
	
	@Test
	public void testComputer(){
		String name = "name of the computer";
		Computer computer = new Computer(name);
		computer.setId(5);
		LocalDate introduced = LocalDate.now();
		introduced.minusYears(1);
		computer.setIntroduced(introduced);
		assertEquals("The introduced date is not set properly",introduced,computer.getIntroduced());
		assertEquals("The name is not set properly",name,computer.getName());
		int company_id = 8;
		computer.setCompany_id(company_id);
		assertEquals("The company id is not set properly",company_id,computer.getCompany_id());
		LocalDate discontinued = introduced.plusMonths(4);
		computer.setDiscontinued(discontinued);
		assertEquals("The discontinued date is not set properly",discontinued,computer.getDiscontinued());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testComputerDiscontinuedBeforeIntroduced(){
		Computer computer = new Computer("");
		LocalDate introduced = LocalDate.now();
		introduced.minusYears(1);
		LocalDate discontinued = introduced.minusMonths(4);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
	}
}
