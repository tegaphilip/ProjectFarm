package model.db;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Owner;
import model.Project;
import model.db.exception.DatabaseAccessError;

public class ProjectDB {

	private static Map<String, Project> projects;

	static {
		projects = new LinkedHashMap<String, Project>();
	}

	public static void saveProject(Project project) throws DatabaseAccessError {
		projects.put(project.getAcronym(), project);
	}

	public static Project getProject(String acronym) throws DatabaseAccessError {
		return projects.get(acronym);
	}

	public static List<Project> getProjectsOfOwner(Owner owner) throws DatabaseAccessError {

		List<Project> projectsOfOwner = new LinkedList<Project>();

		for (Project p : projects.values()) {
			if (p.getOwner().equals(owner)) {
				projectsOfOwner.add(p);
			}
		}
		return projectsOfOwner;

	}
	
	public static List<Project> getAllProjects() throws DatabaseAccessError {
		return new LinkedList<Project>(projects.values());
	}
	
}
