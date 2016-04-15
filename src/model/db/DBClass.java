package model.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBClass {
	
	private static DataSource ds;
	
	private static DataSource getMysqlDataSource() throws NamingException{
		String resourceName = "java:comp/env/jdbc/ProjectFarmMysql";
		Context ctx = new InitialContext();
		ds = (DataSource) ctx.lookup(resourceName);
		return ds;
	}
	
	public static Connection getConnection() throws SQLException, NamingException {
		return getMysqlDataSource().getConnection();
	}
}
