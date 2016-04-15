package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Document;
import model.Project;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;

public class DocumentDB extends BaseDB{

	public static List<Document> getProjectDocuments(Project project) throws DatabaseAccessError, InvalidDataException {
		List<Document> documents = new LinkedList<Document>();
		
		try {
			ResultSet result = QueryHelper.getProjectDocuments(project.getId());
			
			if (result != null) {
				while (result.next()) {
					Document e = new Document(result.getString("document_path"));
					documents.add(e);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return documents;

	}
}
