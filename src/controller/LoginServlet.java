package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.db.TableSetup;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		new TableSetup();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			User user = UserDB.checkLogin(email, password);
			if (user == null) {
				throw new Exception("Invalid login details supplied");
			}
			//set logged in sessions 
			HttpSession session = request.getSession();
			session.setAttribute("name", user.getName());
			session.setAttribute("email", user.getEmail());
			session.setAttribute("user_id", user.getId());
			session.setAttribute("user_type", user.getUserType());
			
			//check user type info
			if (user.getUserType().equalsIgnoreCase(User.USER_TYPE_EVALUATOR)) {
				//send to evaluators page
				response.sendRedirect(request.getContextPath() + "/allprojects");
			} else {
				//send to user's page
				response.sendRedirect(request.getContextPath() + "/home");
			}
			
		} catch (DatabaseAccessError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			request.setAttribute("error_message", e.getMessage());
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
			return;
		}
	}

}
