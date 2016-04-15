package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import model.Project;
import model.db.BaseDB;
import model.db.ProjectDB;
import model.db.QueryHelper;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;
import util.FileUtil;

/**
 * Servlet implementation class ShowProjectServlet
 */
@WebServlet("/project")
public class ShowProjectServlet extends BaseServlet {
	
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
			    Part filePart = request.getPart("doc");
			    InputStream fileContent = filePart.getInputStream();
			    String submittedName = filePart.getSubmittedFileName();
			    long size = filePart.getSize();
			    System.out.println(size);
			    if (!FileUtil.isFileAllowed(submittedName)) {
			    	request.setAttribute("error_message", "The file type uploaded is not supported<br>"
			    			+ "You can only upload pdf, doc, docx, txt, xls, xlsx, jpg, png, and gif");
			    } else if (size > FileUtil.MAX_FILE_SIZE_ALLOWED)  {
			    	request.setAttribute("error_message", "The maximum file size allowed is 1MB");
				} else {
			    	File f = new File(System.currentTimeMillis() + "-" + filePart.getSubmittedFileName());
				    if (!f.exists()) {
				    	f.createNewFile();
				    }
				    
				    OutputStream out = new FileOutputStream(f);
				    IOUtils.copy(fileContent, out);
				    fileContent.close();
				    out.close();
				    
				    boolean result = QueryHelper.addDocument(f.getAbsolutePath(), projectId);
				    if (!result) {
				    	request.setAttribute("error_message", "An error occured >> " + BaseDB.getLastErrorMessage());
				    }
			    }
			}
			
			Project project = ProjectDB.findProjectById(projectId);
			if (project == null || !String.valueOf(project.getOwner().getId()).equalsIgnoreCase(String.valueOf(session.getAttribute("user_id")))) {
				throw new Exception("An error occurred");
			}
			request.setAttribute("project", project);
			request.getRequestDispatcher("/project.jsp").forward(request, response);
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
