package com.excilys.mlemaile.cdb.persistence;


import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring.xml" })
public class DatabaseconnectionTest {

    @Autowired
    private DataSource dataSource;

	@Test
	public void test() {
        try {
            assertNotNull("failed to connect to the database", dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
