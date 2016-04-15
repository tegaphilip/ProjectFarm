package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class FileUtil {
	
	final static String USER_HOME = System.getProperty("user.home");
	final static String PROJECT_FARM_DIR = USER_HOME + File.separator + ".pfarm";
	final static String FILE_NAME = PROJECT_FARM_DIR + File.separator + "db_created.dat";
	public final static long MAX_FILE_SIZE_ALLOWED = 1 * 1024 * 1024;
	
	final static String[] ALLOWED_EXTENSIONS = { "doc", "docx", "xls", "xlsx", "pdf", "txt", "jpg", "jpeg", "png", "gif" }; 
	
	public static void createFile() {
		try {
			File f = new File(PROJECT_FARM_DIR);
			if (!f.isDirectory()) {
				f.mkdir();
			}
			
			f = new File(FILE_NAME);
			f.createNewFile();
			PrintWriter writer = new PrintWriter(f);
			writer.write("Database has been created");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkIfFileExists()
	{
		File f = new File(FILE_NAME);
		return f.exists() && !f.isDirectory();
	}
	
	public static boolean isFileAllowed(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
		for (String ext: ALLOWED_EXTENSIONS) {
			if (extension.toLowerCase().equalsIgnoreCase(ext)) {
				return true;
			}
		}
		
		return false;
	}
}
