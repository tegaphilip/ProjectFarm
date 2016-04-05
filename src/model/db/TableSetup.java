package model.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

public class TableSetup {
	
	private Connection dbConn;
	private Statement statement;
	
	public TableSetup() {
		createTables();
	}
	
	private void createTables() {
		try {
			dbConn = DBUtil.getDefaultConnection();
			
			if (!checkIfExists(SQLQueries.USER_TYPE_TABLE)) {
				executeDDL(SQLQueries.CREATE_USER_TYPES_TABLE);
				executeInsert(SQLQueries.INSERT_USER_TYPES);
			}
			
			if (checkIfExists(SQLQueries.USER_TYPE_TABLE)) {
				if (!checkIfExists(SQLQueries.USERS_TABLE)) {
					executeDDL(SQLQueries.CREATE_USERS_TABLE);
				}
			}
			
			if (!checkIfExists(SQLQueries.CATEGORIES_TABLE)) {
				executeDDL(SQLQueries.CREATE_CATEGORIES_TABLE);
			}
			
			if (checkIfExists(SQLQueries.USERS_TABLE) && checkIfExists(SQLQueries.CATEGORIES_TABLE)) {
				if (!checkIfExists(SQLQueries.PROJECTS_TABLE)) {
					executeDDL(SQLQueries.CREATE_PROJECTS_TABLE);
				}
			}
			
			if (checkIfExists(SQLQueries.USERS_TABLE) && checkIfExists(SQLQueries.PROJECTS_TABLE)) {
				if (!checkIfExists(SQLQueries.EVALUATIONS_TABLE)) {
					executeDDL(SQLQueries.CREATE_EVALUATIONS_TABLE);
				}
			}
			
			if (checkIfExists(SQLQueries.PROJECTS_TABLE)) {
				if (!checkIfExists(SQLQueries.DOCUMENTS_TABLE)) {
					executeDDL(SQLQueries.CREATE_DOCUMENTS_TABLE);
				}
			}
			
			
		} catch (ClassNotFoundException | SQLException | NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				dbConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void executeDDL(String query) throws SQLException {
		statement = dbConn.createStatement();
		statement.executeUpdate(query);
	}
	
	private int executeInsert(String query) throws SQLException {
		statement = dbConn.createStatement();
		return statement.executeUpdate(query);
	}
	
	private boolean checkIfExists(String tableName) {
		try {
			statement = dbConn.createStatement();
			statement.executeQuery("SELECT * FROM " + tableName + " LIMIT 1");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
