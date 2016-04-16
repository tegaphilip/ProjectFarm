package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import model.Owner;
import model.Project;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;
import util.DateUtil;

public class ProjectDB extends BaseDB {

	public static void saveProject(Project project) throws DatabaseAccessError {
		HashMap<String, String> projectParams = new HashMap<>();
		projectParams.put("acronym", project.getAcronym());
		projectParams.put("description", project.getDescription());
		projectParams.put("funding_duration_days", project.getFundingDuration() + "");
		projectParams.put("budget", project.getBudget() + "");
		projectParams.put("owner_id", project.getOwner().getId() + "'");
		projectParams.put("category_id", project.getCategory().getId() + "'");
		QueryHelper.createProject(projectParams);
	}

	public static Project getProject(String acronym) throws DatabaseAccessError, InvalidDataException {
		return ProjectDB.findProjectByAcronym(acronym);
	}

	public static List<Project> getProjectsOfOwner(Owner owner) throws DatabaseAccessError, InvalidDataException {
		List<Project> projectsOfOwner = new LinkedList<Project>();
		
		try {
			ResultSet result = QueryHelper.getOwnerProjects(owner.getId());
			if (result != null) {
				while (result.next()) {
					Project p = getProjectFromResultSet(result);
					projectsOfOwner.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projectsOfOwner;
	}
	
	public static Project getProjectFromResultSet(ResultSet result) throws InvalidDataException, SQLException, DatabaseAccessError {
		Project p = new Project(
				result.getString("acronym"), 
				result.getString("description"), 
				result.getInt("funding_duration_days"), 
				result.getDouble("budget"), 
				UserDB.getOwnerById(result.getInt("owner_id")), 
				CategoryDB.getCategorybyId(result.getInt("category_id")));
		
		p.setId(result.getInt("id"));
		p.setEvaluations(EvaluationDB.getProjectsEvaluations(p));
		p.setDocuments(DocumentDB.getProjectDocuments(p));
		p.setCreated(DateUtil.stringToDate(result.getString("created_at")));
		
		return p;
	}
	
	public static List<Project> getAllProjects() throws DatabaseAccessError, InvalidDataException {
		List<Project> projects = new LinkedList<Project>();
		
		try {
			ResultSet result = QueryHelper.getAllProjects();
			if (result != null) {
				while (result.next()) {
					Project p = getProjectFromResultSet(result);
					projects.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}
	
	public static Project findProjectById(int projectId) throws InvalidDataException, DatabaseAccessError {
		ResultSet result = QueryHelper.findProjectById(projectId);
		try {
			if (result != null && result.next()) {
				return getProjectFromResultSet(result);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Project findProjectByAcronym(String acronym) throws InvalidDataException, DatabaseAccessError {
		ResultSet result = QueryHelper.findProjectByAcronym(acronym);
		try {
			if (result != null && result.next()) {
				return ProjectDB.findProjectById(result.getInt("id"));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
