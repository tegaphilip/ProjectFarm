package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Evaluator;
import model.Owner;
import model.User;
import model.db.exception.DatabaseAccessError;
import util.Password;

public class UserDB {

	static final String USER_TYPE_OWNER = "owner";
	static final String USER_TYPE_EVALUATOR = "evaluator";
	
	static String lastErrorMessage = "";
	
	private static Map<String, User> users;

	static {
		users = new LinkedHashMap<String, User>();
		initializeUsersList();
	}

	private static void initializeUsersList() {
		users.put("john@acme.com", new Owner("john@acme.com", "John Silver", "123"));
		users.put("mary@acme.com", new Owner("mary@acme.com", "Mary Moon", "123"));
		users.put("paul@acme.com", new Owner("paul@acme.com", "Paul McDonalds", "123"));

		users.put("sarah@geek.com", new Evaluator("sarah@geek.com", "Sarah Logan", "456"));
		users.put("thibault@geek.com", new Evaluator("thibault@geek.com", "Thibault Moulin", "456"));
		users.put("george@geek.com", new Evaluator("george@geek.com", "George Papalodeminus", "456"));
	}

	public static User checkLogin(String login, String password) throws DatabaseAccessError{
		User u = UserDB.findUser(login);
		if (u == null) {
			return null;
		}
		
		try {
			 if (Password.check(password, u.getPassword())) {
				 return u;
			 }
			 return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static User getUser(String login) throws DatabaseAccessError {
		User u = getOwner(login);
		if (u == null) {
			u = getEvaluator(login);
		}
		return u;
	}

	public static Owner getOwner(String login) throws DatabaseAccessError {
		User u = UserDB.findUser(login);
		if (u == null || !(u instanceof Owner))
			return null;
		return (Owner) u;
	}

	public static Evaluator getEvaluator(String login) throws DatabaseAccessError {
		User u = UserDB.findUser(login);
		if (u == null || !(u instanceof Evaluator))
			return null;
		return (Evaluator) u;
	}
	
	
	public static User findUser(String login) {
		HashMap< Integer, String> userTypes = getUserTypes();
		ResultSet result = QueryHelper.findUserByEmail(login);
		try {
			if (result != null && result.next()) {
				int type = result.getInt("user_type");
				String name = result.getString("name");
				String email = result.getString("email");
				String password = result.getString("password");
				
				if (userTypes.get(type).equalsIgnoreCase(USER_TYPE_OWNER)) {
					return new Owner(email, name, password);
				} else if (userTypes.get(type).equalsIgnoreCase(USER_TYPE_OWNER)) {
					return new Evaluator(email, name, password);
				}
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean createUser(HashMap<String, String> userParams) {
		return QueryHelper.createUser(userParams);
	}
	
	public static HashMap <Integer, String> getUserTypes() {
		HashMap< Integer, String> map = new HashMap<>();
		ResultSet result = QueryHelper.findUserTypes();
		try {
			if (result != null) {
				while (result.next()) {
					map.put(result.getInt("id"), result.getString("name"));
				}
			}
		} catch (SQLException e) {
			
		}
		return map;
	}
	
	public static String getLastErrorMessage() {
		return UserDB.lastErrorMessage;
	}
}
