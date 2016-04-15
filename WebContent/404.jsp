<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>

<div class="jumbotron centralize">
	<h1>Looks like you got lost. Don't worry, we got you!</h1>
	<p>
		<a href="<%= request.getContextPath()%>/">
			Go back Home
		</a>
	</p>       
</div>

<jsp:include page="/utils/footer.jsp" />
    