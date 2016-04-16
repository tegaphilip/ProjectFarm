package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Project;
import model.db.BaseDB;
import model.db.ProjectDB;
import model.db.QueryHelper;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;

/**
 * Servlet implementation class EvaluateProjectServlet
 */
@WebServlet("/evaluate")
public class EvaluateProjectServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		this.checkLoggedIn(request, response);
		
		try {
			int projectId = Integer.parseInt(request.getParameter("project_id"));
			String hidden = request.getParameter("hidden");
			
			if (hidden != null) {
			    int riskLevel = Integer.parseInt(request.getParameter("risk_level"));
			    int attractiveness = Integer.parseInt(request.getParameter("attractiveness"));
			    int evaluatorId = Integer.parseInt(String.valueOf(session.getAttribute("user_id")));
			    
			    boolean result = QueryHelper.addEvaluation(projectId, evaluatorId, riskLevel, attractiveness);
			    if (!result) {
			    	request.setAttribute("error_message", "An error occured >> " + BaseDB.getLastErrorMessage());
			    } else {
			    	request.setAttribute("created", true);
			    }
			}
			
			Project project = ProjectDB.findProjectById(projectId);
			if (project == null) {
				throw new Exception("An error occurred");
			}
			request.setAttribute("project", project);
			request.getRequestDispatcher("/evaluation.jsp").forward(request, response);
			return;
		} catch (NumberFormatException | InvalidDataException | DatabaseAccessError e) {
			e.printStackTrace();
			request.getRequestDispatcher("/404.jsp").forward(request, response);
			return; 
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/404.jsp").forward(request, response);
			return; 
		}
	}
}
