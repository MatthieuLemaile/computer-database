package com.excilys.mlemaile.cdb.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.excilys.mlemaile.cdb.model.Company;

public class TestCompany {
	@Test
	public void testCompany(){
		int id = 4;
		String name = "Company's name";
		Company company = new Company.Builder().name(name).id(id).build();
		assertEquals("The company id is not properly set",id,company.getId());
		assertEquals("The company name is not properly set",name, company.getName());
	}
}

