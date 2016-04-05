package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

public class QueryHelper {
	
	private static PreparedStatement preparedStatement;
	private static Connection connection = null;

	static {
		try {
			connection = DBUtil.getDefaultConnection();
		} catch (ClassNotFoundException | SQLException | NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet findUserByEmail(String email) 
	{
		try {
			preparedStatement = connection.prepareStatement(SQLQueries.FIND_USER_BY_EMAIL);
			preparedStatement.setString(1, email);
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}
	
	public static ResultSet findUserTypes() {
		try {
			preparedStatement = connection.prepareStatement(SQLQueries.FIND_ALL_USER_TYPES);
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}
}
