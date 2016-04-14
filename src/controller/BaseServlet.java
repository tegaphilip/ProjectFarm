package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BaseServlet
 */
@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseServlet() {
        super();
    }
    
    public void checkLoggedIn (HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();
    	if (session.getAttribute("name") == null) {
    		try {
    			request.setAttribute("error_message", "You are not logged in");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				return;
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
