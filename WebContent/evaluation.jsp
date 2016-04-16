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
	List <Evaluation> evaluations = project != null ? project.getEvaluations() : new LinkedList <Evaluation>();
	
	int riskLevelDefault = 1, attractivenessDefault = 1;
	boolean alreadyEvaluatedByMe = false;
	if (evaluations.size() > 0) {
		for (Evaluation e: evaluations) {
			if (e.getEvaluator().getId() == Integer.valueOf(String.valueOf(session.getAttribute("user_id")))) {
				riskLevelDefault = e.getRiskLevel();
				attractivenessDefault = e.getAttractiveness();
				alreadyEvaluatedByMe = true;
				break;
			}
		}
	}
%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>

<h4>Projects</h4>
		<div class="panel panel-primary">
			
			<form method="post" action="<%= request.getContextPath() + "/evaluate?project_id=" + project.getId() %>">
					<table style="width: 100%;">
				<thead>
					<tr>
						<th colspan="6" style="text-align: center;">Project Evaluation</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<% 
							Object error = request.getAttribute("error_message");
							Object success = request.getAttribute("created");
							if (success != null) {
								out.print("<td colspan='6' class='alert-success'>Your evaluation has been saved</td>");
							} else if (error != null) {
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
						<td><%= budget %>
							<input type="hidden" name="hidden" value="hidden"></td>
					</tr>
					<tr>
						<th colspan="6" style="text-align: center;">Documents</th>
					</tr>
					
					<%
						if (documents.size() > 0) {
							for (Document d : documents) {
					%>
						<tr>
							<td colspan="6"><a href="file:///<%= d.getDocumentPath() %>"><%= d.getDocumentPath().substring(d.getDocumentPath().lastIndexOf('/') + 1) %></a></td>
						</tr>
					<%			
							}
						}
					%>
					
					<tr>
						<th colspan="6" style="text-align: left;">Your evaluation <% if (alreadyEvaluatedByMe) {out.print("(<span class='small'>You have already evaluated this project<span>)");} %></th>
					</tr>
					
					<tr>
						<td>Attractiveness:</td>
						<td>
							<select class="form-control" name="attractiveness" <% if (alreadyEvaluatedByMe) { out.print("disabled = 'disabled'"); } %>>
								<% for (int i=1; i<=5; i++) {
									%>
									<option <% if (i == attractivenessDefault) { out.print("selected = 'selected'");} %> value="<%= i %>"><%= i %></option>
									<%
								} %>
									
								
							</select>
						</td>
						
					</tr>
					<tr>
						<td>Risk Level:</td>
						<td>
							<select class="form-control" name="risk_level" <% if (alreadyEvaluatedByMe) { out.print("disabled = 'disabled'"); } %>>
								<% for (int i=1; i<=5; i++) {
									%>
									<option <% if (i == riskLevelDefault) { out.print("selected = 'selected'");} %> value="<%= i %>"><%= i %></option>
									<%
								} %>
									
								
							</select>
						</td>
					</tr>
					<% if (!alreadyEvaluatedByMe) { 
					%>
					<tr>
						<td><button class="btn btn-default" type="submit" name="Save">Save</button></td>
						<td><button class="btn btn-default" type="reset">Discard</button></td>
					</tr>		
					<%
					} %>
					
				</tbody>
			</table>
			</form>
			
			<!-- panel body -->
		</div>
		<!-- panel default -->

<jsp:include page="/utils/footer.jsp" />
    