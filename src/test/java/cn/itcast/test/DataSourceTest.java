package cn.itcast.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DataSourceTest {
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void testDataSource() throws SQLException {
		Connection conn = dataSource.getConnection();
		System.out.println(conn);
	}
}
