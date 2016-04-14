package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Category;
import model.Owner;
import model.User;
import model.db.exception.DatabaseAccessError;

public class CategoryDB {
	
	private static Map<String,Category> categories;
	
	static {
		categories = new LinkedHashMap<>();
		initializeCategoryList();
	}
	
	public static List<Category> getCategories() throws DatabaseAccessError {
		return new LinkedList<Category>(getCategoriesFromDB().values());
	}
	
	public Category getCategory(String name) {
		return categories.get(name);
	}

	private static void initializeCategoryList() {
		categories.put("Apps",new Category("Apps"));
		categories.put("Robotics",new Category("Robotics"));
		categories.put("Information Systems",new Category("Information Systems"));
		categories.put("Hardware",new Category("Hardware"));
	}
	
	public static HashMap<Integer,Category> getCategoriesFromDB() {
		
		HashMap< Integer, Category> map = new HashMap<>();
		ResultSet result = QueryHelper.getCategories();
		try {
			if (result != null) {
				while (result.next()) {
					map.put(result.getInt("id"), 
							new Category(result.getString("description"), result.getInt("id")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static Category getCategorybyId(int id) throws SQLException {
		ResultSet result = QueryHelper.findCategoryById(id);
		if (result != null) {
			return new Category(result.getString("description"), result.getInt("id"));
		}
		
		return null;
	}

}
