<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>

<div class="jumbotron centralize" align="center">
	<h2>Project Ideas are seeds to Changing the World</h2>
	<p>
		<a href="<%= request.getContextPath()%>/addproject">
			<button type="button" class="btn btn-default">New project idea</button>
		</a>
	</p>       
</div>

<jsp:include page="/utils/footer.jsp" />
    