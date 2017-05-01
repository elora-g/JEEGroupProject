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
			<table class="table table-striped">
				<thead>
					<tr>
						<th scope="row">Identifiant</th>
						<th>Nom</th>
						<th>Prénom</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="client" items="${authenticatedPerson.clients}">
					
						<tr>
							<th scope="row"><c:out value= "${client.externalId}"/></th>
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
	                <div class="form-group row">           
		                <label for="lastName" class="col-2">Nom <span class="required">*</span></label>
		                <input type="text" name="lastName" class="form-control col-3" maxlength="60">
	                </div>
	                
	                <div class="form-group row">
		                <label for="firstName" class="col-2">Prénom <span class="required">*</span></label>
		                <input type="text" name="firstName" class="form-control col-3" maxlength="60">
	                </div>
	                
	                <div class="form-group row">
		                <label for="dob" class="col-2">Date de naissance <span class="required">*</span></label>
		                <input type="text" name="dob" class="form-control col-3" maxlength="60">
	                </div>
	                
	                <div class="form-group row">
		                <label for="email" class="col-2">Email <span class="required">*</span></label>
		                <input type="email" name="email" class="form-control col-3" maxlength="60">
	                </div>
	                
					<div class="form-group row">
		                <label for="password" class="col-2">Entrez un mot de passe<span class="required">*</span></label> <%--Not a good practice but doing differently is out of the scope of the project --%>
		                <input type="password" name="password" class="form-control col-3" maxlength="20">
	                </div>
	                
	                <div class="form-group row">
		                <label for="phoneNumber" class="col-2">Téléphone <span class="required">*</span></label>
		                <input type="text" name="phoneNumber" class="form-control col-3" maxlength="60">
	                </div>
	                
	               	<div class="form-group row">
		                <label for="clientType" class="col-3">Le client est employé de la banque
		                	<input type="radio" name="clientType" value="0">
		                </label>
		                <label for="clientType" class="col-3">Le client n'est pas un employé
							<input type="radio" name="clientType" value="1">
						</label>
					</div>
	
	
	                <input type="submit" value="créer le client" class="btn btn-primary">
	                
	                <p class="error">${message}</p>
	                
	            </fieldset>
	        </form>
		</section>
	</div>
	<script src="/assets/jquery/jquery.min.js"></script>
    <script src="/assets/tether/tether.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
</body>
</html>