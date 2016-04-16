package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.db.ProjectDB;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;

/**
 * Servlet implementation class AllProjectsServlet
 */
@WebServlet("/allprojects")
public class AllProjectsServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.checkLoggedIn(request, response);
		
		try {
			request.setAttribute("projects", ProjectDB.getAllProjects());
		} catch (DatabaseAccessError | InvalidDataException e) {
			request.setAttribute("error_message", e.getMessage());
		}
		
		request.getRequestDispatcher("/allprojects.jsp").forward(request, response);
		return;
	}
}
