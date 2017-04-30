<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bienvenue</title>
</head>
<body>

	<h1>Mes comptes et opérations</h1>
	
	<c:import url="/WEB-INF/Views/authenticated/menuauth.jsp" />
	
	<%--
	<c:forEach var="i" begin="1" end="5">
   		Item <c:out value="${i}"/><p>
	</c:forEach> 
	--%>
	
	<section class="client_account"> 
		<%-- <c:forEach compte du client --%>
			<h2>Compte n°<%--<c:out id></c:out>--%> - Type : <%--<c:out type></c:out>--%> - Solde : <%--<c:out balance></c:out>--%> </h2>
			<h3> Opérations sur le compte</h3>
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
					<%-- <c:forEach operation du compte --%>
						<tr>
							<td><%-- <c:out value= ${createdAt}/> --%></td>
							<td><%-- <c:out value= ${type}> --%></td>
							<td><%-- <c:out value= ${description}/> --%></td>
							<td><%-- <c:out value= ${amount}/> --%></td>
							<td>
								<%-- <c:choose>
									<c:when test = opération déjà contestée>
									Opération déjà contestée
									</c:when>
									<c:otherwise>
										<button name="contest" type="submit" formmethod = "post">Contester cette opération</button>
									</c:otherwise>
								</c:choose>--%>
							</td>
						</tr>
					<%--</c:forEach> --%>
				</tbody>
			</table>
		<%-- <c:/forEach> --%>	
	</section>

	<c:out value="${authenticatedPerson.email}"></c:out>
	
</body>
</html>