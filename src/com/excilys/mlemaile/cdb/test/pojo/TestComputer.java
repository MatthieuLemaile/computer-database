package com.excilys.mlemaile.cdb.test.pojo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.mlemaile.cdb.pojo.Computer;

public class TestComputer {
	@Test
	public void testCreationComputer(){
		Computer computer = new Computer(5);
		assertNotNull(computer);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreationComputerIllegalArgument(){
		Computer computer = new Computer(-3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreationComputerIllegalArgument0(){
		Computer computer = new Computer(0);
	}
	
	@Test
	public void testComputer(){
		Computer computer = new Computer(5);
		LocalDate introduced = LocalDate.now();
		introduced.minusYears(1);
		computer.setIntroduced(introduced);
		assertEquals("The introduced date is not set properly",introduced,computer.getIntroduced());
		String name = "name of the computer";
		computer.setName(name);
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
		Computer computer = new Computer(4);
		LocalDate introduced = LocalDate.now();
		introduced.minusYears(1);
		LocalDate discontinued = introduced.minusMonths(4);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
	}
}
