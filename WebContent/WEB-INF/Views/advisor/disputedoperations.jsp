<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="Connexion Page">
	<meta name="author" content="Elora Guyader">

	<link href="/assets/css/bootstrap.min.css" rel="stylesheet">
	<link href="/assets/css/generalstyle.css" rel="stylesheet" type="text/css">
	<title>Liste de toutes les opérations contestées</title>
</head>

<body>

	
	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	
	<div class = "container2">
	
		<h1>Opérations contestées par mes clients</h1>
		<c:choose>
			<c:when test ="${fn:length(authenticatedPerson.disputedOperationsForClients) > 0}" > 
				<c:forEach var="client" items="${authenticatedPerson.clients }"> 
					<c:if test="${fn:length(client.disputedOperations) > 0}">
						<h2>Client : <c:out value="${client.firstname}"/> <c:out value="${client.lastname}"/> - <c:out value="${client.externalId}"/></h2>
						<section class="client_account"> 
							<c:forEach var="account" items="${client.accounts }"> 
								<c:if test="${fn:length(account.disputedOperations) > 0}">
									<h3>Compte n°<c:out value="${account.id}"/> - Type : <c:out value="${account.type}"/> - Solde : <c:out value="${account.balance}"/> </h3>
									<table class="table table-stripped">
										<thead>
											<tr>
												<th>Date de l'opération</th>
												<th>Type d'opération</th>
												<th>Description</th>
												<th>Montant</th>
												<th>Approuver ou refuser la contestation</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="operation" items="${account.disputedOperations }">
												<tr>
													<td><c:out value="${operation.createdAt}"/> </td>
													<td><c:out value="${operation.type}"/> </td>
													<td><c:out value="${operation.description}"/>  </td>
													<td><c:out value="${operation.amount}"/> </td>
													<td>
														<form method="post" class="form-inline inline">
															<label for="disputeDecision" class="disputeDecision">#NonJeRendsPas 
										                	<input type="radio" name="disputeDecision" value="0">
										                	</label>
										                	<label for="disputeDecision" class="disputeDecision">#Rendsl'argent	
										                	<input type="radio" name="disputeDecision" value="1">
										                	</label>
															<button name="operationid" type="submit" value="${operation.id}" class="btn btn-primary">Enregistrer</button>
														</form>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</c:forEach>
						</section>
					</c:if>
				</c:forEach>
				<p class="error"><c:out value="${message}"></c:out>
			</c:when>
			<c:otherwise>
				<h3>Vos clients n'ont pas contesté d'opérations.</h3>
			</c:otherwise>
		</c:choose>
	</div>
	<script src="/assets/jquery/jquery.min.js"></script>
    <script src="/assets/tether/tether.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>		
		
</body>
</html>