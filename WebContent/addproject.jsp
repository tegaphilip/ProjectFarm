<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="model.Category" %>

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
							if (error != null) {
								out.print("<p class='alert-danger'>" + error + "</p>");
							}
						%>
					</div>
					
					<div class="form-group">
						<label for="acronym">Title:</label> 
						<input type="text" class="form-control" name="acronym" required="required">
					</div>
					
					<div class="form-group">
						<label for="description">Description:</label>
						<textarea class="form-control" rows="" cols="" name="description" required="required"></textarea>
					</div>
					
					<div class="form-group">
						<label for="category_id">Category:</label>
						<select class="form-control" name="category_id" required="required">
							<option value="">Select</option>
							<%
								HashMap <Integer, Category> map = (HashMap <Integer, Category>) request.getAttribute("categories");
								if (map != null) {
									for (Integer i: map.keySet()) {
									%>
									<option value="<%= i %>"><%= map.get(i).getDescription() %></option>
									<%
									}
								}
							%>
						</select>
					</div>
					
					<div class="form-group">
						<label for="funding_duration_days">Incubation (# of days):</label> 
						<input type="text" class="form-control" name="funding_duration_days" required="required">
					</div>
					
					<div class="form-group">
						<label for="budget">Budget (€):</label> 
						<input type="text" class="form-control" name="budget" required="required">
					</div>
					
					<button type="submit" class="btn btn-default" name="AddProject">Submit</button>
				</form>
			</div>
			
			<!-- panel body -->
		</div>
		<!-- panel default -->

<jsp:include page="/utils/footer.jsp" />
    