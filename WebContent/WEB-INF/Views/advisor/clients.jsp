<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="Connexion Page">
	<meta name="author" content="Elora Guyader">

	<link href="/assets/css/bootstrap.min.css" rel="stylesheet">
	<link href="/assets/css/generalstyle.css" rel="stylesheet" type="text/css">
	<title>Liste de mes clients</title>
</head>

<body>
	
	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	<div class="container2">
		<h1>Liste de mes clients</h1>
		<section class="clientList"> 
			<table>
				<thead>
					<tr>
						<td>Identifiant</td>
						<td>Nom</td>
						<td>Prénom</td>
						<td>Voir ce client</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="client" items="${authenticatedPerson.clients}">
					
						<tr>
							<td><c:out value= "${client.externalId}"/></td>
							<td><c:out value= "${client.lastname}"/></td>
							<td><c:out value= "${client.firstname}"/></td>
							<td><a href="/advisor/client?id=<c:out value="${client.externalId}"/>">Voir ce client</a> <%--TODO: redirect on the right client page --%></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</section>
			
		<section class="createClientForm">
		<h2>Créer un nouveau client</h2>
	   		<form method="post" action="">
	            <fieldset>
	                            
	                <label for="lastName">Nom <span class="required">*</span></label>
	                <input type="text" name="lastName" size="20" maxlength="60" />
	                <br />
	                
	                <label for="firstName">Prénom <span class="required">*</span></label>
	                <input type="text" name="firstName" size="20" maxlength="60" />
	                <br />
	                
	                <label for="dob">Date de naissance <span class="required">*</span></label>
	                <input type="text" name="dob" size="20" maxlength="60" />
	                <br />
	                
	                <label for="email">Email <span class="required">*</span></label>
	                <input type="email" name="email" size="20" maxlength="60" />
	                <br />
	
	                <label for="password">Entrez un mot de passe pour votre client <span class="required">*</span></label> <%--Not a good practice but doing differently is out of the scope of the project --%>
	                <input type="password" name="password" size="20" maxlength="20" />
	                <br />
	                
	                <label for="phoneNumber">Téléphone <span class="required">*</span></label>
	                <input type="text" name="phoneNumber" size="20" maxlength="60" />
	                <br />
	                
	                <label for="clientType">Le client est employé de la banque<span class="required">*</span></label>
	                <input type="radio" name="clientType" value="0">Non<br />
					<input type="radio" name="clientType" value="1">Oui<br />
	
	
	                <input type="submit" value="créer le client" class="noLabel" />
	                <br />
	                
	                <p>${message}</p>
	                
	            </fieldset>
	        </form>
		</section>
	</div>
	<script src="/assets/jquery/jquery.min.js"></script>
    <script src="/assets/tether/tether.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
</body>
</html>