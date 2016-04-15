<%@page import="java.util.LinkedList"%>
<%@page import="model.Document"%>
<%@page import="model.Evaluation"%>
<%@page import="model.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@page import="java.util.List"%>
<%@ page import="model.Category" %>

<%
	Project project = (Project) request.getAttribute("project");
	String acronym = project != null ? project.getAcronym() : "";
	String description = project != null ? project.getDescription() : "";
	String fundingDurationDays = project != null ? project.getFundingDuration() + "" : "";
	String budget = project != null ? project.getBudget() + "": "";
	String createdDate = project != null ? project.getCreated() + "" : "";
	String categoryName = project != null ? project.getCategory().getDescription() : "";
	List <Document> documents = project != null ? project.getDocuments() : new LinkedList <Document>();
%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>

<script type="text/javascript" >
	$(document).ready(function (){
		$('#upload').click(function(){
			$('#file-row').slideDown();
		})
	});
</script>

<h4>Projects</h4>
		<div class="panel panel-primary">
		
			<div class="panel-heading">
				Project Evaluation
			</div>
			
			<form enctype="multipart/form-data" method="post" action="<%= request.getContextPath() + "/project?project_id=" + project.getId() %>">
					<table style="width: 100%;">
				<thead>
					<tr>
						<th colspan="6" style="text-align: center;">Evaluation</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<% 
							Object error = request.getAttribute("error_message");
							if (error != null) {
								out.print("<td colspan='6' class='alert-danger'>" + error + "</td>");
							}
						%>
					</tr>
					<tr>
						<td  style="text-align: right;">Acronym:</td>
						<td><%= acronym %></td>
						<td  style="text-align: right;">Created:</td>
						<td><%= createdDate %></td>
					</tr>
					
					<tr>
						<td  style="text-align: right;">Description:</td>
						<td colspan="5"><%= description %></td>
					</tr>
					
					<tr>
						<td style="text-align: right;">Category:</td>
						<td><%= categoryName %></td>
						<td  style="text-align: right;"># Incubation days:</td>
						<td><%= fundingDurationDays %></td>
						<td  style="text-align: right;">Budget(EUR):</td>
						<td><%= budget %></td>
					</tr>
					<tr>
						<th colspan="6" style="text-align: center;">Documents</th>
					</tr>
					
					<tr>
						<td colspan="6" style="text-align: right;">
							<button type="button"  class="btn btn-small" id="upload">Upload</button>
						</td>
					</tr>
					
					<tr style="display: none" id="file-row">
						<td colspan="5" style="text-align: right;">
							<input class="pull-right" type="file" name="doc" id="doc">
						</td>
						<td style="text-align: left;">
							<input type="hidden" name="hidden" value="hidden">
							<input type="submit" class="btn btn-default" name="Upload" value="Save File">
						</td>
					</tr>
					
					<%
						if (documents.size() > 0) {
							for (Document d : documents) {
					%>
						<tr>
							<td colspan="6"><%= d.getDocumentPath() %></td>
						</tr>
					<%			
							}
						}
					%>
					
					<tr>
						<th colspan="6" style="text-align: center;">Statistics</th>
					</tr>
					
					<%
					String averageRiskLevel = "", averageAttractiveness = "", riskClass = "", attractivenessClass = "";
					if (project.getEvaluations().size() > 0) {
						double sumRiskLevel = 0, sumAttractiveness = 0;
						for (Evaluation e: project.getEvaluations()) {
							sumRiskLevel += e.getRiskLevel();
							sumAttractiveness += e.getAttractiveness();
						}
						
						averageRiskLevel = String.valueOf(sumRiskLevel / project.getEvaluations().size());
						averageAttractiveness = String.valueOf(sumAttractiveness / project.getEvaluations().size());
					}
					%>
					
					<tr>
						<td style="text-align: right;">Risk Level:</td>
						<td><%= averageRiskLevel %></td>
						<td  style="text-align: right;">Attractiveness:</td>
						<td><%= averageAttractiveness %></td>
						<td  style="text-align: right;"># of evaluators:</td>
						<td><%= project.getEvaluations().size() %></td>
					</tr>
				</tbody>
			</table>
			</form>
			
			<!-- panel body -->
		</div>
		<!-- panel default -->

<jsp:include page="/utils/footer.jsp" />
    