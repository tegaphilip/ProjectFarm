package model.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {

	static final String MYSQL = "mysql";
	static final String SQLITE = "sqlite";
	
	private static DataSource ds;
	public static String dataSourceType = "";
	
	private static DataSource getDataSource(String type) throws NamingException{
		String resourceName = "java:comp/env/jdbc/ProjectFarm";
		if (type.equalsIgnoreCase(SQLITE)) {
			resourceName += "SqLite";
			dataSourceType = type;
		} else {
			resourceName += "Mysql";
			dataSourceType = MYSQL;
		}
		
		Context ctx = new InitialContext();
		ds = (DataSource) ctx.lookup(resourceName);
		return ds;
	}

	public static Connection getMysqlConnection() throws ClassNotFoundException,
			SQLException, NamingException {
		return getDataSource(MYSQL).getConnection();
	}
	
	public static Connection getSqliteConnection() throws ClassNotFoundException,
		SQLException, NamingException {
		return getDataSource(SQLITE).getConnection();
	}
	
	public static Connection getDefaultConnection() throws ClassNotFoundException, SQLException, NamingException {
		return getSqliteConnection();
//		return getMysqlConnection();
	}
}

