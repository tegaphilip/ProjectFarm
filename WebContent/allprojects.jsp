<%@page import="model.Evaluation"%>
<%@page import="model.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@page import="java.util.List"%>
<%@ page import="model.Category" %>

<%
	List <Project> projects = (List <Project>) request.getAttribute("projects");
%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>
<script src="<%= request.getContextPath()%>/ext/jquery/datatables.min.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath()%>/ext/bootstrap/3.2.2/css/jquery.dataTables.min.css">
<script type="text/javascript">
$(document).ready(function (){
	$("#datatable").DataTable();
});

</script>
<h4>Projects</h4>
		<div class="panel panel-primary">
		
			<div class="panel-heading">
				All projects
			</div>
			
			<table id="datatable" class="display" style="width: 100%;" border="1">
				<thead>
					<tr>
						<th>Acronym</th>
						<th>Category</th>
						<th># of Incubation Days</th>
						<th>Budget (Euros)</th>
						<th># of Evals</th>
						<th>Action</th>
					</tr>
				</thead>
				
				<tbody>
					<%
						if (projects != null && projects.size() != 0) {
							for (Project p: projects) {
								
								List <Evaluation> evaluations =  p.getEvaluations();
								boolean alreadyEvaluatedByMe = false;
								if (evaluations.size() > 0) {
									for (Evaluation e: evaluations) {
										if (e.getEvaluator().getId() == Integer.valueOf(String.valueOf(session.getAttribute("user_id")))) {
											alreadyEvaluatedByMe = true;
											break;
										}
									}
								}
					%>
					
					<tr>
						<td><a href="<%= request.getContextPath()%>/evaluate?project_id=<%= p.getId() %>"><%= p.getAcronym() %></a></td>
						<td><%= p.getCategory().getDescription() %></td>
						<td><%= p.getFundingDuration() %></td>
						<td><%= p.getBudget() %></td>
						<td><%= p.getEvaluations().size() %></td>
						<td><a href="<%= request.getContextPath()%>/evaluate?project_id=<%= p.getId() %>">
							<% if (alreadyEvaluatedByMe) { out.print("Check Your Evaluation");} else {out.print("Evaluate");} %>
						</a></td>
					</tr>
					<% 	
							}
						}
						else {
					%>
						<tr>
							<td colspan="6">No projects have been added yet</td>
						</tr>
					<% 
						}
					%>
				</tbody>
			</table>
			
			<!-- panel body -->
		</div>
		<!-- panel default -->

<jsp:include page="/utils/footer.jsp" />
    