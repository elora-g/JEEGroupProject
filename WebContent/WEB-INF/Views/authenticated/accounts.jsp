<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bienvenue</title>
</head>
<body>

	<h1>Mes comptes et opérations</h1>
	
	<c:import url="/WEB-INF/Views/authenticated/menuauth.jsp" />
		<c:forEach var="account" items="${authenticatedPerson.accounts}">
		<section class="client_account"> 
			<h2>Compte n°<c:out value="${account.id}"></c:out> - Type : <c:out value="${account.type}"></c:out> - Solde : <c:out value="${account.balance}"></c:out> </h2>
			<c:choose>
				<c:when test="${fn:length(account.operations) > 0}">
					<h3> Opérations sur le compte</h3>
					<form method="post">
						<table>
							<thead>
								<tr>
									<td>Date de l'opération</td>
									<td>Type d'opération</td>
									<td>Description</td>
									<td>Montant</td>
									<td>Contester l'opération</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="operation" items="${account.operations}">
									<tr>
										<td><c:out value="${operation.createdAt}"></c:out></td>
										<td><c:out value="${operation.type}"></c:out></td>
										<td><c:out value="${operation.description}"></c:out></td>
										<td><c:out value="${operation.amount}"></c:out></td>
										<td>
											<c:choose>
												<c:when test = "${operation.dispute == true }">
												Opération déjà contestée
												</c:when>
												<c:otherwise>
													<button name="operationid" type="submit" value="${operation.id}">Contester cette opération</button>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
				</c:when>
				<c:otherwise>
					<h3>Vous n'avez aucune opération pour ce compte</h3>
				</c:otherwise>
			</c:choose>
		</section>
	</c:forEach>
	
	<form method="post">
		<input type ="hidden" name="operationId"></input>
	</form>
	
</body>
</html>