package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Category;
import model.Project;
import model.User;
import model.db.CategoryDB;
import model.db.ProjectDB;
import model.db.UserDB;

/**
 * Servlet implementation class LoginServlet
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
		String add = request.getParameter("AddProject");
		if (add == null) {
			// retrieve category types
			HashMap<Integer,Category> cats = CategoryDB.getCategoriesFromDB();
			System.out.println(cats);
			request.setAttribute("categories", CategoryDB.getCategoriesFromDB());
			request.getRequestDispatcher("/addproject.jsp").forward(request, response);
			return;
		}
		
		//Add project
		HashMap<String, String> projectParams = new HashMap<>();
		projectParams.put("acronym", request.getParameter("acronym"));
		projectParams.put("description", request.getParameter("password"));
		projectParams.put("funding_duration_days", request.getParameter("funding_duration_days"));
		projectParams.put("budget", request.getParameter("budget"));
		projectParams.put("owner_id", String.valueOf(session.getAttribute("user_id")));
		projectParams.put("category_id", request.getParameter("category_id"));
		
		try {
			Double.parseDouble(request.getParameter("budget"));
			Integer.parseInt(request.getParameter("funding_duration_days"));
			
			//check if this project idea has already been entered before 
			Project p = ProjectDB.findProjectByAcronym(request.getParameter("acronym"));
			if (p != null) {
				//skip to the exception block
				throw new Exception ("This project idea has been previously created");
			}
			
			if (UserDB.createUser(projectParams)) {
				request.setAttribute("created", true);
			} else {
				request.setAttribute("error_message", UserDB.getLastErrorMessage());
			}
		} catch (Exception e) {
			request.setAttribute("error_message", e.getMessage());
		}
		
		request.getRequestDispatcher("/signup.jsp").forward(request, response);
		return;
	}
}
