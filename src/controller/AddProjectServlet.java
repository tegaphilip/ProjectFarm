package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Project;
import model.db.CategoryDB;
import model.db.ProjectDB;
import model.db.QueryHelper;

/**
 * Servlet implementation class AddProjectServlet
 */
@WebServlet("/addproject")
public class AddProjectServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		this.checkLoggedIn(request, response);
		request.setAttribute("categories", CategoryDB.getCategoriesFromDB());
		String add = request.getParameter("AddProject");
		if (add == null) {
			// retrieve category types
			request.getRequestDispatcher("/addproject.jsp").forward(request, response);
			return;
		}
		
		//Add project
		HashMap<String, String> projectParams = new HashMap<>();
		projectParams.put("acronym", request.getParameter("acronym"));
		projectParams.put("description", request.getParameter("description"));
		projectParams.put("funding_duration_days", request.getParameter("funding_duration_days"));
		projectParams.put("budget", request.getParameter("budget"));
		projectParams.put("owner_id", String.valueOf(session.getAttribute("user_id")));
		projectParams.put("category_id", request.getParameter("category_id"));
		request.setAttribute("project_params", projectParams);
		
		String errorMessage = "";
		try {
			Double.parseDouble(request.getParameter("budget"));
		} catch (NumberFormatException e) {
			errorMessage += "Invalid budget:" + e.getMessage();
		}
		
		try {
			Integer.parseInt(request.getParameter("funding_duration_days"));
		} catch (NumberFormatException e) {
			errorMessage += "Invalid funding days:" + e.getMessage();
		}
		
		try {
			if (!errorMessage.isEmpty()) {
				throw new Exception(errorMessage);
			}
			
			//check if this project idea has already been entered before 
			Project p = ProjectDB.findProjectByAcronym(request.getParameter("acronym"));
			if (p != null) {
				//skip to the exception block
				throw new Exception ("A project idea with this name has been previously created");
			}
			
			if (QueryHelper.createProject(projectParams)) {
				request.setAttribute("created", true);
			} else {
				request.setAttribute("error_message", ProjectDB.getLastErrorMessage());
			}
		} catch (Exception e) {
			request.setAttribute("error_message", e.getMessage());
		}
		
		request.getRequestDispatcher("/addproject.jsp").forward(request, response);
		return;
	}
}
