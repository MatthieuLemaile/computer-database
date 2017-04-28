package com.excilys.mlemaile.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

public class TestComputer {
    @Test
    public void testCreationComputer() {
        Computer computer = new Computer();
        assertNotNull(computer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreationComputerIllegalArgument() {
        Computer computer = new Computer();
        computer.setId(-3);
    }

    @Test
    public void testComputer() {
        String name = "name of the computer";
        LocalDate introduced = LocalDate.now();
        introduced.minusYears(1);
        LocalDate discontinued = introduced.plusMonths(4);
        Company company = new Company();
        company.setId(8);

        Computer computer = new Computer(name, introduced, discontinued, company);
        computer.setId(5);
        assertEquals("The introduced date is not set properly", introduced,
                computer.getIntroduced());
        assertEquals("The name is not set properly", name, computer.getName());
        assertEquals("The company id is not set properly", company, computer.getCompany());
        assertEquals("The discontinued date is not set properly", discontinued,
                computer.getDiscontinued());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testComputerDiscontinuedBeforeIntroduced() {
        Computer computer = new Computer();
        LocalDate introduced = LocalDate.now();
        introduced.minusYears(1);
        LocalDate discontinued = introduced.minusMonths(4);
        computer.setIntroduced(introduced);
        computer.setDiscontinued(discontinued);
    }
}
