package model.db;

public class SQLQueries 
{
	static String AUTO_INCREMENT = "";
	
	static {
		String dataSourceType = DBUtil.dataSourceType;
		AUTO_INCREMENT = dataSourceType.equalsIgnoreCase(DBUtil.SQLITE) ?
				"AUTOINCREMENT" : "AUTO_INCREMENT";
	}
	
	//Tables
	static final String USER_TYPE_TABLE = "user_types";
	static final String USERS_TABLE = "users";
	static final String PROJECTS_TABLE = "projects";
	static final String CATEGORIES_TABLE = "categories";
	static final String EVALUATIONS_TABLE = "evaluations";
	static final String DOCUMENTS_TABLE = "documents";
	
	
	//DDLs
	static final String CREATE_USER_TYPES_TABLE = "CREATE TABLE IF NOT EXISTS user_types("
			+ "id INTEGER PRIMARY KEY " + AUTO_INCREMENT + ","
			+ "name VARCHAR(20))";
	
	static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users("
			+ "id INTEGER PRIMARY KEY " + AUTO_INCREMENT + ","
			+ "name VARCHAR(100) NOT NULL,"
			+ "email VARCHAR(100) UNIQUE,"
			+ "password VARCHAR(100) NOT NULL,"
			+ "user_type INT(11) NOT NULL,"
			+ "created_at datetime NULL,"
			+ "updated_at datetime NULL,"
			+ "FOREIGN KEY(user_type) REFERENCES user_types(id) ON UPDATE CASCADE)";
	
	static final String CREATE_CATEGORIES_TABLE  = "CREATE TABLE IF NOT EXISTS categories("
			+ "id INTEGER PRIMARY KEY " + AUTO_INCREMENT + ","
			+ "description TEXT NOT NULL)";
	
	static final String CREATE_PROJECTS_TABLE = "CREATE TABLE IF NOT EXISTS projects("
			+ "id INTEGER PRIMARY KEY " + AUTO_INCREMENT + ","
			+ "acronym VARCHAR(100) UNIQUE,"
			+ "description TEXT NOT NULL,"
			+ "funding_duration_days INT NOT NULL,"
			+ "budget REAL NOT NULL,"
			+ "created_at datetime NULL,"
			+ "owner_id INT(11) NOT NULL,"
			+ "category_id INT(11) NOT NULL,"
			+ "updated_at datetime NULL,"
			+ "FOREIGN KEY(category_id) REFERENCES categories(id) ON UPDATE CASCADE,"
			+ "FOREIGN KEY(owner_id) REFERENCES users(id) ON UPDATE CASCADE)";
	
	static final String CREATE_EVALUATIONS_TABLE = "CREATE TABLE IF NOT EXISTS evaluations("
			+ "id INTEGER PRIMARY KEY " + AUTO_INCREMENT + ","
			+ "attractiveness INT NOT NULL,"
			+ "risk_level INT NOT NULL,"
			+ "evaluator_id INT(11) NOT NULL,"
			+ "project_id INT(11) NOT NULL,"
			+ "created_at datetime NOT NULL,"
			+ "FOREIGN KEY(evaluator_id) REFERENCES users(id) ON UPDATE CASCADE,"
			+ "FOREIGN KEY(project_id) REFERENCES projects(id) ON UPDATE CASCADE)";
	
	static final String CREATE_DOCUMENTS_TABLE = "CREATE TABLE IF NOT EXISTS documents("
			+ "id INTEGER PRIMARY KEY " + AUTO_INCREMENT + ","
			+ "document_path VARCHAR(100) NOT NULL,"
			+ "project_id INT(11) NOT NULL,"
			+ "created_at datetime NOT NULL,"
			+ "FOREIGN KEY(project_id) REFERENCES projects(id) ON UPDATE CASCADE)";
	
	static final String INSERT_USER_TYPES = "INSERT INTO user_types (name) VALUES "
			+ "('owner'), ('evaluator')";
	
	static final String INSERT_CATEGORIES = "INSERT INTO categories (description) VALUES "
			+ "('entertainment'), ('education'), ('productivity'), ('travels & tourism'), ('others')";
	
	static final String FIND_USER_BY_EMAIL = "SELECT * FROM " + USERS_TABLE + " "
			+ "WHERE email = ?";
	
	static final String FIND_USER_BY_ID = "SELECT * FROM " + USERS_TABLE + " "
			+ "WHERE id = ?";
	
	static final String FIND_ALL_USER_TYPES = "SELECT * FROM " + USER_TYPE_TABLE;
	
	static final String CREATE_USER = "INSERT INTO users (name, email, password, user_type, created_at, updated_at)"
			+ "VALUES (?, ?, ?, ? , ?, ?)";
	
	static final String FIND_ALL_CATEGORIES = "SELECT * FROM " + CATEGORIES_TABLE + " ORDER BY description ASC";
	
	static final String FIND_PROJECT_BY_ID = "SELECT * FROM " + PROJECTS_TABLE + " "
			+ "WHERE id = ?";
	
	static final String FIND_PROJECT_BY_TITLE = "SELECT * FROM " + PROJECTS_TABLE + " "
			+ "WHERE acronym = ?";
	
	static final String FIND_CATEGORY_BY_ID = "SELECT * FROM " + CATEGORIES_TABLE + " "
			+ "WHERE id = ?";
	
	static final String ADD_PROJECT = "INSERT INTO projects (acronym, description, funding_duration_days, budget, owner_id, category_id, created_at, updated_at)"
			+ "VALUES (?, ?, ?, ? , ?, ?, ?, ?)";
	
	static final String GET_USER_PROJECTS = "SELECT * FROM projects WHERE owner_id = ?";
	
	static final String GET_PROJECT_EVALUATIONS = "SELECT * FROM evaluations WHERE project_id = ?";
	
	static final String GET_PROJECT_DOCUMENTS = "SELECT * FROM documents WHERE project_id = ?";
	
	static final String ADD_DOCUMENT = "INSERT INTO documents (document_path, project_id, created_at)"
			+ "VALUES (?, ?, ?)";
	
}
