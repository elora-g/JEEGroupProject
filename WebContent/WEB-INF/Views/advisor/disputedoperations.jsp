<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste de toutes les opérations contestées</title>
</head>

<body>

	<h1>Opérations contestées par mes clients</h1>
	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	
		<%-- <c:forEach client --%>
		<section class="client_account"> 
			<h2>Compte n°<%--<c:out id></c:out>--%> - Type : <%--<c:out type></c:out>--%> - Solde : <%--<c:out balance></c:out>--%> </h2>
			<h3> Opérations contestées sur le compte</h3>
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
					<%-- <c:forEach operation contestée du compte --%>
						<tr>
							<td><%-- <c:out value= ${createdAt}/> --%></td>
							<td><%-- <c:out value= ${type}> --%></td>
							<td><%-- <c:out value= ${description}/> --%></td>
							<td><%-- <c:out value= ${amount}/> --%></td>
							<td>
								<button name="acceptContest" type="submit" formmethod = "post">Approuver</button>
								<button name="refuseContest" type="submit" formmethod = "post">Refuser</button>
								<%--TODO: penser à retirer l'opération de la liste une fois que la décision a été prise passer son état à non contestée ==>  --%>
							</td>
						</tr>
					<%--</c:forEach> --%>
				</tbody>
			</table>
		</section>
	<%-- <c:/forEach> --%>
		
	<form method="post">
		<input type ="hidden" name="operationId"></input>
	</form>
	
	

</body>
</html>