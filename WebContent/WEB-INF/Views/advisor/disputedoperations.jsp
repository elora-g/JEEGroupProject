<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste de toutes les opérations contestées</title>
</head>

<body>

	<h1>Opérations contestées par mes clients</h1>
	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	
	<c:forEach var="client" items="${authenticatedPerson.clients }"> 
		<c:if test="${fn:length(client.disputedOperations) > 0}">
			<h2><c:out value="${client.firstname}"/> <c:out value="${client.lastname}"/> - <c:out value="${client.externalId}"/></h2>
			<section class="client_account"> 
				<c:forEach var="account" items="${client.accounts }"> 
					<c:if test="${fn:length(account.disputedOperations) > 0}">
						<h3>Compte n°<c:out value="${account.id}"/> - Type : <c:out value="${account.type}"/> - Solde : <c:out value="${account.balance}"/> </h3>
						<table>
							<thead>
								<tr>
									<td>Date de l'opération</td>
									<td>Type d'opération</td>
									<td>Description</td>
									<td>Montant</td>
									<td>Approuver ou refuser la contestation</td>
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
											<button name="acceptContest" type="submit" formmethod = "post">Approuver</button>
											<button name="refuseContest" type="submit" formmethod = "post">Refuser</button>
											<%--TODO: penser à retirer l'opération de la liste une fois que la décision a été prise passer son état à non contestée ==>  --%>
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
		
	<form method="post">
		<input type ="hidden" name="operationId"></input>
	</form>
	
	

</body>
</html>