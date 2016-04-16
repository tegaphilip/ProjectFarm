<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><%= request.getParameter("title") %></title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%= request.getContextPath()%>/ext/bootstrap/3.2.2/css/bootstrap.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/ext/bootstrap/3.2.2/css/additions.css">
<script src="<%= request.getContextPath()%>/ext/jquery/1.11.2/jquery-1.11.2.js"></script>
<script src="<%= request.getContextPath()%>/ext/bootstrap/3.2.2/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="<%= request.getContextPath()%>/index.jsp"><%= request.getParameter("title") %></a>
    </div>
	<div>
      <ul class="nav navbar-nav">
        
      </ul>
    </div>    
    <div>
      	<% if(session.getAttribute("name") == null) { %>
		<form method="post" class="navbar-form form-inline pull-right"  action="<%= request.getContextPath()%>/login">
    		<input type="email" placeholder="Email" name="email">
    		<input type="password" placeholder="Password" name="password">
    		<input type="hidden" name="pageSuccess"  value='<%= request.getParameter("page")%>'/>
    		<button type="submit" class="btn btn-default">Sign in</button>
		</form>
		<% } else { 
			String type = String.valueOf(session.getAttribute("user_type"));
			String url = "/myprojects", label = "My projects";
			if (type.equalsIgnoreCase(User.USER_TYPE_EVALUATOR)) {
				url = "/allprojects";
				label = "List Projects";
			} else {
				response.sendRedirect(request.getContextPath() + "/home");
			}
		%>
		<ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">Welcome <%= session.getAttribute("name")%>
          <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="<%= request.getContextPath() + url%>"><%= label %></a></li>
            <li><a href="<%= request.getContextPath()%>/logout">Sign Out</a></li>            
          </ul>
        </li>
        </ul>			
		<% } %>
	</div>	    
  </div>
</nav>

<div class="container">

<!-- container, body and HTML tags are still opened -->