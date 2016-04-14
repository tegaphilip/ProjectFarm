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
<span class="pull-right">
	<a href="<%= request.getContextPath()%>/addproject">New project idea</a>
</span>
<h4>Projects</h4>
		<div class="panel panel-primary">
		
			<div class="panel-heading">
				My projects
			</div>
			
			<table style="width: 100%;" border="1">
				<thead>
					<tr>
						<th>Acronym</th>
						<th>Category</th>
						<th># of Incubation Days</th>
						<th>Budget (Euros)</th>
						<th>Risk Level</th>
						<th>Attractiveness</th>
						<th>Number of Evaluators</th>
					</tr>
				</thead>
				
				<tbody>
					<%
						if (projects != null && projects.size() != 0) {
							for (Project p: projects) {
								String averageRiskLevel = "", averageAttractiveness = "", riskClass = "", attractivenessClass = ""; 
								
								if (p.getEvaluations().size() > 0) {
									double sumRiskLevel = 0, sumAttractiveness = 0;
									for (Evaluation e: p.getEvaluations()) {
										sumRiskLevel += e.getRiskLevel();
										sumAttractiveness += e.getAttractiveness();
									}
									
									averageRiskLevel = String.valueOf(sumRiskLevel / p.getEvaluations().size());
									averageAttractiveness = String.valueOf(sumAttractiveness / p.getEvaluations().size());
									
									if (Double.valueOf(averageRiskLevel) > 3.5) {
										riskClass = "td-red";
									} else if (Double.valueOf(averageRiskLevel) > 2) {
										riskClass = "td-yellow";
									} else {
										riskClass = "td-green";
									}
									
									if (Double.valueOf(averageAttractiveness) > 3.5) {
										attractivenessClass = "td-green";
									} else if (Double.valueOf(averageAttractiveness) > 2) {
										attractivenessClass = "td-yellow";
									} else {
										attractivenessClass = "td-red";
									}
								}
					%>
					
					<tr>
						<td><%= p.getAcronym() %></td>
						<td><%= p.getCategory().getDescription() %></td>
						<td><%= p.getFundingDuration() %></td>
						<td><%= p.getBudget() %></td>
						<td class="<%= riskClass %>"><%= averageRiskLevel %></td>
						<td class="<%= attractivenessClass %>"><%= averageAttractiveness %></td>
						<td><%= p.getEvaluations().size() %></td>
					</tr>
					<% 	
							}
						}
						else {
					%>
						<tr>
							<td colspan="6">You have not added any projects yet</td>
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
    