<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>

		<div class="panel panel-primary">
			<div class="panel-heading">
				Login
			</div>
			<div class="panel-body">
				<form method="post" role="form" action="<%= request.getContextPath()%>/login">
					<div>
						<% 
							Object error = request.getAttribute("error_message");
							if (error != null) {
								out.print("<p class='alert-danger'>" + error + "</p>");
							}
						%>
					</div>
					
					<div class="form-group">
						<label for="email">Email:</label> 
						<input type="email" class="form-control" name="email" required="required">
					</div>
					<div class="form-group">
						<label for="password">Password:</label> 
						<input type="password" class="form-control" name="password" required="required">
					</div>
					
					
					<button type="submit" class="btn btn-default" name="SignIn">SignIn</button>
					<a href="<%= request.getContextPath()%>/register" class = "pull-right">
					<button type="button" class="btn btn-default" name="Register">Sign Up</button>
					</a>
				</form>
			</div>
			
			<!-- panel body -->
		</div>
		<!-- panel default -->

<jsp:include page="/utils/footer.jsp" />
    