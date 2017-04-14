package com.excilys.mlemaile.cdb.persistence;


import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.excilys.mlemaile.cdb.persistence.DatabaseConnection;

public class DatabaseconnectionTest {

	@Test
	public void test() {
		assertNotNull("failed to connect to the database",DatabaseConnection.INSTANCE.getConnection());
	}

}
