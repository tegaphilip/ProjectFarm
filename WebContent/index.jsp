<%@page import="model.User"%>
<%
	if (session.getAttribute("user_id") != null && session.getAttribute("user_type") != null) {
		String type = String.valueOf(session.getAttribute("user_type"));
		if (type.equalsIgnoreCase(User.USER_TYPE_EVALUATOR)) {
			response.sendRedirect(request.getContextPath() + "/allprojects");
		} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}
	}
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>

<div class="jumbotron centralize">
	<h1>Welcome to ProjectFarm</h1>
	<p>
		<a href="<%= request.getContextPath()%>/register">
			<button type="submit" class="btn btn-default">Sign Up</button>
		</a>
	</p>       
</div>

<jsp:include page="/utils/footer.jsp" />
    