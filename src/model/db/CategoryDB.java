package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import model.Category;
import model.db.exception.DatabaseAccessError;

public class CategoryDB {
	
	public static List<Category> getCategories() throws DatabaseAccessError {
		return new LinkedList<Category>(getCategoriesFromDB().values());
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
