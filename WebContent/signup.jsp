<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>

<h4>Registration</h4>
		<div class="panel panel-primary">
			<div class="panel-heading">
				Register
			</div>
			<div class="panel-body">
				<form method="post" role="form" action="<%= request.getContextPath()%>/register">
					<div>
						<% 
							Object error = request.getAttribute("error_message");
							Object success = request.getAttribute("created");
							if (success != null) {
								out.print("<p class='alert-success'>User has successfully registered. <a href = '"+ request.getContextPath() +"'>Click here to login</a></p>");
							} else if (error != null) {
								out.print("<p class='alert-danger'>" + error + "</p>");
							}
						%>
					</div>
					<div class="form-group">
						<label for="name">Name:</label> 
						<input type="text" class="form-control" name="name" required="required">
					</div>
					
					<div class="form-group">
						<label for="email">Email:</label> 
						<input type="email" class="form-control" name="email" required="required">
					</div>
					<div class="form-group">
						<label for="password">Password:</label> 
						<input type="password" class="form-control" name="password" required="required">
					</div>
					<div class="form-group">
						<label for="user_type">User Type:</label>
						<select class="form-control" name="user_type" required="required">
							<option value="">Select</option>
							<%
								HashMap <Integer, String> map = (HashMap <Integer, String>) request.getAttribute("user_types");
								if (map != null) {
									for (Integer i: map.keySet()) {
									%>
									<option value="<%= i %>"><%= map.get(i) %></option>
									<%
									}
								}
							%>
						</select>
					</div>
					
					<button type="submit" class="btn btn-default" name="Register">Submit</button>
				</form>
			</div>
			
			<!-- panel body -->
		</div>
		<!-- panel default -->

<jsp:include page="/utils/footer.jsp" />
    