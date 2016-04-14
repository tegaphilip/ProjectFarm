package model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Owner;
import model.Project;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;

public class ProjectDB extends BaseDB{

	private static Map<String, Project> projects;

	static {
		projects = new LinkedHashMap<String, Project>();
	}

	public static void saveProject(Project project) throws DatabaseAccessError {
		projects.put(project.getAcronym(), project);
	}

	public static Project getProject(String acronym) throws DatabaseAccessError, InvalidDataException {
		return ProjectDB.findProjectByAcronym(acronym);
	}

	public static List<Project> getProjectsOfOwner(Owner owner) throws DatabaseAccessError, InvalidDataException {
		List<Project> projectsOfOwner = new LinkedList<Project>();
		
		try {
			ResultSet result = QueryHelper.getOwnerProjects(owner.getId());
			System.out.println(result);
			if (result != null) {
				while (result.next()) {
					Project p = new Project(
							result.getString("acronym"), 
							result.getString("description"), 
							result.getInt("funding_duration_days"), 
							result.getDouble("budget"), 
							owner, 
							CategoryDB.getCategorybyId(result.getInt("category_id")));
					
					p.setId(result.getInt("id"));
					p.setEvaluations(EvaluationDB.getProjectsEvaluations(p));
					projectsOfOwner.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(projectsOfOwner.size());
		return projectsOfOwner;

	}
	
	public static List<Project> getAllProjects() throws DatabaseAccessError {
		return new LinkedList<Project>(projects.values());
	}
	
	public static Project findProjectById(int projectId) throws InvalidDataException {
		ResultSet result = QueryHelper.findProjectById(projectId);
		try {
			if (result != null && result.next()) {
				return new Project(result.getString("acronym"), 
						result.getString("description"), 
						result.getInt("funding_duration_days"),
						result.getDouble("budget"),
						UserDB.getOwnerById(result.getInt("owner_id")),
						CategoryDB.getCategorybyId(result.getInt("category_id")));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Project findProjectByAcronym(String acronym) throws InvalidDataException {
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
