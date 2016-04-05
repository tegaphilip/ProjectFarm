package model.db;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Category;
import model.db.exception.DatabaseAccessError;

public class CategoryDB {
	
	private static Map<String,Category> categories;
	
	static {
		categories = new LinkedHashMap<>();
		initializeCategoryList();
	}
	
	public static List<Category> getCategories() throws DatabaseAccessError {
		return new LinkedList<Category>(categories.values());
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

}
