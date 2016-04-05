package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.db.TableSetup;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		new TableSetup();
		PrintWriter out = response.getWriter();
		out.println("ok");
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		boolean loginOk;
		try {
			loginOk = UserDB.checkLogin(login, password);
			out.println(loginOk);
		} catch (DatabaseAccessError e) {
			out.println("Sorry: " + e.getMessage());
		}
	}

}
