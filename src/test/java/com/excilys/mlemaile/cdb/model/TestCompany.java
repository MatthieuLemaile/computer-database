package com.excilys.mlemaile.cdb.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.excilys.mlemaile.cdb.service.model.Company;

public class TestCompany {
	@Test
	public void testCompany(){
		int id = 4;
		String name = "Company's name";
        Company company = new Company(id, name);
		assertEquals("The company id is not properly set",id,company.getId());
		assertEquals("The company name is not properly set",name, company.getName());
	}
}

