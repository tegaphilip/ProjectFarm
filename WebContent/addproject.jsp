<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="model.Category" %>

<%
	HashMap <Integer, Category> map = (HashMap <Integer, Category>) request.getAttribute("categories");
	HashMap<String, String> projectParams = new HashMap<>();
    String acronym = "", description = "", category_id = "", funding_duration_days = "", budget = ""; 
	if (request.getAttribute("project_params") != null) {
		projectParams = (HashMap<String, String>) request.getAttribute("project_params");
		acronym = projectParams.get("acronym");
		description = projectParams.get("description");
		category_id = projectParams.get("category_id");
		funding_duration_days = projectParams.get("funding_duration_days");
		budget = projectParams.get("budget");
	}
%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>

<h4>Projects</h4>
		<div class="panel panel-primary">
		
			<div class="panel-heading">
				New Project Idea
			</div>
			
			<div class="panel-body">
				<form method="post" role="form" action="<%= request.getContextPath()%>/addproject">
					<div>
						<% 
							Object error = request.getAttribute("error_message");
							Object success = request.getAttribute("created");
							if (success != null) {
								out.print("<td colspan='6'><span class='alert-success'>Your project has been added</span></td>");
							} else if (error != null) {
								out.print("<p class='alert-danger'>" + error + "</p>");
							}
						%>
					</div>
					
					<div class="form-group">
						<label for="acronym">Title:</label> 
						<input type="text" class="form-control" name="acronym" required="required" value="<%= acronym %>">
					</div>
					
					<div class="form-group">
						<label for="description">Description:</label>
						<textarea class="form-control" rows="" cols="" name="description" required="required"><%= description %></textarea>
					</div>
					
					<div class="form-group">
						<label for="category_id">Category:</label>
						<select class="form-control" name="category_id" required="required">
							<option value="">Select</option>
							<%
								if (map != null) {
									for (Integer i: map.keySet()) {
									%>
									<option value="<%= i %>" <% if (String.valueOf(i).equalsIgnoreCase(category_id)) { out.print("selected = 'selected'"); } %>>
										<% out.print(map.get(i).getDescription()); %>
									</option>
									<%
									}
								}
							%>
						</select>
					</div>
					
					<div class="form-group">
						<label for="funding_duration_days">Incubation (# of days):</label> 
						<input type="number" class="form-control" name="funding_duration_days" required="required" value = "<%= funding_duration_days %>">
					</div>
					
					<div class="form-group">
						<label for="budget">Budget (Euro):</label> 
						<input type="number" class="form-control" name="budget" required="required" value="<%= budget %>">
					</div>
					
					<button type="submit" class="btn btn-default" name="AddProject">Submit</button>
				</form>
			</div>
			
			<!-- panel body -->
		</div>
		<!-- panel default -->

<jsp:include page="/utils/footer.jsp" />
    