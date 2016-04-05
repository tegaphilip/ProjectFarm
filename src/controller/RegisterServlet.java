package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.db.UserDB;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/register")
public class RegisterServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// this will only be done once per session
		BaseServlet.createTables(request);
		HttpSession session = request.getSession();
		if (session.getAttribute("name") != null) {
			//user already logged in
			response.sendRedirect(request.getContextPath() + "/myprojects");
		}
		
		String register = request.getParameter("Register");
		if (register == null) {
			// retrieve user types needed for registration
			request.setAttribute("user_types", UserDB.getUserTypes());
			request.getRequestDispatcher("/signup.jsp").forward(request, response);
			return;
		}
		
		//if register was clicked, proceed to register student
		HashMap<String, String> userParams = new HashMap<>();
		userParams.put("email", request.getParameter("email"));
		userParams.put("password", request.getParameter("password"));
		userParams.put("user_type", request.getParameter("user_type"));
		userParams.put("name", request.getParameter("name"));
		
		try {
			//check if email has already been used
			User u = UserDB.findUser(request.getParameter("email"));
			if (u != null) {
				//skip to the exception block
				throw new Exception ("A user with that email already exists");
			}
			
			if (UserDB.createUser(userParams)) {
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
