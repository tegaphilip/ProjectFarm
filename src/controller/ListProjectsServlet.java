package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Owner;
import model.db.ProjectDB;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/myprojects")
public class ListProjectsServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		this.checkLoggedIn(request, response);
		
		try {
			Owner owner = UserDB.getOwnerById(Integer.valueOf( "" + session.getAttribute("user_id")));
			request.setAttribute("projects", ProjectDB.getProjectsOfOwner(owner));
		} catch (NumberFormatException | SQLException | DatabaseAccessError | InvalidDataException e) {
			request.setAttribute("error_message", e.getMessage());
		}
		
		request.getRequestDispatcher("/listprojects.jsp").forward(request, response);
		return;
	}
}
