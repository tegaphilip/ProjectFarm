package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.db.TableSetup;

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
    
    public static void createTables(HttpServletRequest request) {
    	// this will only be done once per session
    	HttpSession session = request.getSession();
		if (session.getAttribute("tables_created") == null) {
			new TableSetup();
			session.setAttribute("tables_created", true);
		}
    }
    
    public void checkLoggedIn (HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();
    	if (session.getAttribute("name") == null) {
    		try {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				return;
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
