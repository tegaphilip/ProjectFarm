package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.NamingException;

import util.DateUtil;
import util.Password;

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
	
	public static boolean createUser(HashMap<String, String> userParams) {
		try {
			preparedStatement = connection.prepareStatement(SQLQueries.CREATE_USER);
			preparedStatement.setString(1, userParams.get("name"));
			preparedStatement.setString(2, userParams.get("email"));
			//encrypt password
			preparedStatement.setString(3, Password.getSaltedHash(userParams.get("password")));
			preparedStatement.setInt(4, Integer.valueOf(userParams.get("user_type")));
			preparedStatement.setString(5, DateUtil.getCurrentDateTime());
			preparedStatement.setString(6, DateUtil.getCurrentDateTime());
			int result = preparedStatement.executeUpdate();
			if (result == 0) {
				UserDB.lastErrorMessage = "User was not successfully created!";
				return false;
			}
			return true;
		} catch (Exception e) {
			UserDB.lastErrorMessage = e.getMessage();
			return false;
		}
	}
}
