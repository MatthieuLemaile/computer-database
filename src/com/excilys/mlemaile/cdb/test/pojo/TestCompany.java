package com.excilys.mlemaile.cdb.test.pojo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.excilys.mlemaile.cdb.pojo.Company;

public class TestCompany {
	@Test
	public void testCompany(){
		Company company = new Company();
		int id = 4;
		company.setId(id);
		String name = "Company's name";
		company.setName(name);
		assertEquals("The company id is not properly set",id,company.getId());
		assertEquals("The company name is not properly set",name, company.getName());
	}
}

