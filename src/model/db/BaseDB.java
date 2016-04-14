package model.db;

public class BaseDB {
	static String lastErrorMessage = "";
	
	public static String getLastErrorMessage() {
		return BaseDB.lastErrorMessage;
	}
}
